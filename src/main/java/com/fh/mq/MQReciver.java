package com.fh.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.exception.StockLessException;
import com.fh.mapper.ProductMapper;
import com.fh.model.Cart;
import com.fh.model.CartItem;
import com.fh.model.Product;
import com.fh.param.OrderParam;
import com.fh.service.OrderService;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MQReciver {

  /*@RabbitListener(queues = "goodsQueue")
  public void handleGoodsMessage(String goodsMessage){
    System.out.println(goodsMessage);
  }*/

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private OrderService orderService;

  @RabbitListener(queues = MQConfig.ORDER_QUEUE)
  public void handleOrderMessage(String msg, Message message, Channel channel) throws IOException {
    OrderParam orderParam = JSONObject.parseObject(msg, OrderParam.class);
    Long memberId = orderParam.getMemberId();

    //从redis里面取出用户信息
    String cartJson = JedisUtil.get(KeyUtil.buildCartKey(memberId));
    Cart cart = JSONObject.parseObject(cartJson, Cart.class);
    List<CartItem> cartItemList = cart.getCartItemList();
    //根据id集合查询出商品
    List<Long> goodIdList = cartItemList.stream().map(x -> x.getGoodsId()).collect(Collectors.toList());

    QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("id",goodIdList);
    List<Product> productList = productMapper.selectList(queryWrapper);
    //循环对比
    for (CartItem cartItem : cartItemList) {
      for (Product product : productList) {
        if (cartItem.getGoodsId().longValue()==product.getId().longValue()){
            if (cartItem.getNum()>product.getStock()){
                JedisUtil.set(KeyUtil.buildStockLessKey(memberId),"stock less");
              MessageProperties messageProperties = message.getMessageProperties();
              long deliveryTag = messageProperties.getDeliveryTag();
              channel.basicAck(deliveryTag,false);
              return;
            }
        }
      }
    }
    //创建订单
    try {
      orderService.createOrder(orderParam);
    } catch (StockLessException e) {
      MessageProperties messageProperties = message.getMessageProperties();
      long deliveryTag = messageProperties.getDeliveryTag();
      channel.basicAck(deliveryTag,false);
      e.printStackTrace();
    }catch (Exception e){
      e.printStackTrace();
      JedisUtil.set(KeyUtil.ORDER_FAILED(memberId),"error");
      MessageProperties messageProperties = message.getMessageProperties();
      long deliveryTag = messageProperties.getDeliveryTag();
      channel.basicAck(deliveryTag,false);
    }


  }

}
