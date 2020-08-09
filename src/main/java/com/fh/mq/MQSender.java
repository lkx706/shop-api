package com.fh.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//启动扫描
@Component
public class MQSender {
  @Autowired
  private AmqpTemplate amqpTemplate;

  public void  sendGoodsMessage(GoodsMessage goodsMessage){
    String goodsMessage1 = JSONObject.toJSONString(goodsMessage);
    amqpTemplate.convertAndSend("goodsExchange","goods",goodsMessage1);
  }
}
