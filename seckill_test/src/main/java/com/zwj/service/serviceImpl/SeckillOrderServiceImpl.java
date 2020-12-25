package com.zwj.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwj.mapper.SeckillOrderMapper;
import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.pojo.seckill.SeckillOrder;
import com.zwj.pojo.seckill.SeckillStatus;
import com.zwj.service.SeckillGoodsService;
import com.zwj.service.SeckillOrderService;
import com.zwj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 秒杀订单服务类
 *
 * @author zwj
 * @date 2020-12-22
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Override
    public void saveSeckillOrder(SeckillOrder seckillOrder) {
        this.baseMapper.insert(seckillOrder);
    }

    @Override
    public Boolean addOrder(Long id, String time, String userId) {
        //判断是否有库存
        Long size = redisTemplate.boundListOps("SeckillGoodsCountList_" + id).size();
        if (size == null || size <= 0) {
            //101:没有库存
            throw new RuntimeException("101");
        }

        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(userId, 1);
        if (userQueueCount > 1) {
            // 重复排队
            throw new RuntimeException("100");
        }
        //排队信息封装
        SeckillStatus seckillStatus = new SeckillStatus(userId, new Date(), 1, id, time);
        //将秒杀抢单信息存入到Redis中,这里采用List方式存储,List本身是一个队列
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(seckillStatus);
        //抢单状态存入redis中
        redisTemplate.boundHashOps("UserQueueStatus").put(userId, seckillStatus);
        // 多线程下单
        multiThreadingCreateOrder.createOrder();
        return true;
    }

    @Override
    public SeckillStatus queryStatus(String userId) {
        return (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(userId);
    }

    @Override
    public Boolean updatePayStatus(String userId) {
        // 支付后修改订单支付状态
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(userId);
        if(seckillOrder!=null){
            seckillOrder.setStatus("1");
            seckillOrder.setPayTime(new Date());
            // 保存到数据库
            updateById(seckillOrder);

            // 支付后，清除用户相关队列
            redisTemplate.boundHashOps("UserQueueStatus").delete(userId);
            redisTemplate.boundHashOps("SeckillOrder").delete(userId);
            redisTemplate.boundHashOps("UserQueueCount").delete(userId);
        }
        return true;
    }
}
