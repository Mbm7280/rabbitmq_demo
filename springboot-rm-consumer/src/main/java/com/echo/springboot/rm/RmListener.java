package com.echo.springboot.rm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className RmListener
 * @author Echo
 * @description 主题模式-生产者-监听类
 * @updateTime 2021/12/28 20:36
 * @version 1.0
 */
@Component
public class RmListener {
    @RabbitListener(queues = "springboot_topic_queue")
    public void TopicQueueListener(Message msg){
        System.out.println(new String(msg.getBody()));
    }
}
