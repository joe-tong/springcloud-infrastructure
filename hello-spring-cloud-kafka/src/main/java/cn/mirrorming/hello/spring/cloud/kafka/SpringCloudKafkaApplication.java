package cn.mirrorming.hello.spring.cloud.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "cn.mirrorming.hello.spring.cloud.kafka.*")
public class SpringCloudKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudKafkaApplication.class, args);
    }

}