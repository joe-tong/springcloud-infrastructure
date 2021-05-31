package cn.mirrorming.hello.spring.cloud.redis.zset;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestRedisDelay {

    public static void main(String[] args) {
        TaskProducer taskProducer = new TaskProducer();
        //创建 3个任务，并设置超时间为 10s 5s 20s
        taskProducer.produce(1, System.currentTimeMillis() + 10000);
        taskProducer.produce(2, System.currentTimeMillis() + 5000);
        taskProducer.produce(3, System.currentTimeMillis() + 20000);

        //消费端从redis中消费任务
        TaskConsumer taskConsumer = new TaskConsumer();
        taskConsumer.consumer();
    }
}
