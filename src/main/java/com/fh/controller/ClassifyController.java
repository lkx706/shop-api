package com.fh.controller;
import com.fh.annotation.Check;
import com.fh.model.ServerResponse;
import com.fh.service.ClassifyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/classify")
@Api(tags = "分类接口")
public class ClassifyController {
  @Autowired
  private ClassifyService classifyService;

  @RequestMapping("queryClassifyList")
  public ServerResponse queryClassifyList(){
    return classifyService.queryClassifyList();
  }

}

