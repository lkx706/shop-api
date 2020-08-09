package com.fh;
import com.fh.mq.GoodsMessage;
import com.fh.mq.MQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootTest
class ShopBootApplicationTests {

  @Autowired
  JavaMailSenderImpl mailSender;

  @Test
  void contextLoads() {

    /*SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setSubject("李凯信");
    mailMessage.setText("李凯信");
    mailMessage.setTo("532028476@qq.com");
    mailMessage.setFrom("2642476369@qq.com");
    mailSender.send(mailMessage);*/
  }

  @Autowired
  private MQSender mqSender;

  @Test
  public void test2(){

    for (int i = 1; i <=5 ; i++) {
      GoodsMessage goodsMessage = new GoodsMessage();
      goodsMessage.setId(i);
      goodsMessage.setPrice("100"+i);
      goodsMessage.setStock(i*10);
      mqSender.sendGoodsMessage(goodsMessage);
    }
  }

}
