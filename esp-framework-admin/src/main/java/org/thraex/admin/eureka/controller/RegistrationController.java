package org.thraex.admin.eureka.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thraex.admin.eureka.ManualInstanceRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author 鬼王
 * @Date 2019/08/13 18:22
 */
@RestController
@RequestMapping("eureka")
public class RegistrationController {

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private ManualInstanceRegistration manualInstanceRegistration;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @GetMapping("registration/v1")
    public Map<String, Object> v1() {
        Map<String, Object> result = new HashMap<>(2);

        Application application = discoveryClient.getApplications().getRegisteredApplications(appName);
        result.put("current", Optional.of(application).orElse(new Application()).getByInstanceId(instanceId));
        result.put("target", manualInstanceRegistration.v1());

        return result;
    }

}
