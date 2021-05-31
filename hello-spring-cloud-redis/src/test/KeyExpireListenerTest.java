import cn.mirrorming.hello.spring.cloud.redis.SpringCloudRedisApplication;
import cn.mirrorming.hello.spring.cloud.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {SpringCloudRedisApplication.class})
@RunWith(value = SpringRunner.class)
public class KeyExpireListenerTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setExpire() throws InterruptedException {
        redisUtil.set("key_expire:", 2, 10L, TimeUnit.SECONDS);
        Thread.sleep(Duration.ofMinutes(5).toMillis());
    }
}
