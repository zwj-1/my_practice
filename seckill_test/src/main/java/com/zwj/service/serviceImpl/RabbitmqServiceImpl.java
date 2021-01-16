package com.zwj.service.serviceImpl;

import com.github.wxpay.sdk.WXPayUtil;
import com.zwj.pojo.WXpayDo;
import com.zwj.service.RabbitmqService;
import com.zwj.service.WXPayService;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * rabbitmq实现类
 *
 * @author zwj
 * @date 2021-1-14
 */
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public boolean sendDirectMessage() {
        Map<String, Object> param = getParam();
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("testDirectExchange", "testDirectRouting", param);
        return true;
    }

    private Map<String, Object> getParam() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(3);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        return map;
    }

    @Override
    public boolean sendTopicMessage(String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, getParam());
        return true;
    }

    @Override
    public boolean sendTopicDelayMessage(String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, getParam(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(60000));
                return message;
            }
        });

        return true;
    }

    @Override
    public boolean sendTopicExpirationMessage(String exchange, String routingKey, String time) {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(time);
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchange, routingKey, getParam(), messagePostProcessor);
        return true;
    }
}
