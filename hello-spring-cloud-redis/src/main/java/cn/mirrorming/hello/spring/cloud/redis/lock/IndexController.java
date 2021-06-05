package cn.mirrorming.hello.spring.cloud.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/***
 * 分布式锁redisson 的测试
 */
@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 环境：单机并发
     * 问题：库存超库
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/deduct_stock")
    public String deductStock() throws InterruptedException {
        Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
            System.out.println("扣减库存成功，剩余库存：" + realStock + "");
        } else {
            System.out.println("扣减失败，库存不足");
        }
        return stock + "";
    }

    /**
     * 环境：单机
     * 问题：syn 不支持集群
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/deduct_stock2")
    public String deductStock2() throws InterruptedException {
        synchronized (this) {
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减库存成功，剩余库存：" + realStock + "");
            } else {
                System.out.println("扣减失败，库存不足");
            }
            return stock + "";
        }
    }

    /**
     * 环境：集群
     * 问题：
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/deduct_stock3")
    public String deductStock3() throws InterruptedException {
        String lockKey = "lockKey";

        try {
//            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "tpp");
//            stringRedisTemplate.expire(lockKey,10,TimeUnit.SECONDS);

            //setNx+expire 原子命令
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "tpp", 10, TimeUnit.SECONDS);

            if (!result) {
                System.out.println("error");
                return "error";
            }
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减库存成功，剩余库存：" + realStock + "");
            } else {
                System.out.println("扣减失败，库存不足");
            }

        } finally {
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }

    /**
     * 环境：集群
     * redisson
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/deduct_stock4")
    public String deductStock4() throws InterruptedException {
        String lockKey = "lockKey";

        RLock lock = redissonClient.getLock(lockKey);

        try {
            //加锁，实现锁续命功能
            lock.lock();
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减库存成功，剩余库存：" + realStock + "");
            } else {
                System.out.println("扣减失败，库存不足");
            }

        } finally {
            lock.unlock();
        }
        return "end";
    }

}