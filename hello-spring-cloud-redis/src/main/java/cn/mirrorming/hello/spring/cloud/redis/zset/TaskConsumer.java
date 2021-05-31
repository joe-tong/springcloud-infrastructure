package cn.mirrorming.hello.spring.cloud.redis.zset;

import cn.mirrorming.hello.spring.cloud.redis.config.RedisConst;
import cn.mirrorming.hello.spring.cloud.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TaskConsumer {

    @Autowired
    private RedisUtil redisUtil;

    public void consumer() {
        while (true) {
            Set<ZSetOperations.TypedTuple<Object>> tuples = redisUtil.zRangeByScoreWithScores(RedisConst.DelayKey, 0, System.currentTimeMillis(), 0, 1);
            Iterator<ZSetOperations.TypedTuple<Object>> taskIdSetIte = tuples.iterator();
            while (taskIdSetIte.hasNext()) {
                ZSetOperations.TypedTuple<Object> next = taskIdSetIte.next();
                long result = redisUtil.zrem(RedisConst.DelayKey, next.getValue());
                if (result == 1L) {
                    System.out.println("从延时队列中获取到任务，taskId:" + next.getValue() + " , 当前时间：" + LocalDateTime.now());
                }
            }
            log.info("没有消费任务");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}