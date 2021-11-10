package com.zwj.entity;

import lombok.Data;

import java.io.Serializable;

// 角色信息表
@Data
public class RoleEntity implements Serializable {
	private Integer id;
	private String roleName;
	private String roleDesc;
}
