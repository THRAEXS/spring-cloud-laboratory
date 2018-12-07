package com.hanzo.config.repository;

import com.hanzo.config.entity.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 鬼王
 * @Date 2018/11/27 14:32
 */
public interface KeyValueRepository extends JpaRepository<KeyValue, String> {

    List<KeyValue> findByPidOrderByPkeyAscPvalueAsc(String pid);

    void deleteByPid(String pid);

}
