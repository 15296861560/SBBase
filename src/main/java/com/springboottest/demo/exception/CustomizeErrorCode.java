package com.springboottest.demo.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {//使用接口的形式是为了防止后面异常多了的时候业务爆炸，即为了方便后面异常分类

    QUESTION_NOT_FOUND(2001,"你找的问题不存在，请换一个试试吧!"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复!"),
    NO_LOGIN(2003,"当前操作需要登陆，请登陆都重试"),
    SYS_ERROR(2004,"服务器出问题了，要不然稍后再试一下吧..."),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你找的评论不存在，请换一个试试吧!");

    private String message;
    private Integer code;



    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    CustomizeErrorCode(String message) {
        this.message = message;
    }
    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
}

