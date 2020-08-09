package com.fh.service;

import com.fh.model.ServerResponse;
import com.fh.param.OrderParam;

public interface OrderService {
  ServerResponse generateOrder(OrderParam orderParam);

  void createOrder(OrderParam orderParam);
}
