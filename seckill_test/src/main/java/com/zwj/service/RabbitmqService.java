package com.zwj.service;

import java.util.Map;

/**
 * rabbitmq
 *
 * @author zwj
 * @date 2021-1-14
 */
public interface RabbitmqService {
   /**
    * 测试mq消息发送-Direct
    * @return
    */
    boolean sendDirectMessage();

    /**
     * 测试mq消息发送-topic
     * @param exchange
     * @param routingKey
     * @return
     */
    boolean sendTopicMessage(String exchange,String routingKey);
}
