package com.echo.rm.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Consumer_Routing1
 * @author Echo
 * @description 路由模式-消费者01
 * @updateTime 2021/12/27 21:43
 * @version 1.0
 */
public class Consumer_Routing2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接
        ConnUtils cs = new ConnUtils();
        Connection connection = cs.getConnection();

        // 2、创建通道
        Channel channel = connection.createChannel();

        // 3、创建队列
        String queue2Name = "test_direct_queue2";

        // 4、接受消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body：" + new String(body));
            }
        };
        // 5、监听消息
        channel.basicConsume(queue2Name,true,consumer);
    }
}
