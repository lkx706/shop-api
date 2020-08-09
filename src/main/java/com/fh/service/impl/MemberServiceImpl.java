package com.fh.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.MemberMapper;
import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ResponseEnum;
import com.fh.model.ServerResponse;
import com.fh.service.MemberService;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.fh.util.MD5;
import com.fh.util.MailUtil;
import com.fh.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {
  @Autowired
  private MemberMapper memberMapper;

  @Override
  public ServerResponse addMember(Member member){

    String memberName = member.getMemberName();
    String memberPwd = member.getMemberPwd();
    String mail = member.getMail();
    if (StringUtils.isEmpty(memberName)|| StringUtils.isEmpty(memberPwd)||StringUtils.isEmpty(mail)){
          return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_NULL);
    }
    QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("memberName", memberName);
    Member member1 = memberMapper.selectOne(queryWrapper);

    if (member1!=null){
      return ServerResponse.error(ResponseEnum.MEMBER_USERNAME_IS_EXISTED);
    }
    memberMapper.insert(member);
    return ServerResponse.success();
  }

  @Override
  public ServerResponse queryMemberName(String memberName) {
    if (StringUtils.isEmpty(memberName)){
      return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_NULL);
    }
    QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("memberName", memberName);
    Member member1 = memberMapper.selectOne(queryWrapper);

    if (member1!=null){
      return ServerResponse.error(ResponseEnum.MEMBER_USERNAME_IS_EXISTED);
    }
    return ServerResponse.success();
  }

  @Override
  public ServerResponse login(String memberName, String memberPwd) {
    //判断非空
    if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(memberPwd)){
      return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_NULL);
    }
    //判断用户是否存在
    QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("memberName",memberName);
    Member member = memberMapper.selectOne(queryWrapper);
    if(member==null){
      return ServerResponse.error(ResponseEnum.MEMBER_USERNAME_NO_EXISTED);
    }
    if(!memberPwd.equals(member.getMemberPwd())){
      return ServerResponse.error(ResponseEnum.PWD_ERROR);
    }
    //生成用户信息
    MemberVo memberVo = new MemberVo();
    Long memberId = member.getMemberId();
    memberVo.setId(memberId);
    memberVo.setMemberName(member.getMemberName());
    memberVo.setRealName(member.getRealName());
    String uuid = UUID.randomUUID().toString();
    memberVo.setUuid(uuid);

    //将java对象转为JSON字符串
    String memberJson = JSONObject.toJSONString(memberVo);

    try {
      String string = Base64.getEncoder().encodeToString(memberJson.getBytes("utf-8"));
      String sign = MD5.sign(string, MD5.SECRET);
      String encode = Base64.getEncoder().encodeToString(sign.getBytes("utf-8"));

      JedisUtil.setEx(KeyUtil.buildMemberKey(uuid,memberId),KeyUtil.SECONDS,"");

      return ServerResponse.success(string+"."+encode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
     return ServerResponse.error();
    }
  }
}
