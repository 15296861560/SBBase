package com.springboottest.demo.model;

import lombok.Data;

//自动添加bean的方法
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
