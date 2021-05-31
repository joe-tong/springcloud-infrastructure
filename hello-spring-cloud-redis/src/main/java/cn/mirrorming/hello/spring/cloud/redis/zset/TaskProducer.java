package cn.mirrorming.hello.spring.cloud.redis.zset;

import cn.mirrorming.hello.spring.cloud.redis.config.RedisConst;
import cn.mirrorming.hello.spring.cloud.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskProducer {

    @Autowired
    private RedisUtil redisUtil;

    public void produce(Integer taskId, long exeTime) {
        System.out.println("加入任务， taskId: " + taskId + ", exeTime: " + exeTime + ", 当前时间：" + LocalDateTime.now());
        redisUtil.zadd(RedisConst.DelayKey, exeTime, String.valueOf(taskId));
    }
}