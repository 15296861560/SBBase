package com.springboottest.demo.controller;

import com.springboottest.demo.dto.PageDTO;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.User;
import com.springboottest.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")//访问profile的时候是哪个“action”行为
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name="page", defaultValue = "1")Integer page,
                          @RequestParam(name="size",defaultValue = "5")Integer size){


        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }

        if ("questions".equals(action)){//当action是questions的时候
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PageDTO pageDTO=questionService.list(user.getId(),page,size);
        model.addAttribute("pageDTO",pageDTO);


        return "profile";
    }
}
