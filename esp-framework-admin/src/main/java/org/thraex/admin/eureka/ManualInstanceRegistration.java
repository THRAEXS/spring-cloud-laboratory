package org.thraex.admin.eureka;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.discovery.shared.resolver.DefaultEndpoint;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

/**
 * @Author 鬼王
 * @Date 2019/08/13 18:08
 */
@Component
public class ManualInstanceRegistration {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Value("${server.port}")
    private String port;

    public InstanceInfo v1() {
        final String serviceUrl = "http://" + ip + ":8762/eureka/";

        TransportClientFactory factory = new RestTemplateTransportClientFactory();
        EurekaEndpoint defaultEndpoint = new DefaultEndpoint(serviceUrl);
        EurekaHttpClient eurekaHttpClient = factory.newClient(defaultEndpoint);

        final String appName = "esp-framework-admin-v1";
        InstanceInfo info = InstanceInfo.Builder.newBuilder()
                .setInstanceId(appName + ":" + ip + ":" + port)
                .setAppName(appName)
                .setIPAddr(ip)
                .setHomePageUrlForDeser("http://" + ip +":" + port)
                .setStatusPageUrlForDeser("http://" + ip + ":" + port + "/actuator/info")
                .setHealthCheckUrlsForDeser("http://" + ip + ":" + port + "/actuator/health", null)
                .setVIPAddress(appName)
                .setSecureVIPAddress(appName)
                .setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn))
                .setHostName(ip)
                .setLeaseInfo(LeaseInfo.Builder.newBuilder()
                        //.setRegistrationTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of(""+8)).toEpochMilli())
                        .setRegistrationTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .setRenewalTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .setServiceUpTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build())
                .setMetadata(new HashMap(2) { {
                    put("management.port", port);
                    put("jmx.port", "59958");
                } })
                .setActionType(InstanceInfo.ActionType.ADDED)
                //.setPort(8083)
                .build();

        eurekaHttpClient.register(info);

        return info;
    }

}
