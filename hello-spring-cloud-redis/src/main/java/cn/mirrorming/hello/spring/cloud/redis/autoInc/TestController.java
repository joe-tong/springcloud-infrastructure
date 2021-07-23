package cn.mirrorming.hello.spring.cloud.redis.autoInc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisUniqueNoUtil redisUniqueNoUtil;

    @RequestMapping("/getUniqueIDTest")
    public void getUniqueIDTest(){
        for (int i = 0; i < 20; i++){
            //时间格式=yyyyMMddHHmmss，到秒级别，过期常量 = 1，时间单位 = 秒
            //自增拼接位数 = 4
            //描述：时间格式=yyyyMMddHHmmss，每秒自增，最大数字拼接长度4位
            //应用场景自增主键id，或业务自定唯一id
            long uniqueID = redisUniqueNoUtil.getUniqueNo(DateFormatConstant.yyyyMMddHHmmss, 1, TimeUnit.SECONDS,4 );
            System.out.println(uniqueID);
        }

    }

    @RequestMapping("/getgetOrderNoTest")
    public void getgetOrderNoTest(){
        for (int i = 0; i < 20; i++){
            // prefixFlag = 订单号前置标志位，ex：11-商城订单，12-直播课订单，13-拍卖订单，可以用枚举类统一
            //时间格式=yyyyMMddHHmmss，到秒级别，过期常量 = 1，时间单位 = 秒
            //自增拼接位数 = 4
            //描述：时间格式=yyyyMMddHHmmss，每秒自增，最大数字拼接长度4位
            //应用场景自增订单号
            String orderNo = redisUniqueNoUtil.getOrderNo("11", DateFormatConstant.yyyyMMddHHmmss, 1, TimeUnit.SECONDS,4 );
            System.out.println(orderNo);
        }

    }


}
