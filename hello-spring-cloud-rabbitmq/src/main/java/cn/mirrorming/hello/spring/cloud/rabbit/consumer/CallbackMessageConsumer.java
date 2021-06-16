package cn.mirrorming.hello.spring.cloud.rabbit.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqCallbackAckConfig.BUSINESS_CALLBACK_QUEUE_NAME;

@Component
@Slf4j
public class CallbackMessageConsumer {

    @RabbitListener(queues = BUSINESS_CALLBACK_QUEUE_NAME, admin = "taskAdmin", containerFactory = "taskRabbitListener")
    public void receiveMsg(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},回调队列收到消息：{}", new Date().toString(), msg);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }
}

