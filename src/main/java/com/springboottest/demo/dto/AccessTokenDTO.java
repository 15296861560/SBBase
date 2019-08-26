package com.springboottest.demo.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {//传到github那边的数据名不支持驼峰标识，所以这里用下划线方式命名
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
