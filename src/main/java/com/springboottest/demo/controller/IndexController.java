package com.springboottest.demo.controller;

import com.springboottest.demo.dto.PageDTO;
import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import com.springboottest.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    //注入usermapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    //地址映射
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model,
                        @RequestParam(name="page",defaultValue = "1")Integer page,//通过@RequestParam注解获取名字为page的参数默认值为1类型为Integer
                        @RequestParam(name="size",defaultValue = "5")Integer size){

        PageDTO pageDTO=questionService.list(page,size);
        model.addAttribute("pageDTO",pageDTO);

        return "index";

    }
}
