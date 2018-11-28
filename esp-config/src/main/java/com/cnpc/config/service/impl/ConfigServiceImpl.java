package com.cnpc.config.service.impl;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.entity.KeyValue;
import com.cnpc.config.repository.PropertiesRepository;
import com.cnpc.config.repository.KeyValueRepository;
import com.cnpc.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 鬼王
 * @Date 2018/11/26 15:24
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    private KeyValueRepository keyValueRepository;

    @Override
    public void save(Properties properties) {
        propertiesRepository.save(properties);
    }

    @Override
    public void deleteProperties(String id) {
        propertiesRepository.deleteById(id);
    }

    @Override
    public List<Properties> findAll() {
        return propertiesRepository.findAll(Sort.by("application", "profile", "label"));
    }

    @Override
    public List<Properties> findByGroup() {
        Map<String, List<Properties>> collect = this.findAll().stream().collect(
                Collectors.groupingBy(it -> it.getApplication() + "/" + it.getProfile() + "/" + it.getLabel()));
        List<Properties> result = new ArrayList<>();
        collect.forEach((k, v) -> {
            String[] split = k.split("/");
            Properties p = new Properties();
            p.setId(k);
            p.setApplication(split[0]);
            p.setProfile(split[1]);
            p.setLabel(split[2]);
            //p.setItems(v);
            result.add(p);
        });

        return result.stream().sorted(Comparator.comparing(Properties::getApplication)
                .thenComparing(Properties::getProfile).thenComparing(Properties::getLabel))
                .collect(Collectors.toList());
    }

    @Override
    public List<KeyValue> findByPid(String pid) {
        return keyValueRepository.findByPid(pid);
    }

}
