package com.example.rabbitmqdemo.simplequeue;

import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者  消费者从队列中获取消息
 *
 * @Description: 简单队列,生产者将消息发送到队列，消费者从队列中获取消息。
 * @Author: sxk
 * @Version: 1.0
 */
public class Producer {

    //队列名称
    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列，
        /*
         参数含义
         b => durable  true、false true：在服务器重启时，能够存活
         b1 => exclusive  是否为当前连接的专用队列，在连接断开后，会自动删除该队列，生产环境中应该很少用到。
         b2 => autodelete：当没有任何消费者使用时，自动删除该队列。这意味着当不再有进程使用队列中的消息时，队列将被删除。
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
