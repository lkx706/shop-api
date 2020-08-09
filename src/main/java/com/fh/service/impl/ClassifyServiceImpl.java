package com.fh.service.impl;

import com.fh.mapper.ClassifyMapper;
import com.fh.model.Classify;
import com.fh.model.ServerResponse;
import com.fh.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyServiceImpl implements ClassifyService {
  @Autowired
  private ClassifyMapper classifyMapper;


  @Override
  public ServerResponse queryClassifyList() {
    List<Classify> classifyList = classifyMapper.selectList(null);
    return ServerResponse.success(classifyList);
  }
}
