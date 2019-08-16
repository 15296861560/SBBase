package com.springboottest.demo.service;


import com.springboottest.demo.enums.CommmentTypeEnum;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import com.springboottest.demo.mapper.CommentMapper;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.model.Comment;
import com.springboottest.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional//自动为整个方法体加上一个事务
    public void insert(Comment comment) {
        if (comment.getParentId() == null||comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null||!CommmentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==CommmentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByParentId(comment.getParentId());
            if (dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateComment(question);

        }

    }
}
