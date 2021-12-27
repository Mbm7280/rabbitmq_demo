package com.echo.rm.demo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Producer_PubSub
 * @author Echo
 * @description 订阅模式-生产者
 * @updateTime 2021/12/27 20:12
 * @version 1.0
 */
public class Producer_PubSub {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、获取连接
        ConnUtils cs = new ConnUtils();
        Connection connection = cs.getConnection();

        // 2、创建通道
        Channel channel = connection.createChannel();

        // 3、创建交换机
        /**
         * @author Echo
         * exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
         * 参数解释：
         *      exchange：交换机的名字
         *      type：交换机的类型
         *           DIRECT("direct"),：定向
         *           FANOUT("fanout"),：扇形（广播），发送消息到每一个与之绑定队列。
         *           TOPIC("topic"),通配符的方式
         *           HEADERS("headers");参数匹配
         *      durable：是否持久化
         *      autoDelete：是否自动删除
         *      internal：一般使用 false,如果设置为true，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式
         *      arguments：参数
         *
         */
        String exchangeName = "test_fanout";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,true,false,false,null);

        // 4、创建队列
        String queue1Name = "test_fanout_queue1";
        String queue2Name = "test_fanout_queue2";
        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);

        // 5、绑定队列和交换机
        /**
         * @author Echo
         * queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
         * 参数解释：
         *      queue：队列名称
         *      exchange：交换机名称
         *      routingKey：路由的规则
         *          如果交换机类型为 fanout，routingKey设置为 ""
         *      arguments：参数
         */
        channel.queueBind(queue1Name,exchangeName,"");
        channel.queueBind(queue2Name,exchangeName,"");

        // 6、发送消息
        String body = "fanout test";
        channel.basicPublish(exchangeName,"",null,body.getBytes());

        // 7、关闭资源
        channel.close();
        connection.close();
    }
}
