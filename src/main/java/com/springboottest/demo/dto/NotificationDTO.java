package com.springboottest.demo.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer status;
    private Long gmtCreate;
    private Integer notifier;
    private Integer outerId;
    private String notifierName;
    private String outerTitle;
    private Integer type;
    private String typeName;
}
