package com.springboottest.demo.mapper;

import com.springboottest.demo.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(id,notifier,recevier,outer_id,type,gmt_create,status,notifier_name,outer_title)values(#{id},#{notifier},#{recevier},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("select count(1)  from notification where recevier=#{recevier}")//查询未读消息总数
    Integer notificationCount(@Param("recevier") Integer recevier);

    @Select("select * from notification where recevier=#{recevier} order by gmt_create desc limit #{offset},#{size}  ")
    List<Notification> listByNotifierId(@Param("recevier") Integer recevier, @Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1)  from notification where notifier=#{notifier} and status=0")//查询该用户未读通知总数
    Integer unreadCount(@Param("notifier") Integer notifier);

    @Select("select * from notification where id=#{id}")
    Notification findById(@Param("id")Integer id);

    @Update("update notification set status=1 where id=#{id}")
    void read(@Param("id")Integer id);

    @Select("select * from notification where outer_id=#{outer_id}")
    Notification findByOuterId(@Param("outerId")Integer outerId);
}
