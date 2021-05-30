package cn.mirrorming.hello.spring.cloud.shardings.repository;

import cn.mirrorming.hello.spring.cloud.shardings.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
