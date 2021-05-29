package cn.mirrorming.hello.spring.cloud.shardings.service;

import cn.mirrorming.hello.spring.cloud.shardings.bean.User;

public class ServiceImpl<E, V> implements IService {


    @Override
    public boolean save(User entity) {
        return false;
    }
}
