package com.fh.controller;

import com.fh.annotation.Check;
import com.fh.model.ServerResponse;
import com.fh.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/product")
@Api(tags = "商品接口")
public class ProductController {
  @Autowired
  private ProductService productService;

  @Value("${server.port:}")
  private String port;

  @ResponseBody
  @RequestMapping("test")
  public String test(){
    return port;
  }

  @ResponseBody
  @RequestMapping("queryHotProduct")
  public ServerResponse queryHotProduct(){
    return productService.queryHotProduct();
  }

}
