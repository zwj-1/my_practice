package com.zwj.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

// 用户信息表
@Data
public class UserEntity implements UserDetails, Serializable {

    private Integer id;
    private String username;
    private String realname;
    private String password;
    private Set<RoleEntity> roleList;
    private Set<PermissionEntity> permissionList;
    private Date createDate;
    private Date lastLoginTime;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    /**
     * 用户所有权限
     */
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

}
