package com.springboottest.demo.controller;


import com.springboottest.demo.dto.CommentCreateDTO;
import com.springboottest.demo.dto.ResultDTO;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.mapper.CommentMapper;
import com.springboottest.demo.model.Comment;
import com.springboottest.demo.model.User;
import com.springboottest.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody//把页面转化成其它结构
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }


        Comment comment=new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);
        commentService.insert(comment);

        return ResultDTO.okOf();

    }
}
