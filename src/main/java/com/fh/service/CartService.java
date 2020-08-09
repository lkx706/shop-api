package com.fh.service;

import com.fh.model.ServerResponse;

import java.util.List;

public interface CartService {

  ServerResponse addItem(Long memberId, Long goodsId, int num);

  ServerResponse findItemList(Long memberId);

  ServerResponse deleteCart(Long memberId, Long goodsId);

  ServerResponse deleteAllCart(List<Long> goodsIds, Long memberId);
}
