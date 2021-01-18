package com.zwj.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Topic交换机
 *
 * @author zwj
 * @date 2020-1-14
 */
@Configuration
public class TopicRabbitConfig {
    /**
     * 绑定键
     */
    private final static String MAN = "topic.man";
    private final static String WOMAN = "topic.woman";
    private final static String TTL = "ttl";
    private final static String DLX = "dlx";
    private final static String DLX_NORMAL = "dlx.normal";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.MAN, true);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.WOMAN, true);
    }

    @Bean
    public Queue thirdlyQueue() {
        // 设置队列消息过期时间
        // 1、若队列设置了过期时间，单条消息也设置了过期时间，已时间短的为准
        // 2、设置队列过期时间参数：x-message-ttl
        // 3、当消息处于队列头部时，才会过期删除
        Map<String, Object> param = new HashMap<>(2);
        param.put("x-message-ttl", 50000);
        return new Queue(TopicRabbitConfig.TTL, true, false, false, param);
    }

    /**
     * 测试死信队列_正常队列
     *
     * @return
     */
    @Bean
    public Queue fourthlyQueue() {
        Map<String, Object> param = new HashMap<>(2);
        // 过期时间
        param.put("x-message-ttl", 10000);
        // 队列最大长度
        param.put("x-max-length", 10);
        // 死信队列
        param.put("x-dead-letter-exchange", "dlxExchange");
        return new Queue(TopicRabbitConfig.DLX_NORMAL, true, false, false, param);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(TopicRabbitConfig.DLX, true, false, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    TopicExchange dlxExchange() {
        return new TopicExchange("dlxExchange", true, false);
    }

    @Bean
    Binding bindingExchangeMessage() {
        // 将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
        // 这样只要是消息携带的路由键是topic.man,才会分发到该队列
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(MAN);
    }


    @Bean
    Binding bindingExchangeMessage2() {
        // 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
        // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(thirdlyQueue()).to(exchange()).with("ttl");
    }

    /**
     * 测试死信交换机绑定-死信交换机
     *
     * @return
     */
    @Bean
    Binding bindingExchangeMessage4() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("dlx.#");
    }

    /**
     * 测试死信交换机绑定-正常交换机
     *
     * @return
     */
    @Bean
    Binding bindingExchangeMessage5() {
        return BindingBuilder.bind(fourthlyQueue()).to(exchange()).with("dlx.normal");
    }
}
