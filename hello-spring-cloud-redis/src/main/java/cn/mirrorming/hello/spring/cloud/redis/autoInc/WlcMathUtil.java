package cn.mirrorming.hello.spring.cloud.redis.autoInc;

/**
 * 描述: 数字util </br>
 * 时间: 2021-06-18 10:26  </br>
 * 作者：王林冲
 */
public class WlcMathUtil {
    /**
     * 获取10的幂次方 int 数字
     * @param incrDigit 位数-幂次方
     * @return Int-0的幂次方数
     */
    public static Integer getMaxIncrNum(Integer incrDigit){
        double pow = Math.pow(10, incrDigit);
        return (int)pow;
    }
}
