package com.zwj.config;

import com.alibaba.fastjson.JSON;
import com.zwj.constant.SecurityConstants;
import com.zwj.exception.MyOauthWebResponseExceptionTranslator;
import com.zwj.server.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * oauth2配置
 *
 * @author zwj
 * @date 2021-11-10
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private Oauth2TokenProperties oauth2TokenProperties;
    @Resource
    private MyOauthWebResponseExceptionTranslator webResponseExceptionTranslator;


    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl();
    }

    //授权码模式专用对象
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // return new JdbcAuthorizationCodeServices(dataSource);
        return new InMemoryAuthorizationCodeServices();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("server")
                .secret(passwordEncoder.encode(SecurityConstants.CLIENT_SECRET))
                // 授权类型-密码 ,GrantTypes 支持 刷新token
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                // 拦截资源id
                .resourceIds(SecurityConstants.RESOURCE_ID)
                // 作用域
                .scopes("server");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                // 允许客户端表单身份验证
                .allowFormAuthenticationForClients()
                // 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new InMemoryTokenStore())
                .authorizationCodeServices(authorizationCodeServices())
                // 设置authenticationManager以支持密码模式
                .authenticationManager(authenticationManager)
                // 身份验证
                .userDetailsService(userDetailsServiceBean())
                //认证异常处理器
                .exceptionTranslator(webResponseExceptionTranslator);
        endpoints.tokenServices(defaultTokenServices());
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     *
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new RedisTokenStore(redisConnectionFactory));
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        tokenServices.setAuthenticationManager(authenticationManager);
        // 允许重复使用refreshToken
        tokenServices.setReuseRefreshToken(true);
//        tokenServices.setClientDetailsService(customClientDetailsService);
        // token有效期自定义设置
        tokenServices.setAccessTokenValiditySeconds(oauth2TokenProperties.getExpireSeconds());
        // refresh_token
        tokenServices.setRefreshTokenValiditySeconds(oauth2TokenProperties.getRefreshExpireSeconds());
        return tokenServices;
    }

    /**
     * token增强，客户端模式不增强。
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (SecurityConstants.CLIENT_CREDENTIALS
                    .equals(authentication.getOAuth2Request().getGrantType())) {
                return accessToken;
            }
            String userName = authentication.getUserAuthentication().getName();
            JSONObject jsonObj = (JSONObject) JSON.toJSON(authentication.getUserAuthentication().getPrincipal());
            // 得到用户名，去处理数据库可以拿到当前用户的信息和角色信息（需要传递到服务中用到的信息）
            final Map<String, Object> additionalInformation = new HashMap<>();
            additionalInformation.put("license", SecurityConstants.PROJECT_LICENSE);
            additionalInformation.put("username", userName);
            try {
                additionalInformation.put("user_id", jsonObj.get("userId"));
                additionalInformation.put("dept_id", jsonObj.get("deptId"));
                additionalInformation.put("company_id", jsonObj.get("companyId"));
                additionalInformation.put("tenant_id", jsonObj.get("tenantId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // additionalInformation.put("details", JSON.toJSONString(userDetail));
            // authentication.setDetails(userDetail);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            return accessToken;
        };
    }
}
