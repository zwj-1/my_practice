package com.zwj.config;

import com.txj.bwbd.constraint.WeixinConstants;
import com.txj.bwbd.utils.WeChatCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * @author zwj
 * @date 2021年05月25日
 */
@Component
public class WeChatAccessTokenTask {
    Logger logger = LoggerFactory.getLogger(WeChatAccessTokenTask.class);

    @Autowired
    private WeChatCommonUtil weChatCommonUtil;

    public static String token;

    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7100*1000 )
//    @Scheduled(initialDelay = 1000, fixedDelay = 7100*1000 )
    public void WeChatAccessToken(){
        try {
            token = weChatCommonUtil.getToken(WeixinConstants.APPID, WeixinConstants.APPSECRET).getAccess_token();
            logger.info("获取到的微信access_token为"+token);
        } catch (Exception e) {
            logger.error("获取微信access_toke出错，信息如下");
            e.printStackTrace();
        }
    }

}
