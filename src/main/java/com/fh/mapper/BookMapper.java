package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Book;
import com.fh.model.BookQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper extends BaseMapper<Book> {
  Long queryCountList(BookQuery bookQuery);

  List<Book> queryBookList(BookQuery bookQuery);
}
