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

    /**
     * 测试mq消息延迟发送-topic
     * @param exchange
     * @param routingKey
     * @return
     */
    boolean sendTopicDelayMessage(String exchange, String routingKey);

 /**
  * 测试mq单条消息过期时间
  * @param exchange
  * @param routingKey
  * @param time
  * @return
  */
 boolean sendTopicExpirationMessage(String exchange, String routingKey,String time);

 /**
  * 测试死信队列
  * @param exchange
  * @param routingKey
  * @return
  */
 boolean sendTopicDlxMessage(String exchange, String routingKey);
}
