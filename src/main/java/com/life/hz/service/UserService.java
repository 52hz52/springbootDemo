package com.life.hz.service;

import com.life.hz.mapper.UserMapper;
import com.life.hz.model.User;
import com.life.hz.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);

     //   User dbuser = userMapper.findByAccountId(user.getAccountId());
       if(users.size() == 0){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           //更新
           User dbUser = users.get(0);

           User updateUser = new User();
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setToken(user.getToken());
           updateUser.setBio(user.getBio());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setBio(user.getBio());

           UserExample example = new UserExample();
           example.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser, example);

       }
    }
}
