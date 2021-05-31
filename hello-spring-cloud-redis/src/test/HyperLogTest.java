import cn.mirrorming.hello.spring.cloud.redis.SpringCloudRedisApplication;
import cn.mirrorming.hello.spring.cloud.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 做基数统计的算法
 */
@SpringBootTest(classes = {SpringCloudRedisApplication.class})
@RunWith(value = SpringRunner.class)
public class HyperLogTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void hl(){
//        for (int i = 0; i < 10000; i++) {
//            redisUtil.pfadd("pf",String.valueOf(i));
//        }
        Long count = redisUtil.pfCount("pf");
        System.out.println(count);
    }}
