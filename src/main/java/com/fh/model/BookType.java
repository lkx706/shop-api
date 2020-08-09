package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_bookType")
public class BookType {
  private Integer id;

  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
