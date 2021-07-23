package cn.mirrorming.hello.spring.cloud.redis.autoInc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 描述: redis的唯一数字util </br>
 *      redis的自增序列号，唯一订单号 </br>
 * 时间: 2021-06-18 9:53  </br>
 * 作者：王林冲
 */
@Slf4j
@Component
public class RedisUniqueNoUtil {
    //设置6自增6位，用于补全操作
    private static final String STR_FORMAT = "000000";

    //自增4位,用于补全用户唯一标记操作
    private static final String USER_UNIQUE_SIGN_STR_FORMAT = "0000";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 基于redis的分布式订单号
     * @param prefixFlag 订单号前置标志位
     * @param liveTime      在redis中的缓存时间，方法中设置单位(秒/分/天……)
     * @param timeUnit      时间单位
     * @param incrDigit     单位时间内自增的位数
     * @return 订单号字符串
     */
    public String getOrderNo(String prefixFlag, SimpleDateFormat sdf, long liveTime, TimeUnit timeUnit, Integer incrDigit){
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isBlank(prefixFlag)){
            log.info("订单标志位为空!");
            throw new RuntimeException("订单序列号获取异常");
        }
        sb.append(prefixFlag);               //标志位
        sb.append(sdf.format(new Date()));   //年月日时分秒
        String prefixStr = sb.toString();
        String no = incrUniqueNumStr(prefixStr, liveTime, timeUnit, incrDigit);
        sb.append(no);
        return sb.toString();
    }

    /**
     * 基于redis生成用户唯一标记号
     * @return 用户唯一标记字符串
     */
    public long getUniqueNo(SimpleDateFormat sdf, long liveTime, TimeUnit timeUnit, Integer incrDigit){
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()));     //年月日时分秒
        String prefixStr = sb.toString();
        String no = incrUniqueNumStr(prefixStr, liveTime, timeUnit, incrDigit);
        sb.append(no);
        return Long.parseLong(sb.toString());
    }

    /**
     *
     * @param key           自己设置，保存当前自增值
     * @param liveTime      在redis中的缓存时间，方法中设置单位(秒/分/天……)
     * @param timeUnit      时间单位
     * @param incrDigit     单位时间内自增的位数
     * @return  String-返回的自增序列号
     */
    public String incrUniqueNumStr(String key, long liveTime, TimeUnit timeUnit, Integer incrDigit) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        int maxIncrNum =WlcMathUtil.getMaxIncrNum(incrDigit);
        if ((increment == null || increment == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, timeUnit);    //设置自增值过期时间，过期之后getAndIncrement()会置0
        }
        if (increment > (maxIncrNum - 1)){
            increment = 1L;
        }
        //位数不够，前面补0
        DecimalFormat df = new DecimalFormat(WlcStrUtil.repeat('0', incrDigit));
        return df.format(increment);
    }


}
