package com.zwj.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * dlx.normal队列监听类
 * @author zwj
 * @date 2020-1-18
 *
 */
@Component
@RabbitListener(queues = "dlx.normal")
public class TopicDlxNormalReceiver {
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message message) throws IOException {
        // 设置消息拒收，测试拒收是否将信息发送到死信队列
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,false);
    }
}
