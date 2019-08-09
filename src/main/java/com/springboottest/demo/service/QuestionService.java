package com.springboottest.demo.service;

import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//当一个请求需要组装两个数据库对象的时候，需要一个service这样一个中间层去组装
@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            User user=userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的所有属性拷贝到questionDTO上面
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
