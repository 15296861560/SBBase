package com.springboottest.demo.service;


import com.springboottest.demo.dto.CommentDTO;
import com.springboottest.demo.enums.CommmentTypeEnum;
import com.springboottest.demo.enums.NotificationStatusEnum;
import com.springboottest.demo.enums.NotificationTypeEnum;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import com.springboottest.demo.mapper.CommentMapper;
import com.springboottest.demo.mapper.NotificationMapper;
import com.springboottest.demo.mapper.QuestionMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Comment;
import com.springboottest.demo.model.Notification;
import com.springboottest.demo.model.Question;
import com.springboottest.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

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
            Comment dbcomment = commentMapper.selectId(comment.getParentId());//查看数据库中是否有该评论所评论的评论
            if (dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //查看数据库中该评论所评论的评论，所评论的问题是否存在
            Question question = questionMapper.selectById(dbcomment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //创建通知
            createNotify(comment,dbcomment.getCommentator(),NotificationTypeEnum.REPLY_COMMENT,question.getId());

        }else {
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateComment(question);
            //创建通知
            createNotify(comment,question.getCreator(),NotificationTypeEnum.REPLY_QUESTION,question.getId());


        }

    }

    private void createNotify(Comment comment, Integer recevier,NotificationTypeEnum typeEnum,Integer outerId) {
        //添加新通知的数据
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(typeEnum.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setRecevier(recevier);
        notification.setNotifierName(userMapper.findById(notification.getNotifier()).getName());
        notification.setOuterTitle(questionMapper.getById(notification.getOuterId()).getTitle());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByQuestionId(Integer questionId,Integer type) {
        List<Comment> comments = commentMapper.selectByParentIdAndType(questionId,type);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //map(遍历一下返回一个结果集），collect（把结果集算一下），Collectors.toSet(把结果集变成Set对象)
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());//java8语法
        List<Integer>userIds=new ArrayList();
        //将获取的评论人id取出存入userIds
        userIds.addAll(commentators);

        //根据评论人id获取评论人信息并存入users
        List<User>users=new ArrayList();
        for(Integer id : userIds){
            User user=userMapper.findById(id);
            users.add(user);
        }
        //将userIds和users组成map存入userMap中
        Map<Integer,User>userMap=users.stream().collect(Collectors.toMap(user->user.getId(),user->user));
        //将comment转换成commentDTO
        List<CommentDTO>commentDTOS=comments.stream().map(comment -> {
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
