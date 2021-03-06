package com.zwj.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * topic.man队列监听类
 * 自动签收消息
 */
@Component
@RabbitListener(queues = "topic.man")
public class TopicManReceiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("rabbit-topic消息接收到了");
        System.out.println("TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }
}
