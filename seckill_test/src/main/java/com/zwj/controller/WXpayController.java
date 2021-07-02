package com.zwj.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.zwj.service.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wxpay")
@RestController
public class WXpayController {
    @Autowired
    private WXPayService wxPayService;
    @PostMapping("/createNative")
    public Map<String, String> createNative(){
       return wxPayService.createNative("1",898,null,null);
    }

    /**
     * 支付成功回调函数
     * @param request
     * @return
     */
    @RequestMapping("/notify")
    public Map notifyLogic(HttpServletRequest request){
        System.out.println("支付成功回调");
        InputStream inputStream;
        try {
            inputStream = request.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            outputStream.close();
            inputStream.close();
            String result = new String(outputStream.toByteArray(),"utf-8");
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            //orderService.updatePayStatus(map.get("out_trade_no"),map.get("transaction_id"));//更新订单状态
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>(16);
    }
}
