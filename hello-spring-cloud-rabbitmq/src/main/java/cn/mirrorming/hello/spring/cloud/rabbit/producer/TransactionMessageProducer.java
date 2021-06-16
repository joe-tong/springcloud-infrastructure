package cn.mirrorming.hello.spring.cloud.rabbit.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static cn.mirrorming.hello.spring.cloud.rabbit.RabbitMqTransactionConfig.BUSINESS_EXCHANGE_NAME;

@Component
@Slf4j
public class TransactionMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 配置：publisher-confirms: false
     */
//    @PostConstruct
    private void init() {
        rabbitTemplate.setChannelTransacted(true);
    }

    @Transactional
    public void sendMsg(String msg) {
        rabbitTemplate.convertAndSend(BUSINESS_EXCHANGE_NAME, "key", msg);
        log.info("msg:{}", msg);
        if (msg != null && msg.contains("exception"))
            throw new RuntimeException("surprise!");
        log.info("消息已发送 {}", msg);
    }
}