package cn.mirrorming.hello.spring.cloud.redis.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 注意：
 * <p>
 * 1）由于redis key过期删除是定时+惰性，当key过多时，删除会有延迟，回调通知同样会有延迟。
 * <p>
 * 2）且通知是一次性的，没有ack机制，若收到通知后处理失败，将不再收到通知。需自行保证收到通知后处理成功。
 * <p>
 * 3）通知只能拿到key，拿不到value
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("expiredKey=========" + expiredKey);
        System.out.println(new String(message.getBody()));
        System.out.println(new String(message.getChannel()));
        System.out.println(new String(pattern));
    }
}