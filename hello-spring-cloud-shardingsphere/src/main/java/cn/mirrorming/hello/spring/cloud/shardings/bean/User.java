package cn.mirrorming.hello.spring.cloud.shardings.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * 主键Id
     */
    @Id
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private int age;
}

