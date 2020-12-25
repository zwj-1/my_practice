package com.zwj.service.serviceImpl;

import com.github.wxpay.sdk.WXPayUtil;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zwj.pojo.WXpayDo;
import com.zwj.service.WXPayService;
import com.zwj.util.HttpClient1;
import org.apache.http.entity.StringEntity;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付实现类
 *
 * @author zwj
 * @date 2020-12-24
 */
@Service
public class WXPayServiceImpl implements WXPayService {

    @Autowired
    private WXpayDo wxpayDo;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 注意事项：
     * 1、restTemplate先配置ssl
     * 2、RestTemplate  中默认字符格式是 ios-8859-1，配置字符格式为utf-8
     * @param orderId
     * @param money
     * @param notifyUrl
     * @param attach
     * @return
     */
    @Override
    public Map<String, String> createNative(String orderId, Integer money, String notifyUrl, String... attach) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxpayDo.getAppid());
        paramMap.put("mch_id", wxpayDo.getPartner());
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", "青橙");
        paramMap.put("out_trade_no", orderId);
        paramMap.put("total_fee", money + "");
        paramMap.put("spbill_create_ip", "127.0.0.1");
        paramMap.put("notify_url", wxpayDo.getNotifyurl());
        paramMap.put("trade_type", "NATIVE");

        try {
            String paramXml = WXPayUtil.generateSignedXml(paramMap, wxpayDo.getPartnerkey());
            System.out.println(paramXml);
            String result = restTemplate.postForObject(wxpayDo.getCreateNativeUrl(), paramXml, String.class);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            System.out.println(resultMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> findOrder(String orderId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxpayDo.getAppid());
        paramMap.put("mch_id", wxpayDo.getPartner());
        paramMap.put("out_trade_no", orderId);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String paramXml = WXPayUtil.generateSignedXml(paramMap, wxpayDo.getPartnerkey());
            String result = restTemplate.postForObject(wxpayDo.getFindOrderUrl(), paramXml, String.class);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            System.out.println(resultMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> openOrder(String orderId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxpayDo.getAppid());
        paramMap.put("mch_id", wxpayDo.getPartner());
        paramMap.put("out_trade_no", orderId);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String paramXml = WXPayUtil.generateSignedXml(paramMap, wxpayDo.getPartnerkey());
            String result = restTemplate.postForObject(wxpayDo.getOpenOrderUrl(), paramXml, String.class);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            System.out.println(resultMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
