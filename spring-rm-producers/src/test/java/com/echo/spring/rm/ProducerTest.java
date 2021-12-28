package com.echo.spring.rm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @className ProducerTest
 * @author Echo
 * @description 生产者-测试类
 * @updateTime 2021/12/28 14:13
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    // 1、注入 RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @author Echo
     * 简单模式
     */
    @Test
    public void testHelloWorld(){
        /**
         * @author Echo
         * convertAndSend(String routingKey, Object object)
         * 参数解释：
         *      routingKey：简单模式中对应 队列的名称
         *      object：消息
         */
        rabbitTemplate.convertAndSend("spring_hello_world_queue","hello world");
    }

    /**
     * @author Echo
     * 订阅模式
     */
    @Test
    public void testPubSub(){
        /**
         * @author Echo
         * convertAndSend(String exchange, String routingKey, Object object)
         * 参数解释：
         *      exchange：交换机的名称
         *      routingKey：订阅模式中使用: ""
         *      object：消息
         */
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","hello PubSub");
    }
    /**
     * @author Echo
     * 路由模式
     */
    @Test
    public void testRouting(){
        /**
         * @author Echo
         * convertAndSend(String exchange, String routingKey, Object object)
         * 参数解释：
         *      exchange：交换机的名称
         *      routingKey：订阅模式中使用: ""
         *      object：消息
         */
        // routingKey指定 队列一
        rabbitTemplate.convertAndSend("spring_direct_exchange","t1","hello Routing");
    }

    /**
     * @author Echo
     * 主题模式
     */
    @Test
    public void testTopics(){
        /**
         * @author Echo
         * convertAndSend(String exchange, String routingKey, Object object)
         * 参数解释：
         *      exchange：交换机的名称
         *      routingKey：订阅模式中使用: ""
         *      object：消息
         */
        // routingKey指定 队列一
        rabbitTemplate.convertAndSend("spring_topic_exchange","t1.topic","hello Topics");
    }
}
