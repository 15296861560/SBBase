package com.springboottest.demo.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;//评论id
    private Integer notifier;//提醒者
    private Integer recevier;//接收者
    private Integer outerId;//评论问题id
    private Integer type;
    private Integer status;
    private Long gmtCreate;
    private String notifierName;
    private String outerTitle;
}
