package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.ProductMapper;
import com.fh.model.Product;
import com.fh.model.ServerResponse;
import com.fh.service.ProductService;
import com.fh.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductMapper productMapper;

  @Override
  public ServerResponse queryHotProduct() {
    String product = JedisUtil.get("product");
    List<Product> list = JSONObject.parseArray(product, Product.class);

    if(list==null){
      QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("isHot",1);
      List<Product> productList = productMapper.selectList(queryWrapper);
      //存储集合到redis，并取出
      String s = JSONObject.toJSONString(productList);
      JedisUtil.set("product",s);
     return ServerResponse.success(productList);
    }
    return ServerResponse.success(list);
  }
}
