package cn.mirrorming.spring.cloud.alibaba.consumer.feign.service.fallback;

import cn.mirrorming.spring.cloud.alibaba.consumer.feign.service.ProviderService;
import org.springframework.stereotype.Component;

/**
 * @author sentinel整合feign, 熔断保护
 */
@Component
public class ProviderServiceFallback implements ProviderService {
    @Override
    public String echo(String message) {
        return "sentinel fallback";
    }
}
