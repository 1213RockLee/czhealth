package com.service.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.service.health.dao.UserDao;
import com.service.health.pojo.User;
import com.service.health.service.SetmealService;
import com.service.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByUsername(String username) {
     User user= userDao.findByName(username);
        return user;
    }
}
