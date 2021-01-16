package com.zwj.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Map;

/**
 * topic.woman队列监听类
 * 自动签收消息
 */
//@Component
//@RabbitListener(queues = "topic.woman")
public class TopicWomanReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) throws IOException {
        System.out.println("rabbit-topic消息接收到了");
        System.out.println("TopicWomanReceiver消费者收到消息  : " + testMessage.toString());
    }
}
