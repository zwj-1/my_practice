package com.zwj.service.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwj.mapper.SeckillGoodsMapper;
import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.service.SeckillGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 秒杀物品服务类
 *
 * @author zwj
 * @date 2020-12-22
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> findList(SeckillGoods seckillGoods) {
        return this.baseMapper.selectList(createQueryCondition(seckillGoods));
    }

    @Override
    public List<SeckillGoods> findGoodsListByTime(String time) {
        return redisTemplate.boundHashOps("SeckillGoods_"+time).values();
    }

    @Override
    public SeckillGoods findGoodsByTimeAndId(String time, Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_"+time).get(id);
    }

    private LambdaQueryWrapper<SeckillGoods> createQueryCondition(SeckillGoods seckillGoods) {
        LambdaQueryWrapper<SeckillGoods> wrapper = new LambdaQueryWrapper<>();
        if (seckillGoods != null) {
            if (StringUtils.isNotBlank(seckillGoods.getStatus())) {
                wrapper.eq(SeckillGoods::getStatus, seckillGoods.getStatus());
            }
            if (seckillGoods.getStockCount() != null) {
                wrapper.gt(SeckillGoods::getStockCount, 0);
            }
            if (seckillGoods.getStartTime() != null) {
                wrapper.le(SeckillGoods::getStartTime, seckillGoods.getStartTime());
            }
            if (seckillGoods.getEndTime() != null) {
                wrapper.lt(SeckillGoods::getEndTime, seckillGoods.getEndTime());
            }
            if (CollUtil.isNotEmpty(seckillGoods.getGoodsSet())) {
                wrapper.notIn(SeckillGoods::getId, seckillGoods.getGoodsSet());
            }
        }
        return wrapper;
    }
}
