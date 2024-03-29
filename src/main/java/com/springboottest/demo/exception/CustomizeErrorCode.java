package com.springboottest.demo.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {//使用接口的形式是为了防止后面异常多了的时候业务爆炸，即为了方便后面异常分类

    QUESTION_NOT_FOUND(2001,"您找的问题不存在，请换一个试试吧!"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复!"),
    NO_LOGIN(2003,"当前操作需要登陆，请登陆都重试"),
    SYS_ERROR(2004,"服务器出问题了，要不然稍后再试一下吧..."),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"您找的评论不存在，请换一个试试吧!"),
    COMMENT_IS_EMPTY(2007,"发布的评论不能为空，请重新发布!"),
    READ_NOTIFICATION_FAIL(2008,"大哥你是想读别人的信息么..."),
    NOTIFICATION_NOT_FOUND(2008,"您的消息不翼而飞了..."),
    FILE_UPLOAD_FAIL(2008,"文件上传失败");

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

