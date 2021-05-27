package cn.mirrorming.spring.cloud.alibaba.consumer.feign.controller;

import cn.mirrorming.spring.cloud.alibaba.consumer.feign.service.ProviderService;
import cn.mirrorming.spring.cloud.alibaba.consumer.feign.service.SentinelService;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author mirror
 */
@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private SentinelService sentinelService;

    @GetMapping("echo")
    public String echo() {
        return providerService.echo("Feign Client");
    }

    /**
     * sentinel硬编码限流
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/web/getState", method = RequestMethod.GET)
    public String getState(@RequestParam("id") Integer id) {
        Entry entry = null;
        try {
            entry = SphU.entry("getOrder", EntryType.IN, 2);
            return "state=" + id;
        } catch (BlockException e) {
            e.printStackTrace();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return "State Error";
    }

    /**
     * @return
     * @SentinelResource: 限流
     */
    @RequestMapping(value = "/web/block", method = RequestMethod.GET)
    public String getStateLimitAspect() {
        return sentinelService.doBlock("state");
    }

    /**
     * @return
     * @SentinelResource: 熔断
     */
    @RequestMapping(value = "/web/fallback", method = RequestMethod.GET)
    public String getStateFallBackAspect() {
        return sentinelService.doFallback("state");
    }
}
