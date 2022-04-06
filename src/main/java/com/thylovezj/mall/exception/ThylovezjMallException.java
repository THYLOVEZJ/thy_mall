package com.thylovezj.mall.exception;

public class ThylovezjMallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public ThylovezjMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ThylovezjMallException(ThylovezjMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
