package com.example.rabbitmqdemo.Fanout;

import com.example.common.enums.ExchangTypeEnum;
import com.example.common.enums.FanoutExchangEnum;
import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.log4j.Logger;


/**
 * @Description: 生产者
 * @Author: sxk
 * @CreateDate: 2020/1/14 10:44
 * @Version: 1.0
 */
public class Send {

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机（参数为：交换机名称; 交换机类型:广播模式）
        channel.exchangeDeclare(FanoutExchangEnum.EXCHANG_1.getName(), ExchangTypeEnum.FANOUT.getType());

        // 消息内容
        String message = "Hello World!!";
        //消息发布（参数为：交换机名称; routingKey，忽略。在广播模式中，生产者声明交换机的名称和类型即可）
        channel.basicPublish(FanoutExchangEnum.EXCHANG_1.getName(), "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
