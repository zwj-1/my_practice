package com.zwj.controller;

import com.zwj.service.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
