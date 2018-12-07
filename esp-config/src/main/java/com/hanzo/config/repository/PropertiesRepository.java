package com.hanzo.config.repository;

import com.hanzo.config.entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 鬼王
 * @Date 2018/11/26 13:47
 */
public interface PropertiesRepository extends JpaRepository<Properties, String> {
}
