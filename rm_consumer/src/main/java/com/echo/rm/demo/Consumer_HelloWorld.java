package com.echo.rm.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Consumer_HelloWorld
 * @author Echo
 * @description 简单模式-消费者
 * @updateTime 2021/12/27 14:48
 * @version 1.0
 */
public class Consumer_HelloWorld {

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
        //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
        // 由于生产者已经创建了该队列，所以这一行代码可以省略
        channel.queueDeclare("hello_world",true,false,false,null);

        // 6、创建消费者，并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * @author Echo
             * handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
             * 参数解释：
             *      consumerTag：消息者标签
             *      envelope：消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志
             *      properties：属性信息
             *      body：消息
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag：" + consumerTag);
                System.out.println("Exchange：" + envelope.getExchange());
                System.out.println("RoutingKey：" + envelope.getRoutingKey());
                System.out.println("properties：" + properties);
                System.out.println("message：" + new String(body));
            }
        };

        // 7、监听消息
        /**
         * @author Echo
         * basicConsume(String queue, boolean autoAck, Consumer callback)
         * 参数解释：
         *      queue：队列名称
         *      autoAck：是否自动确认消息
         *      callback：回调对象
         */
        channel.basicConsume("hello_world",true,consumer);

        // 8、消费者应该一直监听消息，所以不应该关闭连接
    }
}
