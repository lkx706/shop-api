package com.fh.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.util.BigDecimalJackson;

import java.math.BigDecimal;

public class CartItem {

  private Long goodsId;

  private String goodsName;

  @JsonSerialize(using = BigDecimalJackson.class)
  private BigDecimal price;

  private int num;

  @JsonSerialize(using = BigDecimalJackson.class)
  private BigDecimal subPrice;

  private String filePath;

  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public BigDecimal getSubPrice() {
    return subPrice;
  }

  public void setSubPrice(BigDecimal subPrice) {
    this.subPrice = subPrice;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
