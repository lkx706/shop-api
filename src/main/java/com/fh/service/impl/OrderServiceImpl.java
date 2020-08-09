package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.exception.StockLessException;
import com.fh.mapper.*;
import com.fh.model.*;
import com.fh.mq.MQConfig;
import com.fh.param.OrderParam;
import com.fh.service.OrderService;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private RecipientMapper recipientMapper;

  @Autowired
  private OrderItemMapper orderItemMapper;
  @Autowired
  private PayLogMapper payLogMapper;

  @Override
  public ServerResponse generateOrder(OrderParam orderParam) {
    Long memberId = orderParam.getMemberId();
    JedisUtil.del(KeyUtil.buildStockLessKey(memberId));
    JedisUtil.del(KeyUtil.buildOrderKey(memberId));
    JedisUtil.del(KeyUtil.ORDER_FAILED(memberId));
    //将订单信息发送到消息中间件(MQ)
    String orderParamJson = JSONObject.toJSONString(orderParam);
    rabbitTemplate.convertAndSend(MQConfig.ORDER_EXCHANGE,MQConfig.ORDER_ROUTEKEY,orderParamJson);
    return ServerResponse.success();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createOrder(OrderParam orderParam) {
    Long memberId = orderParam.getMemberId();
    //从redis里面取出用户信息
    String cartJson = JedisUtil.get(KeyUtil.buildCartKey(memberId));
    Cart cart = JSONObject.parseObject(cartJson, Cart.class);
    List<CartItem> cartItemList = cart.getCartItemList();

    //减库存
    //考虑到并发，使用乐观锁
    for (CartItem cartItem : cartItemList) {
      Long goodsId = cartItem.getGoodsId();
      int num = cartItem.getNum();
      int rowCount = productMapper.updateProduct(goodsId,num);
      if (rowCount==0){
          throw new StockLessException("stock less");
      }
    }
    //获取对应收件人id
    Long recipientId = orderParam.getRecipientId();
    Recipient recipient = recipientMapper.selectById(recipientId);
    //插入订单
    Order order = new Order();
    String idStr = IdWorker.getIdStr();
    order.setId(idStr);
    order.setCreateTime(new Date());
    order.setRecipientId(recipient.getRecipientId());
    order.setPhone(recipient.getPhone());
    order.setAddRess(recipient.getAddRess());
    order.setMemberId(memberId);
    order.setTotalPrice(cart.getTotalPrice());
    order.setRecipient(recipient.getRecipient());
    order.setPayType(orderParam.getPayType());
    order.setStatus(KeyUtil.OrderStatus.WAIT_PAY);
    order.setTotalNum(cart.getTotalNum());
    orderMapper.insert(order);
    //插入订单明细
    List<OrderItem> orderItems = new ArrayList<>();
    for (CartItem cartItem : cartItemList) {
      OrderItem orderItem = new OrderItem();
      orderItem.setMemberId(memberId);
      orderItem.setOrderId(order.getId());
      orderItem.setProductId(cartItem.getGoodsId());
      orderItem.setFilePath(cartItem.getFilePath());
      orderItem.setNum(cartItem.getNum());
      orderItem.setPrice(cartItem.getPrice());
      orderItem.setProductName(cartItem.getGoodsName());
      orderItem.setSubPrice(cartItem.getSubPrice());
      orderItems.add(orderItem);
    }
    //批量插入
    orderItemMapper.addAllOrderItem(orderItems);

    //插入支付日志表
    PayLog payLog = new PayLog();
    payLog.setCreateTime(new Date());
    payLog.setMemberId(memberId);
    payLog.setOrderId(order.getId());
    String idStr1 = IdWorker.getIdStr();
    payLog.setOutTradeNo(idStr1);
    payLog.setPayMoney(order.getTotalPrice());
    payLog.setPayStatus(KeyUtil.PayLogStatus.WAIT_PAY);
    payLog.setPayType(orderParam.getPayType());
    payLogMapper.insert(payLog);
    //将支付日志存入redis
    String payLogJson = JSONObject.toJSONString(payLog);
    JedisUtil.set(KeyUtil.buildPayLogKey(memberId),payLogJson);
    //删除购物车中的信息
    JedisUtil.del(KeyUtil.buildCartKey(memberId));
    JedisUtil.set(KeyUtil.buildOrderKey(memberId),"order success");
  }
}
