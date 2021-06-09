package cn.mirrorming.hello.spring.cloud.rabbit.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqCallbackAckConfig.BUSINESS_CALLBACK_EXCHANGE_NAME;

/**
 * 发送消息到rabbitmq，如果没有消息key，直接回调被退回</br>
 *
 */
@Component
@Slf4j
public class CallBackMessageProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 配置：publisher-confirms: true
     */
    @PostConstruct
    private void init() {
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setReturnCallback(this);
    }

    public void sendMsg(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(BUSINESS_CALLBACK_EXCHANGE_NAME, "key", msg, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("消息确认成功, id:{}", id);
        } else {
            log.error("消息未成功投递, id:{}, cause:{}", id, ack);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息被服务器退回。msg:{}, replyCode:{}. replyText:{}, exchange:{}, routingKey :{}",
                new String(message.getBody()), replyCode, replyText, exchange, routingKey);
    }

}