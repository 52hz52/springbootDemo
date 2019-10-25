package com.life.hz.exception;

public enum CustomizeExceptionCode implements ICustomizeExceptionCode {


    QUESTION_NOT_FOUND(2001 ,"你访问的问题已经不存在了,要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002 ,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"没有登录,不能进行评论..请登录"),
    SYS_ERROR(2004,"服务小哥去追寻诗和远方了...请稍后在试"),
    TYPE_PARAM_WORNG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在,要不换个试试?"),
    COMMENT_IS_FOUND(2007,"回复的评论不能为空!")
    ;

    private String message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }

    CustomizeExceptionCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }



}
