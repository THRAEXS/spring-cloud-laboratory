package com.cnpc.order.controller;

import com.cnpc.order.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author 鬼王
 * @Date 2018/11/22 15:18
 */
@RestController
@RequestMapping("detail")
public class OrderController {

    @Autowired
    private UserService userService;

    @Value("${app.order.name}")
    private String name;

    @GetMapping("get")
    public String get() {
        return name;
    }

    @GetMapping("list")
    public List<String> list() {
        return Arrays.asList("Guiwang", "HANZO", name);
    }

    @GetMapping("get/user")
    public String getUser() {
        System.out.println("Through feign get user");
        return name + ": " + userService.get();
    }

    @GetMapping("list/user")
    public List<String> userList() {
        System.out.println("Through feign get user list");
        return Stream.concat(Arrays.asList("Guiwang", "HANZO", name).stream(),
                userService.list().stream()).collect(Collectors.toList());
    }

}
