package com.zwj.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 手动确认消费者收到消息配置
 * @author zwj
 * @date 2020-1-14
 */
@Configuration
public class MessageListenerConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;
    /**自定义手动确认消息业务处理类*/
    @Autowired
    private MyAckReceiver myAckReceiver;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // 消费端设置限流--必须手动确定消息
        container.setPrefetchCount(1);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        //若yml中全局设置了手动确认消息，则此处可以不设置
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //1、设置一个队列
        //container.setQueueNames("topic.woman");

        //2、同时设置多个如下：前提是队列都是必须已经创建存在的
        container.setQueueNames("topic.woman","topic.man");

        //3、同时设置多个如下,如果使用这种情况,那么要设置多个,就使用addQueues
       /* container.setQueues(new Queue("TestDirectQueue",true));
        container.addQueues(new Queue("TestDirectQueue2",true));
        container.addQueues(new Queue("TestDirectQueue3",true));*/

        container.setMessageListener(myAckReceiver);

        return container;
    }

}
