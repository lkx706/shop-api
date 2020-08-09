package com.fh.controller;
import com.fh.annotation.Check;
import com.fh.model.Brand;
import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ServerResponse;
import com.fh.service.MemberService;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/member")
@Api(tags = "会员接口")
public class MemberController {
  @Autowired
  private MemberService memberService;

  @PostMapping
  public ServerResponse addMember(Member member) throws Exception {
    return memberService.addMember(member);
  }

  @RequestMapping("queryMemberName")
  public ServerResponse queryMemberName(String memberName){
    return memberService.queryMemberName(memberName);
  }

  @PostMapping("login")
  @ApiOperation("会员登陆接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberName",value = "会员名",type = "string",required = true,paramType = "query"),
    @ApiImplicitParam(name = "memberPwd",value = "密码",type = "string",required = true,paramType = "query")
  })
  public ServerResponse login(String memberName,String memberPwd){
    return memberService.login(memberName,memberPwd);
  }
  @RequestMapping("initLogin")
  @Check
  @ApiOperation("显示会员登陆名字")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
  })
  public ServerResponse initLogin(HttpServletRequest request){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    return ServerResponse.success(memberVo);
  }
  @RequestMapping("tuichu")
  @Check
  public ServerResponse tuichu(HttpServletRequest request){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    String uuid = memberVo.getUuid();
    Long id = memberVo.getId();
    JedisUtil.del(KeyUtil.buildMemberKey(uuid,id));
    return ServerResponse.success();
  }
}
