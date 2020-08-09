package com.fh.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {


  @Value("${spring.mail.host}")
  private String url;
  @Value("${spring.mail.username}")
  private String memberName;
  @Value("${spring.mail.password}")
  private String memberPwd;

  public void createMail(String mail,String text){
    Properties prop = new Properties();
    prop.setProperty("mail.host", url);
    prop.setProperty("mail.transport.protocol", "smtp");
    prop.setProperty("mail.smtp.auth", "true");
    //使用JavaMail发送邮件的5个步骤
    //1、创建session
    Session session = Session.getInstance(prop);
    //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
    session.setDebug(true);
    //2、通过session得到transport对象
    Transport ts = null;
    try {
      ts = session.getTransport();
      //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
      ts.connect(url, memberName, memberPwd);
      //4、创建邮件
      MimeMessage message = new MimeMessage(session);
      //指明邮件的发件人
      message.setFrom(new InternetAddress(memberName));
      //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
      //邮件的标题
      message.setSubject("注册会员");
      //邮件的文本内容
      message.setContent(text, "text/html;charset=UTF-8");
      //5、发送邮件
      ts.sendMessage(message, message.getAllRecipients());
    } catch (MessagingException e) {
      e.printStackTrace();
    } finally {
      if (ts!=null){
        try {
          ts.close();
        } catch (MessagingException e) {
          e.printStackTrace();
        }
      }
    }

  }



}
