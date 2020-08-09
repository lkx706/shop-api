package com.fh.controller;

import com.fh.annotation.Check;
import com.fh.model.ServerResponse;
import com.fh.service.PayLogService;
import com.fh.util.KeyUtil;
import com.fh.vo.MemberVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payLog")
public class PayLogController {
  @Autowired
  private PayLogService payLogService;

  @PostMapping("createNative")
  @Check
  @ApiOperation("统一下单")
  public ServerResponse createNative(HttpServletRequest request){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    Long memberId = memberVo.getId();
    return payLogService.createNative(memberId);
  }
}
