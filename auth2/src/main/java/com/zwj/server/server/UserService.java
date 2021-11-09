package com.zwj.server.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwj.entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    /**
     * 根据用户名查询用户相关信息
     * @param name
     * @return
     */
    UserEntity findUserInfo(String name);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    UserEntity findByUserName(String username);
}
