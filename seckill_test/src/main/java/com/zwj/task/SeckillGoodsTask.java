package com.zwj.task;

import com.zwj.pojo.seckill.SeckillGoods;
import com.zwj.service.SeckillGoodsService;
import com.zwj.util.CommentUtil;
import com.zwj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**读取数据库秒杀物品定时处理类
 * @author zwj
 * @date 2020-12-22
 */
@Service
@EnableScheduling
public class SeckillGoodsTask {

    @Autowired
    private SeckillGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void findGoods(){
        //获取时间段集合
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date startTime : dateMenus) {
            // namespace = SeckillGoods_20195712
            String extName = DateUtil.date2Str(startTime);
            //根据时间段数据查询对应的秒杀商品数据
            SeckillGoods goods = new SeckillGoods();
            // 1)商品必须审核通过  status=1
            goods.setStatus("1");
            // 2)库存>0
            goods.setStockCount(0);
            // 3)开始时间<=活动开始时间
            goods.setStartTime(startTime);
            // 4)活动结束时间<开始时间+2小时
            goods.setEndTime(DateUtil.addDateHour(startTime,2));
            // 5)排除之前已经加载到Redis缓存中的商品数据
            Set keys = redisTemplate.boundHashOps("SeckillGoods_" + extName).keys();
            if(keys!=null && keys.size()>0){
                goods.setGoodsSet(keys);
            }
            //查询符合条件的秒杀物品数据
            List<SeckillGoods> seckillGoods = goodsService.findList(goods);

            //将秒杀商品数据存入到Redis缓存
            for (SeckillGoods seckillGood : seckillGoods) {
                redisTemplate.boundHashOps("SeckillGoods_"+extName).put(seckillGood.getId(),seckillGood);
                //商品数据队列存储,防止高并发超卖
                Long[] ids = CommentUtil.pushIds(seckillGood.getStockCount(), seckillGood.getId());
                redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGood.getId()).leftPushAll(ids);
                //自增计数器
                redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGood.getId(),seckillGood.getStockCount());
            }
        }
    }
}
