package cn.mirrorming.hello.spring.cloud.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqCallbackAckConfig {

    public static final String BUSINESS_CALLBACK_EXCHANGE_NAME = "rabbitmq.producor.callback.business.exchange";
    public static final String BUSINESS_CALLBACK_QUEUE_NAME = "rabbitmq.producor.callback.business.queue";

    // 声明业务Exchange
    @Bean("businessCallbackExchange")
    public DirectExchange businessCallbackExchange(){
        return new DirectExchange(BUSINESS_CALLBACK_EXCHANGE_NAME);
    }

    // 声明业务队列
    @Bean("businessCallbackQueue")
    public Queue businessCallbackQueue(){
        return QueueBuilder.durable(BUSINESS_CALLBACK_QUEUE_NAME).build();
    }

    // 声明业务队列绑定关系
    @Bean
    public Binding businessCallbackBinding(@Qualifier("businessCallbackQueue") Queue queue,
                                   @Qualifier("businessCallbackExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("key");
    }
}
