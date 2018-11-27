package com.cnpc.config.controller;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.repository.PropertiesRepository;
import com.cnpc.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private PropertiesRepository propertiesRepository;

    @GetMapping
    public String home(Model model) {
        model.addAttribute(configService.findAll());
        model.addAttribute("prefix", PREFIX);
        return "home";
    }

    @PostMapping("save")
    public String save(Properties properties) {
        configService.save(properties);
        return "redirect:/config";
    }

    @GetMapping("delete/{id}")
    public String update(@PathVariable String id) {
        propertiesRepository.deleteById(id);
        return "redirect:/config";
    }

    @GetMapping("detail/{pid}")
    public String detail(@PathVariable String pid, Model model) {
        model.addAttribute(configService.findByPid(pid));
        return "detail";
    }

}
