package com.zwj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwj.pojo.seckill.SeckillGoods;

import java.util.List;

/**
 * @author zwj
 * @date 2020-12-22
 */
public interface SeckillGoodsService extends IService<SeckillGoods> {

    List<SeckillGoods> findList(SeckillGoods seckillGoods);

    /**
     * 获取指定时间的秒杀商品
     * @param time
     * @return
     */
    List<SeckillGoods> findGoodsListByTime(String time);

    /**
     * 查询指定时间秒杀商品的详情
     * @param time
     * @param id
     * @return
     */
    SeckillGoods findGoodsByTimeAndId(String time,Long id);
}
