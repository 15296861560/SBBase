package com.springboottest.demo.service;

import com.springboottest.demo.dto.PageDTO;
import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//当一个请求需要组装两个数据库对象的时候，需要一个service这样一个中间层去组装
@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {

        PageDTO<QuestionDTO> pageDTO=new PageDTO();
        Integer totalCount = questionMapper.questionCount();//问题总数
        pageDTO.setPageDTO(totalCount,page,size);


        //手动输入超出范围页码的时候对页码的处理
//        if (page<1){
//            page=1;
//
//        }
//        if (page>pageDTO.getTotalPage()){
//            page=pageDTO.getTotalPage();
//
//        }

        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList =new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的所有属性拷贝到questionDTO上面
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setDataDTOS(questionDTOList);
        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO=new PageDTO();
        Integer totalCount = questionMapper.userQuestionCount(userId);//用户问题总数
        pageDTO.setPageDTO(totalCount,page,size);


        //手动输入超出范围页码的时候对页码的处理
//        if (page<1){
//            page=1;
//
//        }
//        if (page>pageDTO.getTotalPage()){
//            page=pageDTO.getTotalPage();
//
//        }

        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList =new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的所有属性拷贝到questionDTO上面
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setDataDTOS(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){//第一次创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.create(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            int uodate=questionMapper.update(question);//更新
            if (uodate!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(QuestionDTO questionDTO) {
        questionMapper.updateView(questionDTO);
    }
    public void incComment(Question question) {
        questionMapper.updateComment(question);
    }
    public List<Question> listByQuestionTag(Integer id){
        Question question = questionMapper.getById(id);
        //用英文的逗号分隔tag
        String[] tags = StringUtils.split(question.getTag(), ",");
        //用|把刚刚分隔的字符串重新拼接
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        //将question中的tag该为处理后的regexTag，便于执行mapper中关于tag正则表达式的操作
        question.setTag(regexTag);
        return questionMapper.listByQuestionTag(question);}
}
