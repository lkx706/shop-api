package com.fh.util;

public class KeyUtil {

  public static final int SECONDS = 30*60;

  public static final String MEMBER = "member";

  public static String buildMemberKey(String uuid,Long memberId){
    return "member:"+uuid+":"+memberId;
  }

  public static String buildCartKey(Long memberId) {
    return "cart:"+memberId;
  }

  public static String buildStockLessKey(Long memberId) {
    return "order:stock:less"+memberId;
  }

  public static String buildOrderKey(Long memberId) {
    return "order:success"+memberId;
  }

  public static String buildPayLogKey(Long memberId) {
    return "payLog:"+memberId;
  }

  public static String ORDER_FAILED(Long memberId) {
    return "order:error"+memberId;
  }


  public interface OrderStatus{
    int WAIT_PAY = 10;
    int PAY_SUCCESS = 20;
    int SEND_GOODS = 30;
  }
  public interface PayLogStatus{
    int WAIT_PAY = 10;
    int PAY_SUCCESS = 20;
    int SEND_GOODS = 30;
  }

}
