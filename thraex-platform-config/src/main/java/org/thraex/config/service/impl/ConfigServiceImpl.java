package org.thraex.config.service.impl;

import org.thraex.config.entity.KeyValue;
import org.thraex.config.entity.Properties;
import org.thraex.config.repository.KeyValueRepository;
import org.thraex.config.repository.PropertiesRepository;
import org.thraex.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void save(List<Properties> proList) {
        propertiesRepository.saveAll(proList);
    }

    @Override
    @Transactional
    public void delete(String id) {
        propertiesRepository.deleteById(id);
        keyValueRepository.deleteByPid(id);
    }

    @Override
    public Properties findById(String id) {
        Optional<Properties> op = propertiesRepository.findById(id);
        return op.isPresent()?op.get():null;
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
        return keyValueRepository.findByPidOrderByPkeyAscPvalueAsc(pid);
    }

    @Override
    public void saveKv(KeyValue kv) {
        keyValueRepository.save(kv);
    }

    @Override
    public void saveKv(List<KeyValue> kvList) {
        keyValueRepository.saveAll(kvList);
    }

    @Override
    public void deleteKv(String id) {
        keyValueRepository.deleteById(id);
    }

}
