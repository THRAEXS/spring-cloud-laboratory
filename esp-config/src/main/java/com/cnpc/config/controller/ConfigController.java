package com.cnpc.config.controller;

import com.cnpc.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 鬼王
 * @Date 2018/11/23 15:37
 */
@Controller
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping
    public String home(Model model) {
        Map<String, String> obj = new HashMap<>();
        obj.put("message", "Hello");
        obj.put("content", "Thymeleaf");
        model.addAttribute("user", "HANZO");
        model.addAllAttributes(obj);
        model.addAttribute(configService.findByGroup());
        return "home";
    }

}
