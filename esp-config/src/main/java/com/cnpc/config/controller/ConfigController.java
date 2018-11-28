package com.cnpc.config.controller;

import com.cnpc.config.entity.Properties;
import com.cnpc.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        model.addAttribute(configService.findAll());
        model.addAttribute("prefix", PREFIX);
        return "home";
    }

    @PostMapping("save")
    @ResponseBody
    public ResponseEntity<Properties> save(Properties properties) {
        configService.save(properties);
        return new ResponseEntity(properties, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public String update(@PathVariable String id) {
        configService.deleteProperties(id);
        return "redirect:/config";
    }

    @GetMapping("detail/{application}/{profile}/{label}/{pid}")
    public String detail(@PathVariable String pid,
                         @PathVariable String application,
                         @PathVariable String profile,
                         @PathVariable String label,
                         Model model) {
        model.addAttribute(new Properties(pid, application, profile, label));
        model.addAttribute(configService.findByPid(pid));
        return "detail";
    }

}
