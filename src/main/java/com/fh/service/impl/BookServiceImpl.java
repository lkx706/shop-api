package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.BookMapper;
import com.fh.mapper.BookTypeMapper;
import com.fh.model.*;
import com.fh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookMapper bookMapper;

  @Autowired
  private BookTypeMapper bookTypeMapper;

  @Override
  public DataTableResult queryBookList(BookQuery bookQuery) {

    Long count = bookMapper.queryCountList(bookQuery);

    List<Book> bookList = bookMapper.queryBookList(bookQuery);

    DataTableResult dataTableResult = new DataTableResult(bookQuery.getDraw(),count,count,bookList);

    return dataTableResult;
  }

  @Override
  public ServerResponse queryBookTypeList() {
    List<BookType> bookTypeList = bookTypeMapper.selectList(null);
    return ServerResponse.success(bookTypeList);
  }

  @Override
  public ServerResponse addBook(Book book) {
    bookMapper.insert(book);
    return ServerResponse.success();
  }

  @Override
  public ServerResponse deleteBook(Integer bookId) {
      bookMapper.deleteById(bookId);
    return ServerResponse.success();
  }

  @Override
  public ServerResponse getBookById(Integer bookId) {
    QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
    bookQueryWrapper.eq("bookId",bookId);
    Book book = bookMapper.selectOne(bookQueryWrapper);
    return ServerResponse.success(book);
  }

  @Override
  public ServerResponse updateBook(Book book) {
    bookMapper.updateById(book);
    return ServerResponse.success();
  }
}
