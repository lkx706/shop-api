package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

@TableName("t_order_item")
public class OrderItem {
  @TableId(value = "id",type = IdType.AUTO)
  private Long id;

  @TableField("orderId")
  private String orderId;

  @TableField("memberId")
  private Long memberId;

  @TableField("productId")
  private Long productId;

  @TableField("productName")
  private String productName;

  @TableField("filePath")
  private String filePath;

  private BigDecimal price;

  private Integer num;

  @TableField("subPrice")
  private BigDecimal subPrice;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public BigDecimal getSubPrice() {
    return subPrice;
  }

  public void setSubPrice(BigDecimal subPrice) {
    this.subPrice = subPrice;
  }
}
