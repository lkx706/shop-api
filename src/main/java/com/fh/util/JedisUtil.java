package com.fh.util;

import redis.clients.jedis.Jedis;

public class JedisUtil {

  public static void expire(String key,int seconds){
    Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
    jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
     jedis.expire(key,seconds);
  }

  public static boolean exist(String key){
    Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
    jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
    return jedis.exists(key);
  }

    public static void set(String key,String list){
      Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
      jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
      jedis.set(key,list);
    }
  public static void setEx(String key,Integer time,String list){
    Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
    jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
    jedis.setex(key,time,list);
  }

    public static String get(String key){
      Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
      jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
      String s = jedis.get(key);
      return s;
    }
    public static void del(String key){
      Jedis jedis = new Jedis("192.168.17.132",6379);//设置地址和端口
      jedis.auth(String.valueOf(123456));//如果redis服务器配置了需要密码，此处必须设置
      jedis.del(key);
    }

}
