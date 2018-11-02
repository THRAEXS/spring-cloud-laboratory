package pers.guiwang.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 鬼王
 * @Date 2018/11/02 15:28
 */
@Data
public class User implements Serializable {

    String id;

    String name;

    String account;

    String password;

    public User() { }

    public User(String id, String name, String account, String password) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
    }
}
