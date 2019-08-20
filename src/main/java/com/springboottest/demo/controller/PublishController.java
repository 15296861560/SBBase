package com.springboottest.demo.controller;

import com.springboottest.demo.cache.TagCache;
import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import com.springboottest.demo.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId")Integer questionId,
                       Model model){


        QuestionDTO question = questionService.getById(questionId);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",questionId);
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")//get方法给你页面
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish" ;
    }
    @PostMapping("/publish")// post方法给你请求
    public String doPublish(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value ="description",required = false)String description,
            @RequestParam(value ="tag",required = false)String tag,
            @RequestParam(value ="questionId",required = false)Integer questionId,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("question",questionId);

        model.addAttribute("tags", TagCache.get());
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

        String filterValid = TagCache.filterValid(tag);
        if (StringUtils.isNotBlank(filterValid)){
            model.addAttribute("error","输入非法标签:"+filterValid);
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登陆");
            return "publish";
        }

        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(questionId);

        questionService.createOrUpdate(question);
        return "redirect:/ ";
    }
}
