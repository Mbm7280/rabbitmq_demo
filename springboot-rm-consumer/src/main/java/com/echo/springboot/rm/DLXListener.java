package com.echo.springboot.rm;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className DLXListener
 * @author Echo
 * @description 死信队列-监听类
 * @updateTime 2022/1/2 13:06
 * @version 1.0
 */
@Component
public class DLXListener {
    @RabbitListener(queues = "test_dlx_queue")
    public void TopicQueueListener(Message msg,Channel channel) throws Exception {
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        try {
            Thread.sleep(2000);
            // 1、接受转换的消息
            System.out.println(new String(msg.getBody()));
            // 2、业务逻辑处理
            // 手动异常
            int i = 3 / 0;
            System.out.println("业务逻辑处理中-------------");
            // 3、手动签收
            /**
             * @author Echo
             * basicAck(long deliveryTag, boolean multiple)
             * 参数解释：
             *      deliveryTag：为每条信息带有的tag值
             *      multiple：为true时会将小于等于此次tag的所有消息都确认掉，为false则只确认当前tag的信息
             */
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            // 4、接受失败策略
            /**
             * @author Echo
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * 参数解释：
             *      deliveryTag：同上
             *      multiple：同上
             *      requeue：如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端,false 则会直接将此消息丢弃
             */
            channel.basicNack(deliveryTag,true,false);
        }
    }
}
