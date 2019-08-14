package org.thraex.admin.eureka.serviceregistry;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.resolver.DefaultEndpoint;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory;
import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactory;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Optional;

/**
 * @Author 鬼王
 * @Date 2019/08/13 18:08
 */
@Component
@Log4j2
@ConfigurationProperties("eureka.instance")
public class ManualInstanceRegistration {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Value("http://${spring.cloud.client.ip-address}:8762/eureka/")
    private String defaultServiceUrl;

    @Value("${server.port}")
    private String port;

    @Setter
    private String instanceId;

    @Autowired
    private EurekaInstanceConfig eurekaInstanceConfig;

    @Autowired
    private EurekaClientConfig eurekaClientConfig;

    //@Autowired
    //private EurekaClient eurekaClient;

    @Autowired
    private ApplicationContext context;
    //AnnotationConfigServletWebServerApplicationContext

    @Autowired
    private AbstractDiscoveryClientOptionalArgs<?> optionalArgs;
    //MutableDiscoveryClientOptionalArgs

    @Autowired
    private EurekaRegistration eurekaRegistration;

    @Autowired
    private ObjectProvider<HealthCheckHandler> healthCheckHandler;

    public InstanceInfo v1() {
        log.info("Service url: [{}]", this.defaultServiceUrl);

        TransportClientFactory factory = new RestTemplateTransportClientFactory();
        EurekaEndpoint defaultEndpoint = new DefaultEndpoint(this.defaultServiceUrl);
        EurekaHttpClient eurekaHttpClient = factory.newClient(defaultEndpoint);

        final String appName = "esp-framework-admin-v1";
        final String url = "http://" + ip + ":" + port;
        InstanceInfo info = InstanceInfo.Builder.newBuilder()
                .setInstanceId(appName + ":" + ip + ":" + port)
                .setAppName(appName)
                .setIPAddr(ip)
                .setHomePageUrlForDeser(url)
                .setStatusPageUrlForDeser(url + "/actuator/info")
                .setHealthCheckUrlsForDeser(url + "/actuator/health", null)
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

    public InstanceInfo v2(String hp) {
        String serviceUrl = Optional.ofNullable(hp).map(it -> "http://" + it + "/eureka/").orElse(this.defaultServiceUrl);
        log.info("Service url: [{}]", serviceUrl);

        TransportClientFactory factory = new RestTemplateTransportClientFactory();
        EurekaEndpoint defaultEndpoint = new DefaultEndpoint(serviceUrl);
        EurekaHttpClient eurekaHttpClient = factory.newClient(defaultEndpoint);

        //EurekaInstanceConfigBean eurekaInstanceConfigBean = (EurekaInstanceConfigBean) this.eurekaInstanceConfig;
        // TODO: update InstanceId/appName/VIPAddress/SecureVIPAddress
        InstanceInfo info = new InstanceInfoFactory().create(this.eurekaInstanceConfig);
        info.setStatus(InstanceInfo.InstanceStatus.UP);
        info.setLeaseInfo(LeaseInfo.Builder.newBuilder()
                .setRegistrationTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setRenewalTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setServiceUpTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build());
        info.setActionType(InstanceInfo.ActionType.ADDED);

        eurekaHttpClient.register(info);

        return info;
    }

    public InstanceInfo v3(String hp) {
        String serviceUrl = Optional.ofNullable(hp).map(it -> "http://" + it + "/eureka/").orElse(this.defaultServiceUrl);
        log.info("Service url: [{}]", serviceUrl);

        /**
         * 1. Create EurekaInstanceConfig
         *
         * 2. Create InstanceInfo: Use EurekaInstanceConfig
         *
         * 3. Create ApplicationInfoManager: EurekaInstanceConfig + InstanceInfo
         *
         * 4. Create EurekaClientConfig
         *
         * 5. Create EurekaClient: ApplicationInfoManager + EurekaClientConfig
         */

        /**
         * 1 --> 2 --> 3
         *
         * ${@link org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean}
         */
        EurekaInstanceConfig eurekaInstanceConfig = this.eurekaInstanceConfig;
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(
                eurekaInstanceConfig, new ApplicationInfoManager.OptionalArgs());

        /**
         * 4
         *
         * ${@link org.springframework.cloud.netflix.eureka.EurekaClientConfigBean}
         */
        EurekaClientConfigBean eurekaClientConfig = (EurekaClientConfigBean) this.eurekaClientConfig;
        //Map<String, String> urls = new HashMap<>(1);
        //urls.put(EurekaClientConfigBean.DEFAULT_ZONE, serviceUrl);
        //eurekaClientConfig.setServiceUrl(urls);

        /**
         * 5
         * ${@link org.springframework.cloud.netflix.eureka.CloudEurekaClient}
         */
        //EurekaClient eurekaClient = this.eurekaClient;
        //EurekaClient eurekaClient = new CloudEurekaClient(applicationInfoManager, eurekaClientConfig, this.optionalArgs, this.context);

        return null;
    }

}
