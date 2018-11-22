package com.cnpc.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author 鬼王
 * @Date 2018/11/22 11:09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${user.name}")
    private String name;

    @GetMapping("/get")
    public String get() {
        return name;
    }

    @GetMapping("/list")
    public List<String> list() {
        return Arrays.asList("Guiwang", "HANZO", name);
    }

}
