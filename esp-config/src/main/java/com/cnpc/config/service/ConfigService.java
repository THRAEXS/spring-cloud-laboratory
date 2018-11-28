package com.cnpc.config.service;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.entity.KeyValue;

import java.util.List;

/**
 * @Author 鬼王
 * @Date 2018/11/26 15:23
 */
public interface ConfigService {

    void save(Properties properties);

    void saveAll(List<Properties> proList);

    void deleteProperties(String id);

    List<Properties> findAll();

    List<Properties> findByGroup();

    List<KeyValue> findByPid(String pid);

}
