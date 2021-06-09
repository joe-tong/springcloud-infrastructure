package cn.mirrorming.hello.spring.cloud.rabbit.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqPriopityConfig.PRIORITY_EXCHANGE;

@Component
public class PriorityMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String ROUTING_KEY_PREFIX = "priority.queue.";

    public void sendPriorityMsg(String msg,Integer priority) {
        rabbitTemplate.convertAndSend(PRIORITY_EXCHANGE, ROUTING_KEY_PREFIX + "test", msg,
                message -> {
                    message.getMessageProperties().setPriority(priority);
                    return message;
                });
    }
}