import cn.mirrorming.hello.spring.cloud.redis.SpringCloudRedisApplication;
import cn.mirrorming.hello.spring.cloud.redis.zset.TaskConsumer;
import cn.mirrorming.hello.spring.cloud.redis.zset.TaskProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {SpringCloudRedisApplication.class})
@RunWith(SpringRunner.class)
public class ZSetScoreDelayTest {

    @Autowired
    private TaskProducer taskProducer;

    @Autowired
    private TaskConsumer taskConsumer;

    @Test
    public  void delay() {
        //创建 3个任务，并设置超时间为 10s 5s 20s
        taskProducer.produce(1, System.currentTimeMillis() + 10000);
        taskProducer.produce(2, System.currentTimeMillis() + 5000);
        taskProducer.produce(3, System.currentTimeMillis() + 20000);

        //消费端从redis中消费任务
        taskConsumer.consumer();
    }
}
