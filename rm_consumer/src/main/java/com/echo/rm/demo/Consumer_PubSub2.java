package com.echo.rm.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Consumer_PubSub2
 * @author Echo
 * @description 订阅模式-消费者02
 * @updateTime 2021/12/27 20:34
 * @version 1.0
 */
public class Consumer_PubSub2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、获取连接
        ConnUtils cs = new ConnUtils();
        Connection connection = cs.getConnection();

        // 2、创建通道
        Channel channel = connection.createChannel();

        String queue2Name = "test_fanout_queue2";

        // 3、创建队列
        channel.queueDeclare(queue2Name, true, false, false, null);

        // 4、接收消息
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body："+new String(body));
            }
        };
        // 5、监听消息
        channel.basicConsume(queue2Name,true,consumer);
    }
}
