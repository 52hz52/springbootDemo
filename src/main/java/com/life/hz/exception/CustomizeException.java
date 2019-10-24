package com.life.hz.exception;

public class CustomizeException extends RuntimeException {
    String message;
    Integer code;


    public CustomizeException(ICustomizeExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.code=exceptionCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
