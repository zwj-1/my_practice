package com.zwj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "security.oauth2.token")
public class Oauth2TokenProperties {
    public static final int DEFAULT_EXPIRE_SECONDS = 60*60*2;
    public static final int DEFAULT_REFRESH_EXPIRE_SECONDS = 60*60*24;

    /**
     * token过期时间
     */
    private int expireSeconds;
    /**
     * 刷新token过期时间
     */
    private int refreshExpireSeconds;

    public int getExpireSeconds() {
        return expireSeconds == 0 ? DEFAULT_EXPIRE_SECONDS : expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public int getRefreshExpireSeconds() {
        return refreshExpireSeconds == 0 ? DEFAULT_REFRESH_EXPIRE_SECONDS : refreshExpireSeconds;
    }

    public void setRefreshExpireSeconds(int refreshExpireSeconds) {
        this.refreshExpireSeconds = refreshExpireSeconds;
    }

    @Override
    public String toString() {
        return "Oauth2TokenProperties{" +
                "expireSeconds=" + expireSeconds +
                ", refreshExpireSeconds=" + refreshExpireSeconds +
                '}';
    }
}
