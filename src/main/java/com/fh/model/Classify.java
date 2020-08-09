package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_category")
public class Classify {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;

  @TableField("categoryName")
  private String classifyName;

  @TableField("pid")
  private Integer pid;

  private Integer type;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getClassifyName() {
    return classifyName;
  }

  public void setClassifyName(String classifyName) {
    this.classifyName = classifyName;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }
}
