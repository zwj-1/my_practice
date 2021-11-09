package com.zwj.mapper;

import com.zwj.entity.PermissionEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface PermissionMapper {

    @Select(" select * from sys_permission ")
    List<PermissionEntity> findAllPermission();

}
