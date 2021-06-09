package cn.mirrorming.hello.spring.cloud.rabbit.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqReliabilityConfig.BUSINESS_EXCHANGE_NAME;

@Component
@Slf4j
public class ReliabilityMessageProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    public void sendCustomMsg(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("消息id:{}, msg:{}", correlationData.getId(), msg);

        rabbitTemplate.convertAndSend(BUSINESS_EXCHANGE_NAME, "key", msg, correlationData);

        correlationData = new CorrelationData(UUID.randomUUID().toString());

        log.info("消息id:{}, msg:{}", correlationData.getId(), msg);

        rabbitTemplate.convertAndSend(BUSINESS_EXCHANGE_NAME, "key2", msg, correlationData);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("消息确认成功, id:{}", id);
        } else {
            log.error("消息未成功投递, id:{}, cause:{}", id, s);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息被服务器退回。msg:{}, replyCode:{}. replyText:{}, exchange:{}, routingKey :{}",
                new String(message.getBody()), replyCode, replyText, exchange, routingKey);
    }
}
