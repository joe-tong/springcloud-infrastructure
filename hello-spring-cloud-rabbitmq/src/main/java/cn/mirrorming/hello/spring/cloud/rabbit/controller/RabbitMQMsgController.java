package cn.mirrorming.hello.spring.cloud.rabbit.controller;

import cn.mirrorming.hello.spring.cloud.rabbit.producer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Autowired
    private DelayMessageProducer delayMessageProducer;

    @Autowired
    private PriorityMessageProducer priorityMessageProducer;

    @Autowired
    private TransactionMessageProducer transactionMessageProducer;

    @Autowired
    private CallBackMessageProducer callBackMessageProducer;

    @Autowired
    private ReliabilityMessageProducer reliabilityMessageProducer;

    /**
     * 发送延迟队列
     *
     * @param msg
     * @param delay
     */
    @RequestMapping("sendmsg")
    public void sendMsg(String msg, Integer delay) {
        log.info("当前时间：{},收到请求，msg:{},delay:{}", new Date(), msg, delay);
        delayMessageProducer.sendMsg(msg, delay);
    }

    /**
     * 发送优先队列
     *
     * @param msg
     */
    @RequestMapping("sendmsg/prioirty")
    public void sendPriorityMsg(String msg) {
        log.info("当前时间：{},收到请求，msg:{}", new Date(), msg);
        Integer[] priortys = {9, 8, 7, 6, 1, 2, 3, 4, 5};
        for (Integer priority : priortys) {
            priorityMessageProducer.sendPriorityMsg(msg + ":" + priority, priority);
        }
    }

    /**
     * 发送事务
     *
     * @param msg
     */
    @RequestMapping("sendmsg/tx")
    public void sendTxMsg(String msg) {
        log.info("当前时间：{},收到请求，msg:{}", new Date(), msg);
        transactionMessageProducer.sendMsg(msg);
    }

    /**
     * 发送生产者Callback
     *
     * @param msg
     */
    @RequestMapping("sendmsg/callback")
    public void sendCallbackMsg(String msg) {
        log.info("当前时间：{},收到请求，msg:{}", new Date(), msg);
        callBackMessageProducer.sendMsg(msg);
    }


    /**
     * 发送生产者消息可靠性最终方案
     * 1.业务交换机关联一个备份交换机
     * 2.到达rabbitmq服务器，如果没有找到key，则会转发到备份交换机消费调
     *
     * @param msg
     */
    @RequestMapping("sendmsg/reliable")
    public void sendReliableMsg(String msg) {
        log.info("当前时间：{},收到请求，msg:{}", new Date(), msg);
        reliabilityMessageProducer.sendCustomMsg(msg);
    }
}