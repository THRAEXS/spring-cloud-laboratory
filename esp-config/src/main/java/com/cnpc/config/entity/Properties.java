package com.cnpc.config.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author 鬼王
 * @Date 2018/11/26 13:53
 */
@Data
@Entity
@Table(name = "PROPERTIES")
public class Properties implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String application;

    private String profile;

    private String label;

    private String key;

    private String value;

    @Transient
    private List<Properties> items;

    public Properties() {
    }

    public Properties(String application, String profile, String label, String key, String value) {
        this.application = application;
        this.profile = profile;
        this.label = label;
        this.key = key;
        this.value = value;
    }
}
