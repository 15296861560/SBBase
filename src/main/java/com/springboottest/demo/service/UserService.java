package com.springboottest.demo.service;


import com.springboottest.demo.mapper.UserMapper;
import com.springboottest.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        User dbuser=userMapper.findByAccountId(user.getAccountId());
        if ((dbuser==null)){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            //将新用户信息注入数据库
            userMapper.insert(user);
        }else {
            //更新老用户
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        }

    }
}
