package com.springboottest.demo.controller;

import com.springboottest.demo.dto.AccessTokenDTO;
import com.springboottest.demo.dto.GithubUser;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.User;
import com.springboottest.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    //从配置文件中注入相应值
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    //注入UserMapper
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state ,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirectUri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//通过provider获取token
//        String accessToken = "c66ddb7322cab60eaf9c468f7334807a2b7f5f7e";//bug已修复，先注释掉后期无异常将直接删除
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            //加载用户数据
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //将User信息注入数据库
            userMapper.insert(user);
            //将token写入cookie
            response.addCookie(new Cookie("token",token));
            //重定向回首页
            return "redirect:index";//或者写成return "redirect:/"返回根目录，即首页
        }else {
            //登录失败，重新登陆
            return "redirect:index";
        }
    }
}

