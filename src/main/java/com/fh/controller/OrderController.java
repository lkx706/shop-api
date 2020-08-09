package com.fh.controller;

import com.fh.annotation.Check;
import com.fh.model.ResponseEnum;
import com.fh.model.ServerResponse;
import com.fh.param.OrderParam;
import com.fh.service.OrderService;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
@Api(tags = "生成订单接口")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping("generateOrder")
  @Check
  @ApiOperation("生成订单")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
  })
  private ServerResponse generateOrder(HttpServletRequest request, OrderParam orderParam){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    Long memberId = memberVo.getId();
    orderParam.setMemberId(memberId);
    return orderService.generateOrder(orderParam);
  }

  @GetMapping("getOrderMessage")
  @Check
  @ApiOperation("获取标识")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
  })
  public ServerResponse getOrderMessage(HttpServletRequest request){
    MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
    Long memberId = memberVo.getId();
    if (JedisUtil.exist(KeyUtil.buildStockLessKey(memberId))){
      //证明库存不足
      return ServerResponse.success(ResponseEnum.CART_STOCK_NOT);
    }
    if (JedisUtil.exist(KeyUtil.buildOrderKey(memberId))){
      //证明下单成功
      return ServerResponse.success();
    }
    if (JedisUtil.exist(KeyUtil.ORDER_FAILED(memberId))){
      //证明下单失败
      return ServerResponse.success(ResponseEnum.ORDER_FAILED);
    }
      //证明正在等待
      return ServerResponse.success(ResponseEnum.CART_IS_PAIDUI);
  }
}
