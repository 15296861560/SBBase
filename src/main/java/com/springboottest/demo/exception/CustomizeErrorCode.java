package com.springboottest.demo.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {//使用接口的形式是为了防止后面异常多了的时候业务爆炸，即为了方便后面异常分类

    QUESTION_NOT_FOUND("你找的问题不存在，请换一个试试吧!");

    @Override
    public String getMessage(){
        return message;
    }
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}

