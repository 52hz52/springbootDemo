package com.life.hz.exception;

public enum CustomizeExceptionCode implements ICustomizeExceptionCode {


    QUESTION_NOT_FOUND("你访问的问题已经不存在了,要不要换个试试?");

    private String message;

    @Override
    public String getMessage(){
        return message;
    }

    CustomizeExceptionCode(String message) {
        this.message = message;
    }
}
