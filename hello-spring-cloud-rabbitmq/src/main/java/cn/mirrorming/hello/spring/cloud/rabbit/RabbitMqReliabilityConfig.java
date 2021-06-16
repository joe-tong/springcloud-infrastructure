package cn.mirrorming.hello.spring.cloud.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqReliabilityConfig {

    public static final String BUSINESS_EXCHANGE_NAME = "rabbitmq.backup.test.exchange";
    public static final String BUSINESS_QUEUE_NAME = "rabbitmq.backup.test.queue";
    public static final String BUSINESS_BACKUP_EXCHANGE_NAME = "rabbitmq.backup.test.backup-exchange";
    public static final String BUSINESS_BACKUP_QUEUE_NAME = "rabbitmq.backup.test.backup-queue";
    public static final String BUSINESS_BACKUP_WARNING_QUEUE_NAME = "rabbitmq.backup.test.backup-warning-queue";

    // 声明业务 Exchange
    @Bean("businessTestExchange")
    public DirectExchange businessTestExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(BUSINESS_EXCHANGE_NAME)
                .durable(true)
                .withArgument("alternate-exchange", BUSINESS_BACKUP_EXCHANGE_NAME);

        return (DirectExchange) exchangeBuilder.build();
    }

    // 声明备份 Exchange
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.fanoutExchange(BUSINESS_BACKUP_EXCHANGE_NAME)
                .durable(true);
        return (FanoutExchange) exchangeBuilder.build();
    }

    // 声明业务队列
    @Bean("businessTestQueue")
    public Queue businessTestQueue() {
        return QueueBuilder.durable(BUSINESS_QUEUE_NAME).build();
    }

    // 声明业务队列绑定关系
    @Bean
    public Binding businessTestBinding(@Qualifier("businessTestQueue") Queue queue,
                                   @Qualifier("businessTestExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key");
    }

    // 声明备份队列
    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BUSINESS_BACKUP_QUEUE_NAME).build();
    }

    // 声明报警队列
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(BUSINESS_BACKUP_WARNING_QUEUE_NAME).build();
    }

    // 声明备份队列绑定关系
    @Bean
    public Binding backupBinding(@Qualifier("backupQueue") Queue queue,
                                 @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // 声明备份报警队列绑定关系
    @Bean
    public Binding backupWarningBinding(@Qualifier("warningQueue") Queue queue,
                                        @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
