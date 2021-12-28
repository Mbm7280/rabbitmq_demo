package com.echo.rm.demo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Producer_Topics
 * @author Echo
 * @description 主题模式(通配符模式模式)-生产者
 * @updateTime 2021/12/28 12:23
 * @version 1.0
 */
public class Producer_Topics {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、获取连接
        ConnUtils cs = new ConnUtils();
        Connection connection = cs.getConnection();

        // 2、创建通道
        Channel channel = connection.createChannel();

        // 3、创建交换机
        String exchangeName = "test_topic";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);

        // 4、创建队列
        String queue1Name = "test_topic_queue1";
        String queue2Name = "test_topic_queue2";
        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);

        // 5、绑定交换机和队列
        channel.queueBind(queue1Name,exchangeName,"#.t1");
        channel.queueBind(queue1Name,exchangeName,"t1.*");
        channel.queueBind(queue2Name,exchangeName,"*.*");

        // 6、发送消息
        String body = "test_topic";
        channel.basicPublish(exchangeName,"q1.t1",null,body.getBytes());

        // 7、关闭链接
        channel.close();
        connection.close();
    }
}
