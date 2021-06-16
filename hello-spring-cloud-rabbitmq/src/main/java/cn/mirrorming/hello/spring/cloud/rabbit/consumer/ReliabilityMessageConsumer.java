package cn.mirrorming.hello.spring.cloud.rabbit.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqReliabilityConfig.*;

@Slf4j
@Component
public class ReliabilityMessageConsumer {

    @RabbitListener(queues = BUSINESS_BACKUP_WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.error("【警告】发现不可路由消息：{}", msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = BUSINESS_BACKUP_QUEUE_NAME)
    public void receiveBackUpMsg(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.error("【备份】发现不可路由消息：{}", msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = BUSINESS_QUEUE_NAME)
    public void receiveMsg(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("收到业务消息：{}", msg);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

}
