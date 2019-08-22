package com.springboottest.demo.service;


import com.springboottest.demo.dto.NotificationDTO;
import com.springboottest.demo.dto.PageDTO;
import com.springboottest.demo.enums.NotificationTypeEnum;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import com.springboottest.demo.mapper.NotificationMapper;
import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.Notification;
import com.springboottest.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;
    public PageDTO list(Integer recevier, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO=new PageDTO();

        Integer totalCount = notificationMapper.notificationCount(recevier);//回复总数
        pageDTO.setPageDTO(totalCount,page,size);



        Integer offset=size*(page-1);
        List<Notification> notifications = notificationMapper.listByNotifierId(recevier,offset,size);

        if (notifications.size()==0){
            return pageDTO;
        }

        //建立notificationDTO并将其存入notificationDTOLis中
        List<NotificationDTO> notificationDTOList =new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);//把notification的属性拷贝到notificationDTO上面
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        pageDTO.setDataDTOS(notificationDTOList);
        return pageDTO;
    }

    public Integer unreadCount(Integer notifier) {
        return notificationMapper.unreadCount(notifier);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification=notificationMapper.findById(id);
        if (notification==null){//找不到消息
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getRecevier().equals(user.getId())){//登陆用户id与接收者id不符合
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);//把notification的属性拷贝到notificationDTO上面
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        notificationMapper.read(id);

        return notificationDTO;
    }
}
