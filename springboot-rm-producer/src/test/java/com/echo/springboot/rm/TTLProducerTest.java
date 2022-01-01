package com.echo.springboot.rm;

import com.echo.springboot.rm.config.RmConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.echo.springboot.rm.config.RmConfig.TTL_EXCHANGE_NAME;

/**
 * @className TTLProducerTest
 * @author Echo
 * @description Time To Live 生产者
 * @updateTime 2022/1/1 18:28
 * @version 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TTLProducerTest {

    // 1、注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @author Echo
     *  TTL:过期时间
     *      1. 队列统一过期
     *      2. 消息单独过期
     *      如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准。
     *      队列过期后，会将队列所有消息全部移除。
     *      消息过期后，只有消息在队列顶端，才会判断其是否过期(移除掉)
     */
    @Test
    public void testTTl(){
        // 设置消息的过期时间
        // 消息后处理对象，设置一些消息的参数信息
        MessagePostProcessor messagePostProcessor = message -> {
            //1.设置message的信息
            //消息的过期时间
            message.getMessageProperties().setExpiration("5000");
            //2.返回该消息
            return message;
        };
        // 消息单独过期
        // rabbitTemplate.convertAndSend(TTL_EXCHANGE_NAME,"ttl.test","hello  ttl",messagePostProcessor);

        // 如果消息不在队列顶端，则不会根据过期时间移除
        for (int i = 0; i < 10; i++) {
            if(i == 5){
                //消息单独过期
                rabbitTemplate.convertAndSend(TTL_EXCHANGE_NAME, "ttl.test", "hello ttl....",messagePostProcessor);
            }else{
                //不过期的消息
                rabbitTemplate.convertAndSend(TTL_EXCHANGE_NAME, "ttl.test", "hello ttl....");
            }
        }
    }
}
