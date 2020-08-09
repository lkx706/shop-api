package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.config.WXConfig;
import com.fh.mapper.PayLogMapper;
import com.fh.model.PayLog;
import com.fh.model.ServerResponse;
import com.fh.service.PayLogService;
import com.fh.util.BigDecimalUtil;
import com.fh.util.JedisUtil;
import com.fh.util.KeyUtil;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayLogServiceImpl implements PayLogService {
  @Autowired
  private PayLogMapper payLogMapper;

  @Override
  public ServerResponse createNative(Long memberId) {
    String payLogJson = JedisUtil.get(KeyUtil.buildPayLogKey(memberId));
    PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
    String outTradeNo = payLog.getOutTradeNo();
    BigDecimal payMoney = payLog.getPayMoney();
    try {
      WXConfig wxConfig = new WXConfig();
      WXPay wxPay = new WXPay(wxConfig);
      HashMap<String, String> data = new HashMap<>();
      data.put("body","飞狐乐购");
      int mul = BigDecimalUtil.mul(payMoney.toString(), "100").intValue();
      //订单号
      data.put("out_trade_no",outTradeNo);
      data.put("total_fee",mul+"");
      data.put("notify_url", "http://www.example.com/wxpay/notify");
      data.put("trade_type","NATIVE");
      Map<String, String> resp = wxPay.unifiedOrder(data);
      System.out.println(resp);

      String return_code = resp.get("return_code");
      String return_msg = resp.get("return_msg");
      if (!return_code.equals("SUCCESS")){
          return ServerResponse.error(99999,return_msg);
      }
      String result_code = resp.get("result_code");
      String err_code_des = resp.get("err_code_des");
      if (!result_code.equals("SUCCESS")){
          return ServerResponse.error(99999,err_code_des);
      }
      String code_url = resp.get("code_url");
      HashMap<String, String> hashMap = new HashMap<>();
      hashMap.put("codeUrl",code_url);
      hashMap.put("orderId",payLog.getOrderId());
      hashMap.put("totalPrice",payMoney.toString());
      return ServerResponse.success(hashMap);
    } catch (Exception e) {
      e.printStackTrace();
      return ServerResponse.error();
    }
  }
}
