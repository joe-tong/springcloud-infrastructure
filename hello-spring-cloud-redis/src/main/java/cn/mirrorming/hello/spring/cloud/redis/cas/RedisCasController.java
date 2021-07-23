package cn.mirrorming.hello.spring.cloud.redis.cas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisCasController {

    @Autowired
    private RedisTransactionCASImpl redisTransactionCAS;

    @RequestMapping("/cas/trans")
    public void casTrans() throws InterruptedException {
        redisTransactionCAS.decCounts();
    }
}
