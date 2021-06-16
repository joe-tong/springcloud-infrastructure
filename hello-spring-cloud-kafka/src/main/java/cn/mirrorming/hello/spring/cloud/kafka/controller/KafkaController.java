package cn.mirrorming.hello.spring.cloud.kafka.controller;

import cn.mirrorming.hello.spring.cloud.kafka.service.KafkaProducer;
import cn.mirrorming.hello.spring.cloud.kafka.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/send")
    public void send(String msg) {
        kafkaProducer.send(msg);
    }
}
