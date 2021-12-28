package com.echo.spring.rm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @className QueueListener
 * @author Echo
 * @description HelloWorld-消费者-监听类
 * @updateTime 2021/12/28 14:19
 * @version 1.0
 */
public class HelloWorld_QueueListener  implements MessageListener {
    /**
     * @methodName onMessage
     * @author Echo
     * @param: message
     * @version 1.0
     * @description 打印消息
     * @updateTime 2021/12/28 14:20
     * @throws
     */
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
