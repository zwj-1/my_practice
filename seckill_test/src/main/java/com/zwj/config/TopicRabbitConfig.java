package com.zwj.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.MAN, true);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.WOMAN, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange", true, false);
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

}
