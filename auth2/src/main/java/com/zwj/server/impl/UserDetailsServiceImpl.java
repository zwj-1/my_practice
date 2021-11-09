package com.zwj.server.impl;


import com.zwj.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.zwj.server.server.UserService;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 获取登录用户信息
        UserEntity userEntity = userService.findByUserName(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("用户:" + username + "不存在");
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getAuthorities());
    }
}