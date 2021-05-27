package cn.mirrorming.spring.cloud.alibaba.consumer.feign.service;

public class SentinelFallbackHandler {

    public static String fallbackHandler(String str) {
        System.out.println("fallbackHandler" + str);
        return "降级了";
    }
}
