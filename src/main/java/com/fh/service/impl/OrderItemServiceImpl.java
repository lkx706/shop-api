package com.fh.service.impl;

import com.fh.mapper.OrderItemMapper;
import com.fh.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  @Autowired
  private OrderItemMapper orderItemMapper;
}
