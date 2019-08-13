package com.springboottest.demo.controller;

import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")//get方法给你页面
    public String publish(){
        return "publish" ;
    }
    @PostMapping("/publish")// post方法给你请求
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //校验逻辑（可在前端通过js校验）
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";

        }
        if (description==null||description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0)//cookie不为null时
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                //通过token获取User对象
                user = userMapper.findByToken(token);
                if (user != null) {//user不为空则将user信息存入session
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登陆");
            return "publish";
        }

        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/ ";
    }
}
