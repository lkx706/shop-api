package com.fh.service;

import com.fh.model.Book;
import com.fh.model.BookQuery;
import com.fh.model.DataTableResult;
import com.fh.model.ServerResponse;

public interface BookService {
  DataTableResult queryBookList(BookQuery bookQuery);

  ServerResponse queryBookTypeList();

  ServerResponse addBook(Book book);

  ServerResponse deleteBook(Integer bookId);

  ServerResponse getBookById(Integer bookId);

  ServerResponse updateBook(Book book);
}
