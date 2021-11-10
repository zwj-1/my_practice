package com.zwj.exception;

import com.zwj.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 自定义验证器
 */
@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(username);
        if(!user.isEnabled()){
            throw new BadCredentialsException("帐号已锁定！");
        }
        if(!user.isAccountNonExpired()){
            throw new BadCredentialsException("帐号已过期！");
        }
        if(!new BCryptPasswordEncoder().matches(password,user.getPassword())){
            throw new BadCredentialsException("密码错误！");
        }
        return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
