package com.echo.rm.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Producer_HelloWorld
 * @author Echo
 * @description 简单模式-生产者
 * @updateTime 2021/12/27 14:33
 * @version 1.0
 */
public class Producer_HelloWorld {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置参数
        // ip设置：默认值也是localhost
        factory.setHost("localhost");
        // port设置 默认值：5672
        factory.setPort(5672);
        // 虚拟机设置 默认值：/
        factory.setVirtualHost("/echo");
        // 用户名设置 默认 guest
        factory.setUsername("echo");
        // 密码设置 默认 guest
        factory.setPassword("echo");

        // 3、创建连接 Connection
        Connection connection = factory.newConnection();

        // 4、创建Channel
        Channel channel = connection.createChannel();

        // 5、创建队列Queue
        /**
         * @author Echo
         * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         * 参数解释：
         *      queue：队列的名称
         *      durable：是否持久化
         *      exclusive：是否独占。只能有一个消费者监听这队列
         *      autoDelete：是否自动删除,当没有Consumer时，自动删除掉
         *      arguments：配置参数
         */
        channel.queueDeclare("hello_world",true,false,false,null);

        // 6、发送消息
        /**
         * @author Echo
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * 参数解释：
         *      exchange：交换机名称
         *      routingKey：路由的名称
         *      props：配置信息
         *      body：要发送的消息数据
         */
        String bodyStr = "简单模式";
        channel.basicPublish("","hello_world",null,bodyStr.getBytes());

        // 7、释放资源
        channel.close();
        connection.close();
    }
}
