package com.echo.springboot.rm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className TtlListener
 * @author Echo
 * @description Time To Live 监听类
 * @updateTime 2022/1/1 18:24
 * @version 1.0
 */
@Component
public class TtlListener {

    @RabbitListener(queues = "springboot_ttl_queue")
    public void ttlListener(Message msg){
        System.out.println(new String(msg.getBody()));
    }

}
