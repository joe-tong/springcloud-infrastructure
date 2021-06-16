package cn.mirrorming.hello.spring.cloud.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudRabbitMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRabbitMqApplication.class, args);
    }

}