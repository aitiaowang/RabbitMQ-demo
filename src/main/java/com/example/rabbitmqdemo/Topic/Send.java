package com.example.rabbitmqdemo.Topic;

import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式（通配符模式）
 * Topic Exchange -- 将路由键和某模式进行匹配。此时队列需要绑定在一个模式上。符号"#"匹配一个或多个词，符号"*"匹配不多不少一个词。
 * 因此“audit.#”能够匹配到"audit.irs.com",但是"audit."只会匹配到"audit.irs"。
 *
 *
 * 同一个消息被多个消费者获取。一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费到消息。
 *
 * @Description: 生产者
 * @Author: sxk
 * @Version: 1.0
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 消息内容
        String message = "Hello World!!";
        channel.basicPublish(EXCHANGE_NAME, "routekey.1", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
