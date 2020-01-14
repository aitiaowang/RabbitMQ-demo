package com.example.rabbitmqdemo.Fanout;

import com.example.common.enums.ExchangTypeEnum;
import com.example.common.enums.FanoutExchangEnum;
import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Description: 消费者
 * @Author: sxk
 * @CreateDate: 2020/1/14 11:29
 * @Version: 1.0
 */
public class Recv1 {

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //交换机声明（参数为：交换机名称；交换机类型）
        channel.exchangeDeclare(FanoutExchangEnum.EXCHANG_1.getName(), ExchangTypeEnum.FANOUT.getType());
        //获取一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
        channel.queueBind(queueName, FanoutExchangEnum.EXCHANG_1.getName(), "");

        System.out.println("********Waiting for messages********");
        //这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println("received: ===========" + message);
            }
        };
        //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
        channel.basicConsume(queueName, true, consumer);
        //这里不能关闭连接，调用了消费方法后，消费者会一直连接着rabbitMQ等待消费
    }
}
