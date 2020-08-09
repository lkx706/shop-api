package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.annotation.Check;
import com.fh.exception.GlobalException;
import com.fh.model.ResponseEnum;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.fh.util.MD5;
import com.fh.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    //处理跨域
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");

    //处理自定义的请求头
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type");

    //处理options请求
    String method1 = request.getMethod();
    if (method1.equalsIgnoreCase("options")){
      //组织访问
          return false;
    }

    //把handler转成HandlerMethod
    HandlerMethod handlerMethod = (HandlerMethod) handler;

    Method method = handlerMethod.getMethod();
    //验证路径中是否包含Check注解
    if(!method.isAnnotationPresent(Check.class)){
        return true;
    }
    //获取请求头信息
    String header = request.getHeader("x-auth");

    if(StringUtils.isEmpty(header)){
        throw new GlobalException(ResponseEnum.TOKEN_IS_NULL);
    }
    String[] split = header.split("\\.");

    if (split.length!=2){
      throw new GlobalException(ResponseEnum.TOKEN_IS_NOT);
    }
    String memberBase64Json = split[0];
    String signBase64 = split[1];

    String newSign = MD5.sign(memberBase64Json, MD5.SECRET);
    String newSignBase64 = Base64.getEncoder().encodeToString(newSign.getBytes("utf-8"));

    if(!newSignBase64.equals(signBase64)){
      throw new GlobalException(ResponseEnum.TOKEN_IS_CHANGED);
    }
    //判断超时
    String memberJson = new String(Base64.getDecoder().decode(memberBase64Json), "utf-8");

    MemberVo memberVo = JSONObject.parseObject(memberJson, MemberVo.class);

    Long id = memberVo.getId();

    String uuid = memberVo.getUuid();

    boolean exist = JedisUtil.exist(KeyUtil.buildMemberKey(uuid, id));

    if(!exist){
      throw new GlobalException(ResponseEnum.TOKEN_IS_EXPIRED);
    }

    //续命
    JedisUtil.expire(KeyUtil.buildMemberKey(uuid,id),KeyUtil.SECONDS);

    //存入request
    request.setAttribute(KeyUtil.MEMBER,memberVo);

    //放行
    return true;
  }

}
