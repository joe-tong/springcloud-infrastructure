package cn.mirrorming.hello.spring.cloud.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTransactionConfig {

    public static final String BUSINESS_EXCHANGE_NAME = "rabbitmq.tx.demo.simple.business.exchange";
    public static final String BUSINESS_QUEUE_NAME = "rabbitmq.tx.demo.simple.business.queue";

    // 声明业务Exchange
    @Bean("businessExchange")
    public FanoutExchange businessExchange(){
        return new FanoutExchange(BUSINESS_EXCHANGE_NAME);
    }

    // 声明业务队列
    @Bean("businessQueue")
    public Queue businessQueue(){
        return QueueBuilder.durable(BUSINESS_QUEUE_NAME).build();
    }

    // 声明业务队列绑定关系
    @Bean
    public Binding businessBinding(@Qualifier("businessQueue") Queue queue,
                                   @Qualifier("businessExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }


    /**
     * 配置启用rabbitmq事务
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}
