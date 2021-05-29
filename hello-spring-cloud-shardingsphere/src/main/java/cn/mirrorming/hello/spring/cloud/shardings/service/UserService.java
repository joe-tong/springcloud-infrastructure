package cn.mirrorming.hello.spring.cloud.shardings.service;


import cn.mirrorming.hello.spring.cloud.shardings.bean.User;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     * @param entity
     * @return
     */
    @Override
    boolean save(User entity);

    /**
     * 查询全部用户信息
     * @return
     */
    List<User> getUserList();
}

