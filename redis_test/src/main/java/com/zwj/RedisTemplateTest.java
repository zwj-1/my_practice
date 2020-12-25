package com.zwj;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class RedisTemplateTest {

    private static RedisTemplate redisTemplate;
    @Autowired
    public RedisTemplateTest(RedisTemplate redisTemplate) {
        RedisTemplateTest.redisTemplate = redisTemplate;
    }



    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        map.put("username","zwj");
        BoundListOperations listOperations = redisTemplate.boundListOps("userList");
        listOperations.leftPush(map);
        System.out.println(listOperations.rightPop());
        System.out.println(1);
    }
}
