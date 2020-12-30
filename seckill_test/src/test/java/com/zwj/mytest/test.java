package com.zwj.mytest;

import com.zwj.pojo.WXpayDo;
import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.pojo.seckill.SeckillStatus;
import com.zwj.service.SeckillGoodsService;
import com.zwj.service.SeckillOrderService;
import com.zwj.service.WXPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private SeckillOrderService orderService;
    @Test
    public void test1(){
        List<SeckillGoods> list = goodsService.findList(new SeckillGoods());
        for (SeckillGoods seckillGoods : list) {
            System.out.println(seckillGoods);
        }
    }

    @Test
    public void test2(){
        List<SeckillGoods> list = goodsService.findGoodsListByTime("2020122216");
        for (SeckillGoods seckillGoods : list) {
            System.out.println(seckillGoods);
        }
    }
    @Test
    public void test3(){
        SeckillGoods seckillGoods = goodsService.findGoodsByTimeAndId("2020122216", 1L);
        System.out.println(seckillGoods);
    }
    @Test
    public void test4(){
        Boolean b = orderService.addOrder(1L, "2020122216", "1");
    }
    @Test
    public void test5(){
        SeckillStatus seckillStatus = orderService.queryStatus("1");
        System.out.println(seckillStatus);
    }
    @Autowired
    private WXpayDo wXpayDo;
    @Test
    public void test6(){
        System.out.println(wXpayDo);
    }
    @Autowired
    private WXPayService wxPayService;
    @Test
    public void test7(){
        Map<String, String> aNative = wxPayService.createNative("13265131096113", 1, null, null);

    }
    @Test
    public void test8(){
        //13265131096112订单支付成功
        Map<String, String> order = wxPayService.findOrder("13265131096112");
    }
    @Test
    public void test9(){
        wxPayService.openOrder("13265131096111");
    }
}
