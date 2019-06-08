package com.example.rabbitmqdemo.Routing;

import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式:
 *
 * @Description: 消息的生产者（看作是后台系统） , 向交换机中发送消息。
 * @Version: 1.0
 */
public class Send {

    //交换机名称
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        /*
        交换类型
        direct:  direct交换基于消息路由密钥将消息传递到队列。direct交换对于消息的单播路由是理想的（尽管它们也可以用于多播路由）。
        工作原理：
        队列用路由密钥K绑定到交换机
        当具有路由密钥R的新消息到达直接交换时，如果K = R，则交换机将其路由到队列

       direct交换通常用于以循环方式在多个工作者（同一应用程序的实例）之间分配任务。在这样做时，重要的是要理解，在AMQP 0-9-1中，消息在消费者之间而不是在队列之间进行负载平衡。
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "Hello World!";

        //将消息key设置为 delete
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
