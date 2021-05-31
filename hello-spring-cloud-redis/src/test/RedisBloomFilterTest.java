import cn.mirrorming.hello.spring.cloud.redis.SpringCloudRedisApplication;
import cn.mirrorming.hello.spring.cloud.redis.bloomfilter.BloomFilterHelper;
import cn.mirrorming.hello.spring.cloud.redis.bloomfilter.Person;
import cn.mirrorming.hello.spring.cloud.redis.bloomfilter.PersonFunnel;
import cn.mirrorming.hello.spring.cloud.redis.bloomfilter.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {SpringCloudRedisApplication.class})
@RunWith(SpringRunner.class)
public class RedisBloomFilterTest {

    @Autowired
    private RedisService redisService;

    public BloomFilterHelper<Person> bloomFilterHelper = new BloomFilterHelper<>(PersonFunnel.INSTANCE, 10, 0.01);


    @Test
    public void addByBloomFilter() {
        redisService.addByBloomFilter(bloomFilterHelper, "key", new Person("童", "平平"));
    }

    @Test
    public void includeByBloomFilter() {
        boolean result = redisService.includeByBloomFilter(bloomFilterHelper, "key", new Person("童", "平平"));
        System.out.println(result);
    }
}