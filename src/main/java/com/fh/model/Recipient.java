package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

@TableName("t_recipient")
public class Recipient {
  @TableId(value = "recipientId",type = IdType.AUTO)
  private Long recipientId;

  private String recipient;//收件人

  @TableField("addRess")
  private String addRess;//详细地址

  private Integer phone;

  private String email;

  @TableField("areaAlias")
  private String areaAlias;//地址别名

  @TableField("memberId")
  private Integer memberId;

  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getRecipientId() {
    return recipientId;
  }

  public void setRecipientId(Long recipientId) {
    this.recipientId = recipientId;
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public String getAddRess() {
    return addRess;
  }

  public void setAddRess(String addRess) {
    this.addRess = addRess;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAreaAlias() {
    return areaAlias;
  }

  public void setAreaAlias(String areaAlias) {
    this.areaAlias = areaAlias;
  }

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
  }
}
