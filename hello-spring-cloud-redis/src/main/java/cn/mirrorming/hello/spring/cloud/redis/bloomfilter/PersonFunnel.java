package cn.mirrorming.hello.spring.cloud.redis.bloomfilter;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import java.nio.charset.Charset;

public enum PersonFunnel implements Funnel<Person> {
    INSTANCE;

    @Override
    public void funnel(Person person, PrimitiveSink into) {
        into.putString(person.getFistName(), Charset.defaultCharset())
                .putString(person.getLastName(), Charset.defaultCharset());
    }
}
