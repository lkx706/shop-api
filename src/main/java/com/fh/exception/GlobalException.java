package com.fh.exception;

import com.fh.model.ResponseEnum;

public class GlobalException extends RuntimeException {

  private ResponseEnum responseEnum;

  public GlobalException(ResponseEnum responseEnum){
    this.responseEnum = responseEnum;
  }

  public ResponseEnum getResponseEnum(){
    return responseEnum;
  }

}
