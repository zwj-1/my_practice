package com.zwj.util;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.txj.bwbd.common.wechat.entity.AccessToken;
import com.txj.bwbd.constraint.WeixinConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * @author zwj
 * @date 2021年05月25日
 */
@Component
public class WeChatCommonUtil {
    Logger logger = LoggerFactory.getLogger(WeChatCommonUtil.class);

    // 获取access_token的接口地址（GET） 限2000（次/天）
    private static String url = WeixinConstants.ACCESS_TOKEN_URL;

    public AccessToken getToken(String appid, String appsecret){

        AccessToken token;
        //访问微信服务器的地址
        String requestUrl=url.replace("APPID", appid).replace("APPSECRET", appsecret);
        //HttpRequestUtil httpRequestUtil=new HttpRequestUtil();
        //创建一个json对象
        String responseStr = HttpUtil.get(requestUrl);
        System.out.println("获取到的String格式的Response为:"+responseStr);
        //判断返回字符串是否为空
        if (responseStr != null) {
            token = JSONObject.parseObject(responseStr, AccessToken.class);
        } else {
            token = null;
            // 获取token失败
            logger.error("获取token失败 errcode:{} errmsg:{}");
        }
        return token;
    }
}
