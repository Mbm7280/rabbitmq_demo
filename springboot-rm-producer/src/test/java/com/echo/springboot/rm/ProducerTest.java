package com.echo.springboot.rm;

import com.echo.springboot.rm.config.RmConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @className ProducerTest
 * @author Echo
 * @description 生产者
 * @updateTime 2021/12/28 20:52
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    // 1、注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @author Echo
     * 正常投递
     */
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend(RmConfig.EXCHANGE_NAME,"t1.topic.test","hello springboot topic");
    }

    /**
     * @author Echo
     * 生产者的 confirm 模式
     * 生产者的投递路径： producer--->rabbitmq broker--->exchange
     * 具体操作：
     *      1、配置文件中配置 confirm 模式的开启
     *          publisher-confirms: true
     *
     *      2、设置确认回调机制
     *          通过rabbitTemplate.setConfirmCallback(RabbitTemplate.ConfirmCallback confirmCallback)
     *          ConfirmCallback对象对应的参数：
     *              confirm(@NonNull CorrelationData correlationData, boolean ack, @Nullable String cause)
     *              CorrelationData：相关配置信息
     *              ack：exchange交换机是否成功收到了消息。true 成功，false代表失败
     *              cause：失败原因
     */
    @Test
    public void testConfirm(){
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            System.out.println("confirm 方法被指行了.........");
            if(b){
                System.out.println("接受消息成功" + s);
            }else{
                System.out.println("接受失败" + s);
            }
        });
        // 正常情况
        rabbitTemplate.convertAndSend("springboot_topic_exchange","t1.topic.test","hello springboot topic");
        // 模拟异常
        rabbitTemplate.convertAndSend("springboot_topic_exchange1","t1.topic.test","hello springboot topic");
    }

    /**
     * @author Echo
     * 回退模式
     */
    @Test
    public void testReturn(){
        // 1、设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);
        // 2、设置ReturnCallBack
        /**
         * @author Echo
         * setReturnCallback(ReturnCallback)
         *      需要传入一个ReturnCallback对象
         * ReturnCallback：
         *      returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey)
         * 参数解释：
         *      message：消息对象
         *      replyCode：错误码
         *      replyText：错误信息
         *      exchange：交换机名称
         *      routingKey：路由键
         */
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey) -> {
            System.out.println("return 执行了....");
            System.out.println(message);
            System.out.println(replyCode);
            System.out.println(replyText);
            System.out.println(exchange);
            System.out.println(routingKey);
        });
        // 正常情况
        rabbitTemplate.convertAndSend(RmConfig.EXCHANGE_NAME,"t1.topic.test","hello springboot topic");
        // 失败情况
        // rabbitTemplate.convertAndSend(RmConfig.EXCHANGE_NAME,"t2.topic.test","hello springboot topic");
    }


    @Test
    public void testPreftch(){
       for (int i = 0; i < 10; i++) {
           rabbitTemplate.convertAndSend(RmConfig.EXCHANGE_NAME,"t1.topic.test","hello springboot topic");
       }
    }

}
