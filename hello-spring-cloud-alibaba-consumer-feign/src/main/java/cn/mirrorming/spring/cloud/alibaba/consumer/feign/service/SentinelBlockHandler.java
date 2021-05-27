package cn.mirrorming.spring.cloud.alibaba.consumer.feign.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class SentinelBlockHandler {

    /**
     * 必须是静态方法
     *
     * @param str
     * @param ex
     * @return
     */
    public static String blockHandler(String str, BlockException ex) {
        System.out.println("blockHandler" + str + ex.getMessage());
        return "阻塞了";
    }
}
