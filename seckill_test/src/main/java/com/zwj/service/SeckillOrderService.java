package com.zwj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwj.pojo.seckill.SeckillOrder;
import com.zwj.pojo.seckill.SeckillStatus;

/**
 * @author zwj
 * @date 2020-12-22
 */
public interface SeckillOrderService extends IService<SeckillOrder> {

    /**
     * 保存订单
     *
     * @param seckillOrder
     */
    void saveSeckillOrder(SeckillOrder seckillOrder);

    /**
     * 用户下单
     *
     * @param id
     * @param time
     * @param userId
     */
    Boolean addOrder(Long id, String time, String userId);

    /**
     * 查询抢单状态
     *
     * @param userId
     * @return
     */
    SeckillStatus queryStatus(String userId);

    /**付款后，更新订单支付状态
     * @param userId
     * @return
     */
    Boolean updatePayStatus(String userId);

    /**
     * 查询微信支付状态
     * @param orderId
     * @return
     */
    Boolean findOrderPayStatus(String orderId,String userId);
}
