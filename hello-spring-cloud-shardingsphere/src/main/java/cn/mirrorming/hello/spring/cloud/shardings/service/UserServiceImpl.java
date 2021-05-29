package cn.mirrorming.hello.spring.cloud.shardings.service;

import cn.mirrorming.hello.spring.cloud.shardings.bean.User;
import cn.mirrorming.hello.spring.cloud.shardings.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean save(User entity) {
        userRepository.save(entity);
        return true;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

}