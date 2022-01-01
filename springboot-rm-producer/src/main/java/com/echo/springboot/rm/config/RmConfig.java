package com.echo.springboot.rm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @className RmConfig
 * @author Echo
 * @description Rm配置类
 * @updateTime 2021/12/28 20:24
 * @version 1.0
 */
@Configuration
public class RmConfig {
    public static final String EXCHANGE_NAME = "springboot_topic_exchange";
    public static final String QUEUE_NAME = "springboot_topic_queue";


    public static final String TTL_EXCHANGE_NAME = "springboot_ttl_exchange";
    public static final String TTL_QUEUE_NAME = "springboot_ttl_queue";

    /**
     * @author Echo
     * TTL交换机
     */
    @Bean("SpringBootTTLExchange")
    public Exchange TTLExchange(){
        return ExchangeBuilder.topicExchange(TTL_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * @author Echo
     * TTL队列
     */
    @Bean("SpringBootTTLQueue")
    public Queue TTLQueue() {
        Map<String,Object> map = new HashMap<>();
        // 设置队列的过期时间
        map.put("x-message-ttl",10000);
        return QueueBuilder.durable(TTL_QUEUE_NAME).withArguments(map).build();
    }

    /**
     * @author Echo
     * 定队列和交换机
     */
    @Bean
    public Binding bindTTLQueueTOExchange(@Qualifier("SpringBootTTLQueue") Queue queue, @Qualifier("SpringBootTTLExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();
    }

    /**
     * @methodName TopicExchange
     * @author Echo
     * @version 1.0
     * @description 声明交换机
     * @updateTime 2021/12/28 20:27
     * @return: org.springframework.amqp.core.Exchange
     * @throws
     */
    @Bean("SpringBootExchange")
    public Exchange TopicExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * @methodName TopicQueue
     * @author Echo
     * @version 1.0
     * @description 声明队列
     * @updateTime 2021/12/28 20:29
     * @return: org.springframework.amqp.core.Queue
     * @throws
     */
    @Bean("SpringBootQueue")
    public Queue TopicQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * @methodName bindQueueTOExchange
     * @author Echo
     * @param: queue
     * @param: exchange
     * @version 1.0
     * @description 绑定I队列和交换机
     * @updateTime 2021/12/28 20:31
     * @return: org.springframework.amqp.core.Binding
     * @throws
     */
    @Bean
    public Binding bindQueueTOExchange(@Qualifier("SpringBootQueue") Queue queue, @Qualifier("SpringBootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("t1.#").noargs();
    }
}
