package com.springboottest.demo.dto;

import com.springboottest.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;

}
