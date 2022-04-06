package com.thylovezj.mall.exception;

public enum ThylovezjMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码不能太短"),
    NAME_EXIST(10004, "不允许重名"),
    INSERT_FAILED(10005, "插入失败,请重试"),
    SYSTEM_ERROR(20000, "系统异常"),
    WRONG_PASSWORD(10006, "密码错误"),
    UPDATE_FAILED(10008, "更新失败"),
    NEED_LOGIN(10007, "用户未登录"),
    NAME_NOT_NULL(10010, "名字不能为空"),
    CREATE_FAILED(10011, "新增失败"),
    REQUEST_PARAM_ERROR(10012, "参数错误"),
    DELETE_FAILED(10013, "删除失败"),
    NEED_ADMIN(10009, "无管理员权限");
    Integer code;
    String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ThylovezjMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
