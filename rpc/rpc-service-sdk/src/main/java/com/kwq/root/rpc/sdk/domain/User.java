package com.kwq.root.rpc.sdk.domain;

import java.io.Serializable;

/**
 * SDK中定义用户实体
 * 注意: 由于User需要在网络中传送，故需要支持序列化
 */
public class User implements Serializable {
    private Long id;
    private String name;
    private String gender;

    public User() {
    }

    public User(Long id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
