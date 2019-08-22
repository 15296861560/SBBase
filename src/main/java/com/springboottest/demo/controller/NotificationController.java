package com.springboottest.demo.controller;


import com.springboottest.demo.dto.NotificationDTO;
import com.springboottest.demo.dto.PageDTO;
import com.springboottest.demo.enums.NotificationTypeEnum;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.User;
import com.springboottest.demo.service.NotificationService;
import com.springboottest.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")//访问profile的时候是哪个“action”行为
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Integer id){


        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO=notificationService.read(id,user);

        return "redirect:/question/"+notificationDTO.getOuterId();
    }
}
