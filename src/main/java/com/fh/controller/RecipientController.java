package com.fh.controller;

import com.fh.annotation.Check;
import com.fh.model.ServerResponse;
import com.fh.service.RecipientService;
import com.fh.util.KeyUtil;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/recipient")
@Api(tags = "收件人接口")
public class RecipientController {

  @Autowired
  private RecipientService recipientService;

  @GetMapping("findRecipientList")
  @Check
  public ServerResponse findRecipientList(HttpServletRequest request){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    Long memberId = memberVo.getId();
    return recipientService.findRecipientList(memberId);
  }



}
