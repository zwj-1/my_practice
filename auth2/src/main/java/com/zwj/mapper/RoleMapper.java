package com.zwj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.zwj.entity.RoleEntity;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleEntity> {
    /**
     * 根据用户名称查询角色集合
     *
     * @param userName
     * @return
     */
    @Select(" SELECT t3.* FROM sys_user t1 LEFT JOIN sys_user_role t2 ON t1.`id`=t2.`user_id` \n" +
            "LEFT JOIN sys_role t3 ON t2.`role_id`=t3.`id` WHERE t1.`username`=#{userName}")
    List<RoleEntity> findRolesByUserName(@Param("userName") String userName);

}