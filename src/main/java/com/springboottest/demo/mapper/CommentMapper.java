package com.springboottest.demo.mapper;

import com.springboottest.demo.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CommentMapper {

    @Insert("insert into comment(id,parent_id,type,gmt_create,gmt_modified,commentator,like_count,content)values(#{id},#{parentId},#{type},#{gmtCreate},#{gmtModified},#{commentator},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parentId}")
    Comment selectByParentId(@Param(value = "parentId") Integer parentId);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type} order by gmt_create desc")
    List<Comment> selectByParentIdAndType(@Param(value = "parentId") Integer parentId, @Param(value = "type") Integer type);

    @Select("select * from comment where id=#{id}")
    Comment selectId(@Param(value = "id")Integer id);
}
