package com.springboottest.demo.mapper;

import com.springboottest.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")//分页查询
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);//传非对象数据的时候需要自己添加一个映射

    @Select("select count(1) from question")//查询问题总数
    Integer questionCount();
}
