package org.thraex.admin.controller;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.resolver.DefaultEndpoint;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.EurekaHttpResponse;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 鬼王
 * @Date 2018/11/22 11:09
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private EurekaClient discoveryClient;

    //@Autowired
    //private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private Integer port;

    @Value("${app.user.name}")
    private String username;

    @GetMapping("get")
    public String get() {
        return name + "[" + port + "]: " + username;
    }

    @GetMapping("list")
    public List<String> list() {
        return Arrays.asList("Guiwang", "HANZO", username);
    }


    @GetMapping("service")
    public Map serviceUrl() {
        Map<String, Object> result = new HashMap<>(2);
        //discoveryClient.getApplicationInfoManager().initComponent();
        //EurekaInstanceConfigBean ic = new EurekaInstanceConfigBean();
        //InstanceInfo instance = discoveryClient.getNextServerFromEureka("STORES", false);
        Applications applications = discoveryClient.getApplications();
        List<Application> registeredApplications = applications.getRegisteredApplications();
        List<InstanceInfo> instances = registeredApplications.get(0).getInstances();
        result.put("applications", instances.get(0));
        //result.put("applications", applications);

        final String serviceUrl = "http://10.10.1.255:8762/eureka/";
        TransportClientFactory factory = new RestTemplateTransportClientFactory();
        EurekaEndpoint defaultEndpoint = new DefaultEndpoint(serviceUrl);
        EurekaHttpClient eurekaHttpClient = factory.newClient(defaultEndpoint);
        //EurekaHttpClient ehc = new RestTemplateEurekaHttpClient(new RestTemplate(), serviceUrl);
        InstanceInfo info = InstanceInfo.Builder.newBuilder()
                //.setInstanceId("Guiwang")
                .setInstanceId("esp-framework-admin-hanzo:10.10.1.255:8083")
                .setAppName("esp-framework-admin-hanzo")
                .setIPAddr("10.10.1.255")
                //.setHomePageUrl("http://10.10.1.255:8083/")
                .setHomePageUrlForDeser("http://10.10.1.255:8083/")
                .setStatusPageUrlForDeser("http://10.10.1.255:8083/actuator/info")
                .setHealthCheckUrlsForDeser("http://10.10.1.255:8083/actuator/health", null)
                .setVIPAddress("esp-framework-admin")
                .setSecureVIPAddress("esp-framework-admin")
                .setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn))
                .setHostName("10.10.1.255")
                .setLeaseInfo(LeaseInfo.Builder.newBuilder()
                        //.setRegistrationTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of(""+8)).toEpochMilli())
                        .setRegistrationTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .setRenewalTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .setServiceUpTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build())
                .setMetadata(new HashMap(2) { {
                    put("management.port", "8083");
                    put("jmx.port", "59958");
                } })
                .setActionType(InstanceInfo.ActionType.ADDED)
                //.setPort(8083)
                .build();
        result.put("instanceInfo", info);
        //ehc.register(info);
        eurekaHttpClient.register(info);

        return result;
    }
}
