package com.life.hz.exception;

public class CustomizeException extends RuntimeException {
    String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(ICustomizeExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
