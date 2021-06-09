package cn.mirrorming.hello.spring.cloud.rabbit.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqDelayConfig.DELAY_EXCHANGE_NAME;
import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqDelayConfig.DELAY_QUEUE_ROUTING_KEY;

@Component
public class DelayMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, Integer delay) {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration(String.valueOf(delay));
        Message message = rabbitTemplate
                .getMessageConverter()
                .toMessage(msg, properties);

        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUE_ROUTING_KEY, message);
    }
}