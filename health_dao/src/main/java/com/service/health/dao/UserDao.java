package com.service.health.dao;

import com.service.health.pojo.User;

public interface UserDao {
    User findByName(String username);
}
