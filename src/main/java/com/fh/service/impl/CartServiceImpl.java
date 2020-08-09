package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.ProductMapper;
import com.fh.model.*;
import com.fh.service.CartService;
import com.fh.util.BigDecimalUtil;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private ProductMapper productMapper;

  @Override
  public ServerResponse addItem(Long memberId, Long goodsId, int num) {
      //判断商品是否存在
    QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("id",goodsId);
    Product product = productMapper.selectOne(queryWrapper);
    if(product==null){
      return ServerResponse.error(ResponseEnum.PRODUCT_IS_NULL);
    }
    //判断商品状态是否上架
    if (product.getIsHot()!=1){
      return ServerResponse.error(ResponseEnum.PRODUCT_IS_SOLD_OUT);
    }
    String cartKey = KeyUtil.buildCartKey(memberId);

    String cartJson = JedisUtil.get(cartKey);
    //判断会员有没有购物车
    if (StringUtils.isNotEmpty(cartJson)){
      //直接向购物车放商品
      Cart cart = JSONObject.parseObject(cartJson, Cart.class);
      List<CartItem> cartItemList = cart.getCartItemList();
      CartItem cartItem = null;
      for (CartItem item : cartItemList) {
            if (item.getGoodsId().longValue() == goodsId.longValue()){
                cartItem=item;
                break;
            }
      }
      if (cartItem!=null){
        //如果商品存在那么更新数量和小计，
        cartItem.setNum(cartItem.getNum()+num);
        int num1 = cartItem.getNum();
        if(num1 <= 0){
          cartItemList.remove(cartItem);
        }else {
          BigDecimal mul = BigDecimalUtil.mul(num1 + "", cartItem.getPrice().toString());
          cartItem.setSubPrice(mul);
        }
        if (cartItemList.size()==0){
            JedisUtil.del(cartKey);
            return ServerResponse.success();
        }
        //更新购物车
        int totalCount=0;
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem item : cartItemList) {
            totalCount+=item.getNum();
            totalPrice=BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
        }
        cart.setTotalNum(totalCount);
        cart.setTotalPrice(totalPrice);
        //更新redis
        String carNewJson = JSONObject.toJSONString(cart);
        JedisUtil.set(cartKey,carNewJson);
      }else {
        //如果商品不存在的话添加商品并更新购物车
        if (num<=0){
            return ServerResponse.error(ResponseEnum.PRODUCT_NUM);
        }
        CartItem cartItem1 = new CartItem();
        cartItem1.setGoodsId(product.getId());
        cartItem1.setPrice(product.getPrice());
        cartItem1.setFilePath(product.getFilePath());
        cartItem1.setNum(num);
        cartItem1.setGoodsName(product.getProductName());
        BigDecimal subPrice = BigDecimalUtil.mul(num + "", product.getPrice().toString());
        cartItem1.setSubPrice(subPrice);
        //加入购物车
        cart.getCartItemList().add(cartItem1);

        //更新购物车
        int totalCount=0;
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem item : cartItemList) {
          totalCount+=item.getNum();
          totalPrice=BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
        }
        cart.setTotalNum(totalCount);
        cart.setTotalPrice(totalPrice);
        //更新redis
        String carNewJson = JSONObject.toJSONString(cart);
        JedisUtil.set(cartKey,carNewJson);
      }

    }else {
      //会员没有购物车
      if (num<=0){
        return ServerResponse.error(ResponseEnum.PRODUCT_NUM);
      }
      //创建购物车
      Cart cart = new Cart();
      List<CartItem> cartItemList = cart.getCartItemList();
      //添加商品并加入购物车
      CartItem cartItem1 = new CartItem();
      cartItem1.setGoodsId(product.getId());
      cartItem1.setPrice(product.getPrice());
      cartItem1.setFilePath(product.getFilePath());
      cartItem1.setNum(num);
      cartItem1.setGoodsName(product.getProductName());
      BigDecimal subPrice = BigDecimalUtil.mul(num + "", product.getPrice().toString());
      cartItem1.setSubPrice(subPrice);
      //加入购物车
      cart.getCartItemList().add(cartItem1);

      //更新购物车
      int totalCount=0;
      BigDecimal totalPrice = new BigDecimal(0);
      for (CartItem item : cartItemList) {
        totalCount+=item.getNum();
        totalPrice=BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
      }
      cart.setTotalNum(totalCount);
      cart.setTotalPrice(totalPrice);
      //更新redis
      String carNewJson = JSONObject.toJSONString(cart);
      JedisUtil.set(cartKey,carNewJson);
    }
    return ServerResponse.success();
  }

  @Override
  public ServerResponse findItemList(Long memberId) {
    String cartKey = KeyUtil.buildCartKey(memberId);
    String cartJson = JedisUtil.get(cartKey);
    if (cartJson==null){
      return ServerResponse.success(0);
    }
    Cart cart = JSONObject.parseObject(cartJson, Cart.class);
    return ServerResponse.success(cart);
  }

  @Override
  public ServerResponse deleteCart(Long memberId, Long goodsId) {
    String cartKey = KeyUtil.buildCartKey(memberId);
    String cartJson = JedisUtil.get(cartKey);
    Cart cart = JSONObject.parseObject(cartJson, Cart.class);
    List<CartItem> cartItemList = cart.getCartItemList();
    for (CartItem cartItem : cartItemList) {
      if (cartItem.getGoodsId().longValue()==goodsId.longValue()){
        cartItemList.remove(cartItem);
        break;
      }
    }
    int totalCount=0;
    BigDecimal totalPrice = new BigDecimal(0);
    for (CartItem item : cartItemList) {
      totalCount+=item.getNum();
      totalPrice=BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
    }


    Cart cart1 = new Cart();
    cart1.setTotalNum(totalCount);
    cart1.setTotalPrice(totalPrice);
    cart1.setCartItemList(cartItemList);
    //更新redis
    String carNewJson = JSONObject.toJSONString(cart1);
    JedisUtil.set(cartKey,carNewJson);
    return ServerResponse.success();
  }

  @Override
  public ServerResponse deleteAllCart(List<Long> goodsIds, Long memberId) {
    String cartKey = KeyUtil.buildCartKey(memberId);
    String cartJson = JedisUtil.get(cartKey);
    Cart cart = JSONObject.parseObject(cartJson, Cart.class);
    List<CartItem> cartItemList = cart.getCartItemList();
    for (Long goodsId : goodsIds) {
      for (CartItem cartItem : cartItemList) {
        if (cartItem.getGoodsId()==goodsId){
          cartItemList.remove(cartItem);
          break;
        }
      }
    }

    int totalCount=0;
    BigDecimal totalPrice = new BigDecimal(0);
    for (CartItem item : cartItemList) {
      totalCount+=item.getNum();
      totalPrice=BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
    }

    cart.setTotalNum(totalCount);
    cart.setTotalPrice(totalPrice);
    cart.setCartItemList(cartItemList);
    String cartNewJson = JSONObject.toJSONString(cart);
    JedisUtil.set(KeyUtil.buildCartKey(memberId),cartNewJson);
    return ServerResponse.success();
  }
}
