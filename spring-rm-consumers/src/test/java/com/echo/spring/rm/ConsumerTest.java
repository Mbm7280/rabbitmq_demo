package com.echo.spring.rm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @className ConsumerTest
 * @author Echo
 * @description 消费者-测试类
 * @updateTime 2021/12/28 14:25
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-rabbitmq-consumer.xml")
public class ConsumerTest {

    @Test
    public void testQueueListener(){
        // 写一个死循环，用于持续监听
        while(true){
        }
    }
}
