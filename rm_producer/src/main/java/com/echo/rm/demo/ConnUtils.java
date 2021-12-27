package com.echo.rm.demo;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className ConnUtils
 * @author Echo
 * @description 获取连接工具类
 * @updateTime 2021/12/27 17:29
 * @version 1.0
 */
public class ConnUtils {
    public Connection getConnection() throws IOException, TimeoutException {
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
        return connection;
    }
}
