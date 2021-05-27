package cn.mirrorming.spring.cloud.alibaba.consumer.feign;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author mirror
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignApplication.class, args);
    }

    // 注解支持的配置Bean
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
