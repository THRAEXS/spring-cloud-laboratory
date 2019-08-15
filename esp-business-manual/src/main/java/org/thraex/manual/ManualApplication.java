package org.thraex.manual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.thraex.spring.cloud.service.ThraexService;

/**
 * @Author 鬼王
 * @Date 2019/08/15 16:45
 */
@SpringBootApplication
@RestController
public class ManualApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManualApplication.class, args);
    }

    @Autowired
    private ThraexService service;

    @GetMapping("test/{name}")
    public String test(@PathVariable String name) {
        return service.test(name);
    }

}
