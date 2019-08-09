package com.springboottest.demo.dto;

import com.springboottest.demo.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id ;
    private String title ;
    private String description ;
    private Long gmtCreate ;
    private Long gmtModified ;
    private Integer creator;
    private Integer commentCount ;
    private Integer viewCount ;
    private Integer likeCount ;
    private String tag;
    private User user;
}
