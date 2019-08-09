package com.springboottest.demo.controller;

import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    //注入usermapper
    @Autowired
    private UserMapper userMapper;
    //地址映射
    @GetMapping("/index")
    public String index(HttpServletRequest request){
        //通过request获取Cookie
        Cookie[] cookies = request.getCookies();
        //从Cookie中获取token
        for (Cookie cookie:cookies) {//需添加一个cookie为null时的判断
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                //通过token获取User对象
                User user=userMapper.findByToken(token);
                if (user!=null){//user不为空则将user信息存入session
                    request.getSession().setAttribute("user",user);
                }
                break;
            }


        }


        return "index";

    }
}
