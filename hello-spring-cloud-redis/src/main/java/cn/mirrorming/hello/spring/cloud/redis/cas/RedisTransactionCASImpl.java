package cn.mirrorming.hello.spring.cloud.redis.cas;

import cn.mirrorming.hello.spring.cloud.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class RedisTransactionCASImpl {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private CountDownLatch countDownLatch = new CountDownLatch(200);

    public void decCounts() throws InterruptedException {
        redisUtil.set("counts", 100);
        for (int i = 0; i < 200; i++) {
            new Thread(new DecCount()).start();
        }
        countDownLatch.await();
    }

    class DecCount implements Runnable {

        @Override
        public void run() {
            decCounts();
        }

        public void decCounts() {
            countDownLatch.countDown();
            flushdb();
            ValueOperations<String, String> vo = redisTemplate.opsForValue();
            redisTemplate.setEnableTransactionSupport(true);
            //监控key
            redisTemplate.watch("counts");
            Object counts1 = redisUtil.get("counts");
            log.debug("库存数据{}",counts1);
            Integer counts = (Integer) counts1;
            if (counts <= 0) {
                log.debug("库存已经扣除完");
            }
            //开启事务
            redisTemplate.multi();
            vo.decrement("counts", 1);
            redisTemplate.exec();
        }
    }

    public void flushdb() {
        redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}