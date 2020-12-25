package com.zwj.service.serviceImpl;

import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.pojo.seckill.SeckillOrder;
import com.zwj.pojo.seckill.SeckillStatus;
import com.zwj.service.SeckillGoodsService;
import com.zwj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 异步多线程下单
 *
 * @author zwj
 * @date 2020-12-23
 */

@Component
public class MultiThreadingCreateOrder {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private IdWorker idWorker;

    @Async
    public void createOrder() {
        try {
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();
            if (seckillStatus != null) {
                //从队列中获取一个商品
                Object o = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();
                if (o == null) {
                    clearQueue(seckillStatus);
                    return;
                }
                Long id = seckillStatus.getGoodsId();
                String userId = seckillStatus.getUsername();
                String time = seckillStatus.getTime();
                // 获取缓存的商品信息
                SeckillGoods goods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);
                if (goods == null || goods.getStockCount() <= 0) {
                    throw new RuntimeException("已售馨");
                }
                SeckillOrder seckillOrder = new SeckillOrder();
                seckillOrder.setId(idWorker.nextId());
                seckillOrder.setSeckillId(id);
                seckillOrder.setMoney(goods.getCostPrice());
                seckillOrder.setUserId(userId);
                seckillOrder.setSellerId(goods.getSellerId());
                seckillOrder.setCreateTime(new Date());
                seckillOrder.setStatus("0");
                redisTemplate.boundHashOps("SeckillOrder").put(userId, seckillOrder);
                //扣减秒杀商品库存
                Long goodsCount = redisTemplate.boundHashOps("SeckillGoodsCount").increment(id,-1);
                goods.setStockCount(goodsCount.intValue());
                //判断当前商品是否还有库存
                if (goodsCount <= 0) {
                    //并且将商品数据同步到MySQL中
                    goodsService.updateById(goods);
                    //如果没有库存,则清空Redis缓存中该商品
                    redisTemplate.boundHashOps("SeckillGoods_" + time).delete(id);
                } else {
                    //如果有库存，则直数据重置到Reids中
                    redisTemplate.boundHashOps("SeckillGoods_" + time).put(id, goods);
                }

                // 抢单成功，更新抢单状态，排队-抢单成功秒杀等待支付
                seckillStatus.setStatus(2);
                seckillStatus.setOrderId(seckillOrder.getId());
                seckillStatus.setMoney(seckillOrder.getMoney().floatValue());
                redisTemplate.boundHashOps("UserQueueStatus").put(userId, seckillStatus);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除队列
     *
     * @param seckillStatus
     */
    private void clearQueue(SeckillStatus seckillStatus) {
        //清理排队标示
        redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());
        //清理抢单标示
        redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());
    }
}
