package com.cnpc.config;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.service.ConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    }
}
