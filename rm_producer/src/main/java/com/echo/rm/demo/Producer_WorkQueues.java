package com.echo.rm.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Producer_WorkQueues
 * @author Echo
 * @description 工作模式-生产者
 * @updateTime 2021/12/27 17:29
 * @version 1.0
 */
public class Producer_WorkQueues {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、获取连接对象
        ConnUtils cs = new ConnUtils();
        Connection connection = cs.getConnection();

        // 2、创建频道
        Channel channel = connection.createChannel();

        // 3、声明队列
        channel.queueDeclare("work_queue", true, false, false, null);

        // 4、发送消息
        for (int i = 1; i <= 30; i++) {
            // 发送信息
            String message = i + "工作模式" ;
            channel.basicPublish("", "work_queue", null, message.getBytes());
            System.out.println("已发送消息：" + message);
        }

        // 5、关闭资源
        channel.close();
        connection.close();
    }
}
