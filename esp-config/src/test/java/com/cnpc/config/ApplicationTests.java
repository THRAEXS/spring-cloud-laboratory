package com.cnpc.config;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.service.ConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author 鬼王
 * @Date 2018/10/26 16:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ConfigService configService;

    @Test
    public void findAll() {
        List<Properties> list = configService.findAll();
        Map<String, List<Properties>> collect1 = list.stream().collect(Collectors.groupingBy(Properties::getApplication));
        Map<String, List<String>> collect = list.stream()
                .collect(Collectors.groupingBy(Properties::getApplication,
                        Collectors.mapping(Properties::getProfile,
                                Collectors.toList())));
        System.out.println(collect);
        Assert.assertEquals(0, list.size());
    }


    public static void main(String[] args) {
        /*List<Properties> list = new ArrayList<>();
        list.add(new Properties("esp-gateway", "dev", "master", "foo", "Val-"));
        list.add(new Properties("esp-gateway", "dev", "feat", "info", "Val-"));
        list.add(new Properties("esp-gateway", "dev", "feat", "version", "Val-"));
        list.add(new Properties("esp-gateway", "test", "master", "foo", "Val-"));
        list.add(new Properties("esp-gateway", "prod", "master", "foo", "Val-"));
        list.add(new Properties("esp-admin", "dev", "master", "foo-admin", "Val-"));
        list.add(new Properties("esp-admin", "dev", "master", "app.name", "Val-"));
        list.add(new Properties("esp-admin", "test", "master", "foo-admin", "Val-"));
        list.add(new Properties("esp-admin", "prod", "master", "foo-admin", "Val-"));

        for (int i = 0; i < list.size(); i++) {
            Properties p = list.get(i);
            p.setId("id-" + i);
            p.setValue(p.getKey() + ": " + i);
        }*/

        List<Properties> list = Stream.of(
                new Properties("esp-gateway", "dev", "master", "foo", "Val-"),
                new Properties("esp-gateway", "dev", "feat", "info", "Val-"),
                new Properties("esp-gateway", "dev", "feat", "version", "Val-"),
                new Properties("esp-gateway", "test", "master", "foo", "Val-"),
                new Properties("esp-gateway", "prod", "master", "foo", "Val-"),
                new Properties("esp-admin", "dev", "master", "foo-admin", "Val-"),
                new Properties("esp-admin", "dev", "master", "app.name", "Val-"),
                new Properties("esp-admin", "dev", "master", "version", "Val-"),
                new Properties("esp-admin", "test", "master", "foo-admin", "Val-"),
                new Properties("esp-admin", "prod", "master", "foo-admin", "Val-")
        ).collect(Collectors.toList());

        list.forEach(it -> {
            it.setId(UUID.randomUUID().toString());
            it.setValue(it.getValue() + ": " + it.getKey());
        });

        Map<String, Map<String, Map<String, List<Properties>>>> collect = list.stream().collect(
                Collectors.groupingBy(Properties::getApplication,
                        Collectors.groupingBy(Properties::getProfile,
                                Collectors.groupingBy(Properties::getLabel))));

        Map<String, List<Properties>> collect4 = list.stream().collect(
                Collectors.groupingBy(it -> it.getApplication() + "," + it.getProfile() + "," + it.getLabel()));

        List<Properties> result = new ArrayList<>();
        collect4.forEach((k, v) -> {
            String[] split = k.split(",");
            Properties p = new Properties();
            p.setApplication(split[0]);
            p.setProfile(split[1]);
            p.setLabel(split[2]);
            p.setItems(v);
            result.add(p);
        });

        List<Properties> collect5 = result.stream().sorted(
                Comparator.comparing(Properties::getApplication)
                        .thenComparing(Properties::getProfile).reversed()
                        .thenComparing(Properties::getLabel)
                ).collect(Collectors.toList());

        System.out.println(11);
        //List<String> collect = list.stream().map(it -> it.getApplication()).collect(Collectors.toList());
        Map<String, List<Properties>> collect1 = list.stream().collect(Collectors.groupingBy(Properties::getApplication));
        Map<String, Map<String, Map<String, List<Properties>>>> tmp1 = new HashMap<>();
        collect1.forEach((k, v) -> {
            Map<String, List<Properties>> collect2 = v.stream().collect(Collectors.groupingBy(Properties::getProfile));
            Map<String, Map<String, List<Properties>>> tmp2 = new HashMap<>();

            collect2.forEach((k1, v1) -> {
                Map<String, List<Properties>> collect3 = v1.stream().collect(Collectors.groupingBy(Properties::getLabel));
                tmp2.put(k1, collect3);
            });
            tmp1.put(k, tmp2);
        });
        System.out.println(tmp1);
    }
}
