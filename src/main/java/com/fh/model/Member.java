package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_member")
public class Member {
  @TableId(value = "memberId",type = IdType.AUTO)
  private Long memberId;
  @TableField("memberName")
  private String memberName;
  @TableField("memberPwd")
  private String memberPwd;
  @TableField("realName")
  private String realName;

  private String mail;
  @TableField("shengId")
  private Integer shengId;
  @TableField("shiId")
  private Integer shiId;
  @TableField("xianId")
  private Integer xianId;
  @TableField("areaName")
  private String areaName;

  public Integer getShengId() {
    return shengId;
  }

  public void setShengId(Integer shengId) {
    this.shengId = shengId;
  }

  public Integer getShiId() {
    return shiId;
  }

  public void setShiId(Integer shiId) {
    this.shiId = shiId;
  }

  public Integer getXianId() {
    return xianId;
  }

  public void setXianId(Integer xianId) {
    this.xianId = xianId;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getMemberPwd() {
    return memberPwd;
  }

  public void setMemberPwd(String memberPwd) {
    this.memberPwd = memberPwd;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
}
