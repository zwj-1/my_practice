package com.zwj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwj.entity.PermissionEntity;
import com.zwj.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.zwj.entity.RoleEntity;

import java.util.Set;

public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 根据用户名称查询
     *
     * @param userName
     * @return
     */
    @Select(" select * from sys_user where username = #{userName}")
    UserEntity findByUserName(@Param("userName") String userName);

    /**
     * 查询用户的权限根据用户查询权限
     *
     * @param userName
     * @return
     */
    @Select(" select permission.* from sys_user user" + " inner join sys_user_role user_role"
            + " on user.id = user_role.user_id inner join "
            + "sys_role_permission role_permission on user_role.role_id = role_permission.role_id "
            + " inner join sys_permission permission on role_permission.perm_id = permission.id where user.username = #{userName};")
    Set<PermissionEntity> findPermissionByUsername(@Param("userName") String userName);

    /**
     * 根据用户名称查询角色集合
     *
     * @param userName
     * @return
     */
    @Select(" SELECT t3.* FROM sys_user t1 LEFT JOIN sys_user_role t2 ON t1.`id`=t2.`user_id` \n" +
            "LEFT JOIN sys_role t3 ON t2.`role_id`=t3.`id` WHERE t1.`username`=#{userName}")
    Set<RoleEntity> findRolesByUserName(@Param("userName") String userName);
}