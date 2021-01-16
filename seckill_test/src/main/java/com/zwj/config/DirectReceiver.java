package com.zwj.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * rabbitmq监听类- 监听队列：testDirectQueue
 */
@Component
@RabbitListener(queues = "testDirectQueue")
public class DirectReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) {
        System.out.println("rabbit消息接收到了");
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
