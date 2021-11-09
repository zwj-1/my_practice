package com.zwj.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwj.entity.PermissionEntity;
import com.zwj.entity.RoleEntity;
import com.zwj.entity.UserEntity;
import com.zwj.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.zwj.server.server.UserService;

import java.util.Set;

/**
 * 用户server
 * @author zwj
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public UserEntity findUserInfo(String name) {
        // 获取用户信息
        UserEntity user = this.baseMapper.findByUserName(name);
        // 获取用户角色信息
        Set<RoleEntity> roleList = this.baseMapper.findRolesByUserName(name);
        // 获取用户权限信息
        Set<PermissionEntity> permissionList = this.baseMapper.findPermissionByUsername(name);

        user.setRoleList(roleList);
        user.setPermissionList(permissionList);
        return user;
    }

    @Override
    public UserEntity findByUserName(String username) {
        return this.baseMapper.findByUserName(username);
    }
}
