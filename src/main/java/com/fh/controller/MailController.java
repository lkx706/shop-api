package com.fh.controller;



import com.fh.util.MailUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
@Api(tags = "发送邮件接口")
public class MailController {
  @Autowired
  private MailUtil mailUtil;

  @PostMapping
  public void mail(String mail){
    String aaa ="注册成功！！";
    mailUtil.createMail(mail,aaa);
  }



}
