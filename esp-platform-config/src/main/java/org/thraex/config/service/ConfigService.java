package org.thraex.config.service;

import org.thraex.config.entity.KeyValue;
import org.thraex.config.entity.Properties;

import java.util.List;

/**
 * @Author 鬼王
 * @Date 2018/11/26 15:23
 */
public interface ConfigService {

    void save(Properties properties);

    void save(List<Properties> proList);

    void delete(String id);

    Properties findById(String id);

    List<Properties> findAll();

    @Deprecated
    List<Properties> findByGroup();

    List<KeyValue> findByPid(String pid);

    void saveKv(KeyValue kv);

    void saveKv(List<KeyValue> kvList);

    void deleteKv(String id);

}
