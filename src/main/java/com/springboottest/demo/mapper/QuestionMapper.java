package com.springboottest.demo.mapper;

import com.springboottest.demo.dto.QuestionDTO;
import com.springboottest.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")//分页查询
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);//传非对象数据的时候需要自己添加一个映射

    @Select("select count(1) from question")//查询问题总数
    Integer questionCount();

    @Select("select * from question where creator=#{userId}  limit #{offset},#{size} ")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size")Integer siz);

    @Select("select count(1)  from question where creator=#{userId}")//查询问题总数
    Integer userQuestionCount(@Param("userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    int update(Question question);

    @Update("update question set view_count=#{viewCount}+1 where id=#{id}")
    void updateView(QuestionDTO questionDTO);

    @Update("update question set comment_count=#{commentCount}+1 where id=#{id}")
    void updateComment(Question question);

    @Select("select * from question where id=#{id}")
    Question selectById(@Param(value = "id") Integer id);
}
