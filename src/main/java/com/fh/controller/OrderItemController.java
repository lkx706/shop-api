package com.fh.controller;

import com.fh.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
  @Autowired
  private OrderItemService orderItemService;
}
