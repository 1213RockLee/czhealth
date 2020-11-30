package com.service.health.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.service.health.pojo.Permission;
import com.service.health.pojo.Role;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserService implements UserDetailsService {
    @Reference
    com.service.health.service.UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws AccessDeniedException {
        //通过方法返回一个他的实现类的对象,选择User,等下要注意两个user的区别,一个要使用权限定名
        com.service.health.pojo.User user=userService.findByUsername(username);
        //获得对象之后就(需要注意的是,这个时候是登陆成功之后,对该账号的权限分发的过程,验证密码不是在这个地方)返回对象的详细信息即可
        //需要返回的接口，本身是个接口，找了一个实现类之后，发现他的实现类中也有一个接口属性，那就继续找实现类（这个实现类有个构造方法
        // 可以根据字符串来构建对象，那就将角色和权限都设置到这个里面）
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> set = new HashSet();
        roles.forEach(role -> {
            //使用一波新特性装波比
            set.add(new SimpleGrantedAuthority(role.getKeyword()));
            role.getPermissions().forEach(permission -> {
                set.add(new SimpleGrantedAuthority(permission.getKeyword()));
            });
        });
        return new User(username,user.getPassword(),set);
    }

}
