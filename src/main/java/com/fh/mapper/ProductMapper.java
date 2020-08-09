package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {

  @Update("update t_product set stock=stock-#{num} where id = #{goodsId} and stock>=#{num}")
  int updateProduct(@Param("goodsId") Long goodsId,@Param("num") int num);
}
