package com.cnpc.config.controller;

import com.cnpc.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 鬼王
 * @Date 2018/11/23 15:37
 */
@Controller
@RequestMapping("config")
public class ConfigController {

    @Value("${spring.cloud.config.server.prefix:}")
    private String PREFIX;

    @Autowired
    private ConfigService configService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute(configService.findByGroup());
        model.addAttribute("prefix", PREFIX);
        return "home";
    }

}
