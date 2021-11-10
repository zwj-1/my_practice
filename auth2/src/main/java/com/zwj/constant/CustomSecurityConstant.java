package com.zwj.constant;

/**
 * @ClassName LoginTypeConstant
 * @Description：
 * @Copyright：Copyright(c) 2020
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2020/5/14
 **/
public interface CustomSecurityConstant {

    /**
     * 登录类型标识
     */
    String SECURITY_LOGIN_TYPE_KEY = "type";
    /**
     * 登陆终端：2：移动端登陆，包括微信公众号、小程序等；1：PC后台登陆
     */
    String SECURITY_RESTFUL_MOBILE_KEY = "mobile";
    /**
     * 登录用户名KEY
     */
    String SECURITY_RESTFUL_USERNAME_KEY = "username";
    /**
     * 登录密码KEY
     */
    String SECURITY_RESTFUL_PASSWORD_KEY = "password";
    /**
     * 登录类型 手机登录
     */
    String SECURITY_LOGIN_TYPE_PHONE = "phone";
    /**
     * 登录类型 验证码登录
     */
    String SECURITY_LOGIN_TYPE_VERIFY = "verifyCode";
    /**
     * 登录类型 二维码登录
     */
    String SECURITY_LOGIN_TYPE_QR_CODE = "qrCode";
    /**
     * 登录类型 job user登录
     */
    String SECURITY_LOGIN_TYPE_JOB_USER = "jobUser";
    /**
     * jobuser 登录地址
     */
    String SECURITY_JOB_USER_LOGIN_URL = "/jobuser/token";




}
