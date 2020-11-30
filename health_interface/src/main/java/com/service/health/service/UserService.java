package com.service.health.service;

import com.service.health.pojo.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User findByUsername(String username) throws UsernameNotFoundException;
}
