package com.springboottest.demo.controller;

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
    @GetMapping("/index")
    public String index(HttpServletRequest request,Model model){
        //通过request获取Cookie
        Cookie[] cookies = request.getCookies();
        //从Cookie中获取token
        if (cookies!=null&&cookies.length!=0) {//cookie不为null时
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //通过token获取User对象
                    User user = userMapper.findByToken(token);
                    if (user != null) {//user不为空则将user信息存入session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO>questionDTOs=questionService.list();
        model.addAttribute("questionDTOs",questionDTOs);

        return "index";

    }
}
