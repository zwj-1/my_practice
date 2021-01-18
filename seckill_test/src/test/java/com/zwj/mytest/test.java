package com.zwj.mytest;

import cn.hutool.core.util.XmlUtil;
import com.zwj.pojo.WXpayDo;
import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.pojo.seckill.SeckillStatus;
import com.zwj.service.RabbitmqService;
import com.zwj.service.SeckillGoodsService;
import com.zwj.service.SeckillOrderService;
import com.zwj.service.WXPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private SeckillOrderService orderService;

    @Test
    public void test1() {
        List<SeckillGoods> list = goodsService.findList(new SeckillGoods());
        for (SeckillGoods seckillGoods : list) {
            System.out.println(seckillGoods);
        }
    }

    @Test
    public void test2() {
        List<SeckillGoods> list = goodsService.findGoodsListByTime("2020122216");
        for (SeckillGoods seckillGoods : list) {
            System.out.println(seckillGoods);
        }
    }

    @Test
    public void test3() {
        SeckillGoods seckillGoods = goodsService.findGoodsByTimeAndId("2020122216", 1L);
        System.out.println(seckillGoods);
    }

    @Test
    public void test4() {
        Boolean b = orderService.addOrder(1L, "2020122216", "1");
    }

    @Test
    public void test5() {
        SeckillStatus seckillStatus = orderService.queryStatus("1");
        System.out.println(seckillStatus);
    }

    @Autowired
    private WXpayDo wXpayDo;

    @Test
    public void test6() {
        System.out.println(wXpayDo);
    }

    @Autowired
    private WXPayService wxPayService;

    @Test
    public void test7() {
        Map<String, String> aNative = wxPayService.createNative("13265131096113", 1, null, null);

    }

    @Test
    public void test8() {
        //13265131096112订单支付成功
        Map<String, String> order = wxPayService.findOrder("13265131096112");
    }

    @Test
    public void test9() {
        wxPayService.openOrder("13265131096111");
    }

    @Autowired
    private RabbitmqService rabbitmqService;

    @Test
    public void test10() {
        boolean b = rabbitmqService.sendDirectMessage();
        System.out.println(b);
    }

    @Test
    public void test11() {
        rabbitmqService.sendTopicMessage("topicExchange", "topic.man");
        rabbitmqService.sendTopicMessage("topicExchange", "topic.woman");
    }

    @Test
    public void test12() {
        // 测试延迟队列 -- 队列已设置过期时间10秒
        rabbitmqService.sendTopicDelayMessage("topicExchange", "dlx.normal","60000");
        System.out.println("message send time:"+ LocalDateTime.now());
    }

    @Test
    public void test13() {
        for (int i = 0; i < 10; i++) {
            rabbitmqService.sendTopicMessage("topicExchange", "topic.woman");
        }
    }

    @Test
    public void test14() {
        // 测试队列过期时间
        rabbitmqService.sendTopicMessage("topicExchange", "ttl");
    }

    @Test
    public void test15() {
        //测试单条消息的过期时间
        rabbitmqService.sendTopicExpirationMessage("topicExchange", "ttl", "5000");
    }

    @Test
    public void test16() {
        // 1、测试死信队列-队列过期后是否死信交换机路由到死信队列
        //rabbitmqService.sendTopicDlxMessage("topicExchange","dlx.normal");
        // 2、测试死信队列-队列消息长度超过最大值
        /*for (int i = 0; i < 20; i++) {
            rabbitmqService.sendTopicDlxMessage("topicExchange", "dlx.normal");
        }*/
        // 3、消费者拒接消费消息，basicNack/basicReject,并且不把消息重新放入原目标队列,requeue=false；
        rabbitmqService.sendTopicDlxMessage("topicExchange", "dlx.normal");
    }
}
