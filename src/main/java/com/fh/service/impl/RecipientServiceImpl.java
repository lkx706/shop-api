package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.RecipientMapper;
import com.fh.model.Recipient;
import com.fh.model.ServerResponse;
import com.fh.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {
  @Autowired
  private RecipientMapper recipientMapper;

  @Override
  public ServerResponse findRecipientList(Long memberId) {
    QueryWrapper<Recipient> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("memberId", memberId);
    List<Recipient> orderList = recipientMapper.selectList(queryWrapper);
    return ServerResponse.success(orderList);
  }
}
