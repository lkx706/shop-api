package com.fh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.annotation.Check;
import com.fh.model.Cart;
import com.fh.model.ServerResponse;
import com.fh.service.CartService;
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
@RequestMapping("api/cart")
@Api(tags = "购物车接口")
public class CartController {

  @Autowired
  private CartService cartService;

    @PostMapping("addItem")
    @Check
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth",value = "头信息",type ="string",required = true,paramType = "header"),
      @ApiImplicitParam(name = "goodsId",value = "商品id",type ="long",required = true,paramType = "query"),
      @ApiImplicitParam(name = "num",value = "商品数量",type ="int",required = true,paramType = "query")
    })
    public ServerResponse addItem(HttpServletRequest request,Long goodsId,int num){
      MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
      Long memberId = memberVo.getId();
      return cartService.addItem(memberId,goodsId,num);
    }

    @GetMapping("findItemList")
    @Check
    @ApiOperation("查询购物车商品")
    @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
    })
    public ServerResponse findItemList(HttpServletRequest request){
      MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
      Long memberId = memberVo.getId();
      return cartService.findItemList(memberId);
    }
    @PostMapping("deleteCart")
    @Check
    @ApiOperation("删除购物车指定商品")
    @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header"),
      @ApiImplicitParam(name = "goodsId",value = "商品id",type = "long",required = true,paramType = "query")
    })
    public ServerResponse deleteCart(HttpServletRequest request,Long goodsId){
      MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
      Long memberId = memberVo.getId();
      return cartService.deleteCart(memberId,goodsId);
    }

    @GetMapping("findCartCount")
    @Check
    public ServerResponse findCartCount(HttpServletRequest request){
      MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
      Long memberId = memberVo.getId();
      String cartKey = KeyUtil.buildCartKey(memberId);
      String cartJson = JedisUtil.get(cartKey);
      Cart cart = JSONObject.parseObject(cartJson, Cart.class);
      int cartTotalNum = cart.getTotalNum();
      return ServerResponse.success(cartTotalNum);
    }
    @PostMapping("deleteAllCart")
    @Check
    public ServerResponse deleteAllCart(@RequestParam("goodsIds[]")List<Long> goodsIds,HttpServletRequest request){
      MemberVo memberVo = (MemberVo) request.getAttribute(KeyUtil.MEMBER);
      Long memberId = memberVo.getId();
      return cartService.deleteAllCart(goodsIds,memberId);
    }

}
