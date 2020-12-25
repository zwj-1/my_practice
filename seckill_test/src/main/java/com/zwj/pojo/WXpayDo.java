package com.zwj.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**微信支付相关信息
 * @author zwj
 */
@Data
@PropertySource("classpath:weixinpay.properties")
@Component
public class WXpayDo {

    @Value("${appid}")
    private String appid;

    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;

    @Value("${notifyurl}")
    private String notifyurl;

    @Value("${createNativeUrl}")
    private String createNativeUrl;

    @Value("${findOrderUrl}")
    private String findOrderUrl;

    @Value("${openOrderUrl}")
    private String openOrderUrl;
}
