package cn.mirrorming.hello.spring.cloud.shardings.service;

import cn.mirrorming.hello.spring.cloud.shardings.bean.User;

public interface IService<T> {
    /**
     * 保存用户信息
     * @param entity
     * @return
     */
    boolean save(User entity);
}
