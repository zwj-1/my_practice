package com.zwj.constant;

/**
 * security-oauth2
 * @author zwj
 * @date 2021-11-10
 */
public interface SecurityConstants {
    /**
     * oauth拦截资源id
     */
    String  RESOURCE_ID="xingpan";
    /**
     *client密码
     */
    String  CLIENT_SECRET="secret";

    String OAUTH2_LOGIN_URL = "/oauth/token";

    String ROLE_PREFIX = "ROLE_";
    /**
     * 权限标识类型 id
     */
    String AUTHORITY_TYPE_ID = "0";

    String TOKEN_CACHE_KEY = "SYSTEM_TOKEN_CACHE";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    String BASIC_ = "Basic ";

    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "project_oauth:client:details";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 项目的license
     */
    String PROJECT_LICENSE = "made by xingpan";
    /**
     * 前缀
     */
    String PROJECT_PREFIX = "project_";
    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";
    /**
     * 用于token刷新时缓存token数据的key
     */
    String REFRESH_ACCESS_TOKEN_KEY =  SecurityConstants.PROJECT_PREFIX+ SecurityConstants.OAUTH_PREFIX + "access_for_refresh:";

    Long BASE_ANONYMOUS_ROLE_ID = 2L;
    String BASE_ANONYMOUS_ROLE_NAME = "匿名角色";
    String BASE_ANONYMOUS_ROLE_CODE = "ANONYMOUS";
}
