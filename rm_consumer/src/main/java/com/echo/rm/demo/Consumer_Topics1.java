package com.echo.rm.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Consumer_Topics1
 * @author Echo
 * @description 主题模式(通配符模式模式)-消费者01
 * @updateTime 2021/12/28 12:30
 * @version 1.0
 */
public class Consumer_Topics1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、获取连接
        ConnUtils cs = new ConnUtils();
        Connection connection = cs. getConnection();

        // 2、创建通道
        Channel channel = connection.createChannel();

        // 3、接受消息
        String queue1Name = "test_topic_queue1";
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body：" + new String(body));
            }
        };
        // 4、监听消息
        channel.basicConsume(queue1Name,true,consumer);
    }
}
