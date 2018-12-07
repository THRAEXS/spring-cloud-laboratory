package com.hanzo.config.controller;

import com.hanzo.config.entity.KeyValue;
import com.hanzo.config.entity.Properties;
import com.hanzo.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @PostMapping("save/all")
    @ResponseBody
    public ResponseEntity saveAll(@RequestBody List<Properties> proList) {
        configService.save(proList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        configService.delete(id);
        return "redirect:/config";
    }

    @GetMapping("detail/{pid}")
    public String detail(@PathVariable String pid, Model model) {
        model.addAttribute(configService.findById(pid));
        model.addAttribute(configService.findByPid(pid));
        return "detail";
    }

    @PostMapping("detail/save")
    @ResponseBody
    public ResponseEntity<KeyValue> saveKv(KeyValue keyValue) {
        configService.saveKv(keyValue);
        return new ResponseEntity(keyValue, HttpStatus.OK);
    }

    @PostMapping("detail/save/all")
    @ResponseBody
    public ResponseEntity saveKvAll(@RequestBody List<KeyValue> kvList) {
        configService.saveKv(kvList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("detail/delete/{pid}/{id}")
    public String deleteKv(@PathVariable String pid, @PathVariable String id) {
        configService.deleteKv(id);
        return "redirect:/config/detail/"+pid;
    }

}
