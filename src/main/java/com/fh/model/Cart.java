package com.fh.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.util.BigDecimalJackson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

  @JsonSerialize(using = BigDecimalJackson.class)
  private BigDecimal totalPrice;

  private int totalNum;

  private List<CartItem> cartItemList = new ArrayList<>();

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public int getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(int totalNum) {
    this.totalNum = totalNum;
  }

  public List<CartItem> getCartItemList() {
    return cartItemList;
  }

  public void setCartItemList(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }
}
