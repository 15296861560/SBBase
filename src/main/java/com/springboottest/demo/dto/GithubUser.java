package com.springboottest.demo.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;//描述
    private String avatarUrl;//头像url


}
