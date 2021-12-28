package com.echo.springboot.rm;

import com.echo.springboot.rm.config.RmConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @className ProducerTest
 * @author Echo
 * @description 生产者
 * @updateTime 2021/12/28 20:52
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    // 1、注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend(RmConfig.EXCHANGE_NAME,"t1.topic.test","hello springboot topic");
    }
}
