package com.zwj.resouce;

import com.zwj.constant.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


/**
 * oauth拦截资源配置
 * @author zwj
 */
@EnableResourceServer
@Configuration
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(SecurityConstants.RESOURCE_ID);

    }

    @Override

    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
//                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()

                .headers().addHeaderWriter((request, response) -> {
                    // 允许跨域
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    // 如果是跨域的预检请求，则原封不动向下传达请求头信息
                    if (request.getMethod().equals("OPTIONS")) {
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                    }
                });
    }
}
