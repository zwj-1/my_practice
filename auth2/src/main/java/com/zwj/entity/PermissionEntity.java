package com.zwj.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionEntity implements Serializable {

	private Integer id;
	// 权限名称
	private String permName;
	// 权限标识
	private String permTag;
	// 请求url
	private String url;
}
