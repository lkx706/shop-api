package com.fh.exception;

import com.fh.model.ResponseEnum;
import com.fh.model.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler {

  @ResponseBody
  @ExceptionHandler(GlobalException.class)
  public ServerResponse handlerGlobleException(GlobalException e){
    ResponseEnum responseEnum = e.getResponseEnum();
    return ServerResponse.error(responseEnum);
  }

}
