package cn.mirrorming.hello.spring.cloud.rabbit.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqPriopityConfig.PRIORITY_QUEUE;

@Slf4j
@Component
public class PriorityMessageConsumer {

    @RabbitListener(queues = PRIORITY_QUEUE, admin = "taskAdmin", containerFactory = "taskRabbitListener")
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},优先队列收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
