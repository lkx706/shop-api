package com.fh.controller;

import com.fh.model.Book;
import com.fh.model.BookQuery;
import com.fh.model.DataTableResult;
import com.fh.model.ServerResponse;
import com.fh.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("api/book")
@Api(tags = "Demo接口")
public class BookController {

  @Autowired
  private BookService bookService;

  @RequestMapping("queryBookList")
  @ResponseBody
  public DataTableResult queryBookList(BookQuery bookQuery){
    DataTableResult dataTableResult = bookService.queryBookList(bookQuery);
    return dataTableResult;
  }
  @RequestMapping("queryBookTypeList")
  @ResponseBody
  public ServerResponse queryBookTypeList(){
    return bookService.queryBookTypeList();
  }
  @RequestMapping("addBook")
  @ResponseBody
  public ServerResponse addBook(Book book){
    book.setCreateDate(new Date());
    return bookService.addBook(book);
  }
  @RequestMapping("deleteBook")
  @ResponseBody
  public ServerResponse deleteBook(Integer bookId){
    return bookService.deleteBook(bookId);
  }
  @RequestMapping("getBookById")
  @ResponseBody
  public ServerResponse getBookById(Integer bookId){
    return bookService.getBookById(bookId);
  }
  @RequestMapping("updateBook")
  @ResponseBody
  public ServerResponse updateBook(Book book){
    return bookService.updateBook(book);
  }
}
