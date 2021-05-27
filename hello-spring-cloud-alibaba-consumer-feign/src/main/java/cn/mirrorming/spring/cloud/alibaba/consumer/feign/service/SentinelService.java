package cn.mirrorming.spring.cloud.alibaba.consumer.feign.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelService {

    @SentinelResource(value = "doBlock", blockHandlerClass = SentinelBlockHandler.class, blockHandler = "blockHandler")
    public String doBlock(String str) {
        return str;
    }

    @SentinelResource(value = "doFallback", fallbackClass = SentinelFallbackHandler.class, fallback = "fallbackHandler")
    public String doFallback(String str) {
        throw new RuntimeException("fall back test");
    }

}
