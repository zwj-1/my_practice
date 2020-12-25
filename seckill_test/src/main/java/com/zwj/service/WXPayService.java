package com.zwj.service;

import java.util.Map;

/**
 * 微信支付
 *
 * @author zwj
 * @date 2020-12-24
 */
public interface WXPayService {
   /**
    * 下单生成二维码
    * @param orderId
    * @param money
    * @param notifyUrl
    * @param attach
    * @return
    */
   Map<String,String> createNative(String orderId, Integer money, String notifyUrl, String... attach);

   /**
    * 查询订单
    * @param orderId
    * @return
    */
   Map<String,String> findOrder(String orderId);

   /**
    * 关闭订单
    * @param orderId
    * @return
    */
   Map<String,String> openOrder(String orderId);
}
