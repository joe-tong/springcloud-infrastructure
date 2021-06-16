package cn.mirrorming.hello.spring.cloud.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqPriopityConfig {

    /**
     * 优先队列
     */
    public static final String PRIORITY_EXCHANGE = "priority-exchange-1";
    public static final String PRIORITY_QUEUE = "priority-queue-1";
    public static final String PRIORITY_ROUTING_KEY = "priority.queue.#";

    /**
     * 定义优先级队列
     */
    @Bean
    Queue queue() {
        Map<String, Object> args= new HashMap<>();
        args.put("x-max-priority", 100);
        return new Queue(PRIORITY_QUEUE, false, false, false, args);
    }

    /**
     * 定义交换器
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(PRIORITY_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PRIORITY_ROUTING_KEY);
    }

}
