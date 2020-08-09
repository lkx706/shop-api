package com.fh.mq;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//可以注入
@Configuration
public class MQConfig {

  public static final String ORDER_EXCHANGE = "orderExchange";
  public static final String ORDER_QUEUE = "orderQueue";
  public static final String ORDER_ROUTEKEY = "order";

  @Bean
  public DirectExchange orderExchange(){
    return new DirectExchange(ORDER_EXCHANGE,true,false);
  }
  @Bean
  public Queue orderQueue(){
    return new Queue(ORDER_QUEUE,true);
  }
  @Bean
  public Binding orderBinding(){
    return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTEKEY);
  }


  @Bean
  public DirectExchange goodsExchange(){
    return new DirectExchange("goodsExchange",true,false);
  }

  @Bean
  public Queue goodsQueue(){
    return new Queue("goodsQueue",true);
  }

  @Bean
  public Binding goodsBinding(){
    return BindingBuilder.bind(goodsQueue()).to(goodsExchange()).with("goods");
  }
}
