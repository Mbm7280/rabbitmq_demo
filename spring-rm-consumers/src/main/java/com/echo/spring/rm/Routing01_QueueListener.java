package com.echo.spring.rm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @className Routing01_QueueListener
 * @author Echo
 * @description 路由模式-消费者01-监听器
 * @updateTime 2021/12/28 15:06
 * @version 1.0
 */
public class Routing01_QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.printf("路由模式：接收路由名称为：%s，路由键为：%s，队列名为：%s的消息：%s \n",
                message.getMessageProperties().getReceivedExchange(),
                message.getMessageProperties().getReceivedRoutingKey(),
                message.getMessageProperties().getConsumerQueue(),
                new String(message.getBody()));
    }
}
