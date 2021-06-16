package cn.mirrorming.hello.spring.cloud.rabbit;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfiguration {

	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.port}")
	private Integer port;

//    @Value("${rabbitmq.vhost}")
//    private String vhost;

    public ConnectionFactory taskConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(vhost);
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);//生产者是自动否确认（跟事务机制互斥）
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setChannelCacheSize(20);
        return connectionFactory;
    }

    @Bean
    public MessagePostProcessor messagePostProcessor() {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message, Correlation correlation) {
                MessageProperties messageProperties = message.getMessageProperties();

                if (correlation instanceof CorrelationData) {
                    String correlationId = ((CorrelationData) correlation).getId();
                    messageProperties.setCorrelationId(correlationId);
                }
                // 可以设置持久化，但与本文无关，因此没有附上
                return message;
            }

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                return message;
            }
        };
        return messagePostProcessor;
    }

    @Bean("taskAdmin")
    public RabbitAdmin taskAdmin() {
        return new RabbitAdmin(taskConnectionFactory());
    }

    @Bean
    @Primary
    public RabbitTemplate taskRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(taskConnectionFactory());
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.setReplyTimeout(180);
        return rabbitTemplate;
    }

    @Bean("taskRabbitListener")
    public SimpleRabbitListenerContainerFactory taskRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(taskConnectionFactory());
        factory.setMaxConcurrentConsumers(3);
        factory.setChannelTransacted(false);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}

