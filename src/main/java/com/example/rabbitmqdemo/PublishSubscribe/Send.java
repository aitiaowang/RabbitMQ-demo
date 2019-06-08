package com.example.rabbitmqdemo.PublishSubscribe;

import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式:
 * 1、1个生产者，多个消费者
 * 2、每一个消费者都有自己的一个队列
 * 3、生产者没有将消息直接发送到队列，而是发送到了交换机
 * 4、每个队列都要绑定到交换机
 * 5、生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
 * 注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
 *
 * 测试结果：
 * 同一个消息被多个消费者获取。一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费到消息。
 *
 * @Description: 消息的生产者（看作是后台系统） , 向交换机中发送消息。
 * 注意： 消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中
 * @Version: 1.0
 */
public class Send {

    //交换机名称
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        /*
        交换类型
       fanout:  fanout交换将消息路由到绑定到它的所有队列，并忽略路由密钥。如果N队列绑定到扇出交换，则当向该交换发布新消息时，
       该消息的副本将被传递到所有N个队列。扇出交换是消息广播路由的理想选择
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String message = "Hello World!";
        /*
        routingKey：路由键，#匹配0个或多个单词，*匹配一个单词，在topic exchange做消息转发用
         mandatory：true：如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返还给生产者。false：出现上述情形broker会直接将消息扔掉
         immediate：true：如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
         BasicProperties ：需要注意的是BasicProperties.deliveryMode，0:不持久化 1：持久化 这里指的是消息的持久化，配合channel(durable=true),queue(durable)可以实现，即使服务器宕机，消息仍然保留

         简单来说：mandatory标志告诉服务器至少将该消息route到一个队列中，否则将消息返还给生产者；
         immediate标志告诉服务器如果该消息关联的queue上有消费者，则马上将消息投递给它，如果所有queue都没有消费者，直接把消息返还给生产者，不用将消息入队列等待消费者了。
         */
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
