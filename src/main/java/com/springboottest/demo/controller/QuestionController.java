package com.springboottest.demo.controller;

import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public String question(@PathVariable(name = "questionId")Integer questionId,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(questionId);
        model.addAttribute("question",questionDTO);

        return "question";
    }
}
