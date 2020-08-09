package com.fh.config;

import com.fh.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

  public void addInterceptors(InterceptorRegistry registry){
       registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/**");
  }

  @Bean
  public LoginInterceptor loginInterceptor(){
    return new LoginInterceptor();
  }
}
