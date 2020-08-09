package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookQuery extends DataTablePageBean{

  private String bookName;

  private Integer minPrice;

  private Integer maxPrice;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date minCreateDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date maxCreateDate;

  private Integer bookTypeId;


  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public Integer getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(Integer minPrice) {
    this.minPrice = minPrice;
  }

  public Integer getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(Integer maxPrice) {
    this.maxPrice = maxPrice;
  }

  public Date getMinCreateDate() {
    return minCreateDate;
  }

  public void setMinCreateDate(Date minCreateDate) {
    this.minCreateDate = minCreateDate;
  }

  public Date getMaxCreateDate() {
    return maxCreateDate;
  }

  public void setMaxCreateDate(Date maxCreateDate) {
    this.maxCreateDate = maxCreateDate;
  }

  public Integer getBookTypeId() {
    return bookTypeId;
  }

  public void setBookTypeId(Integer bookTypeId) {
    this.bookTypeId = bookTypeId;
  }
}
