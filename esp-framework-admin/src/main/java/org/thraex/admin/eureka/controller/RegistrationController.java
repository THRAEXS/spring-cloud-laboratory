package org.thraex.admin.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thraex.admin.eureka.serviceregistry.ManualInstanceRegistration;

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
    private ManualInstanceRegistration registration;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @GetMapping("registration/v1")
    public Map<String, Object> v1() {
        return this.getResult(registration.v1());
    }

    @GetMapping({ "registration/v2", "registration/v2/{hp}" })
    public Map<String, Object> v2(@PathVariable(required = false) String hp) {
        return this.getResult(registration.v2(hp));
    }

    @GetMapping({ "registration/v3", "registration/v3/{hp}" })
    public Map<String, Object> v3(@PathVariable(required = false) String hp) {
        return this.getResult(registration.v3(hp));
    }

    private Map<String, Object> getResult(InstanceInfo target) {
        Map<String, Object> result = new HashMap<>(2);

        Application application = discoveryClient.getApplications().getRegisteredApplications(appName);
        result.put("current", Optional.ofNullable(application).orElse(new Application()).getByInstanceId(instanceId));
        result.put("target", target);

        return result;
    }

}
