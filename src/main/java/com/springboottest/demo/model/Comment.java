package com.springboottest.demo.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;


}
