package com.springboottest.demo.mapper;

import com.springboottest.demo.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CommentMapper {

    @Insert("insert into comment(id,parent_id,type,gmt_create,gmt_modified,commentator,like_count,content)values(#{id},#{parentId},#{type},#{gmtCreate},#{gmtModified},#{commentator},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parentId}")
    Comment selectByParentId(@Param(value = "parentId") Integer parentId);
}
