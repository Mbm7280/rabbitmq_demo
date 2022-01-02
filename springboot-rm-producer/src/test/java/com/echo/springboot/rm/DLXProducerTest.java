package com.echo.springboot.rm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @className DLXProducerTest
 * @author Echo
 * @description 死信队列测试
 * @updateTime 2022/1/2 12:47
 * @version 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class DLXProducerTest {

    // 1、注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送测试死信消息：
     *  消息成为死信的三种情况：
     *      队列消息长度到达限制；
     *      消费者拒接消费消息，并且不重回队列；
     *      原队列存在消息过期设置，消息到达超时时间未被消费；
     */
    @Test
    public void testDlx(){
        // 1. 测试过期时间，死信消息
        // rabbitTemplate.convertAndSend("test_dlx_exchange","test.dlx.hello","我是一条消息，我会死吗？");

        // 2. 测试长度限制后，消息死信
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("test_dlx_exchange","test.dlx.hello","我是一条消息，我会死吗？");
//        }

        // 3. 测试消息拒收
        rabbitTemplate.convertAndSend("test_dlx_exchange","test.dlx.haha","我是一条消息，我会死吗？");
    }

}
