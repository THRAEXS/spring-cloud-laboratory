package com.cnpc.config.repository;

import com.cnpc.config.entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 鬼王
 * @Date 2018/11/26 13:47
 */
public interface ConfigRepository extends JpaRepository<Properties, String> {
}
