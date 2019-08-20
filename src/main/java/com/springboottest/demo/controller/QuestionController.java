package com.springboottest.demo.controller;

import com.springboottest.demo.dto.CommentDTO;
import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.service.CommentService;
import com.springboottest.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{questionId}")
    public String question(@PathVariable(name = "questionId")Integer questionId,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(questionId);
        List<CommentDTO> commentDTOs=commentService.listByQuestionId(questionId,1);


        questionService.incView(questionDTO);//累加浏览数
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOs);

        return "question";
    }
}
