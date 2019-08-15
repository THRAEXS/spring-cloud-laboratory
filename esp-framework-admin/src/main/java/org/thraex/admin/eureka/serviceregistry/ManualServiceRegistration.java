package org.thraex.admin.eureka.serviceregistry;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadataProvider;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author 鬼王
 * @Date 2019/08/14 18:48
 */
@Component
@Log4j2
@ConfigurationProperties("eureka.client")
public class ManualServiceRegistration implements ApplicationRunner {

    @Setter
    private Map<String, List<String>> otherServiceUrl;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private InetUtils inetUtils;

    @Autowired
    private ManagementMetadataProvider managementMetadataProvider;

    @Autowired
    private EurekaRegistration registration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Services are registered to other eureka servers");

        //EurekaClientConfiguration eurekaClientConfiguration = new EurekaClientConfiguration(this.env);
        //EurekaInstanceConfigBean eurekaInstanceConfig = eurekaClientConfiguration.eurekaInstanceConfigBean(this.inetUtils, this.managementMetadataProvider);
        //eurekaInstanceConfig.setEnvironment(this.env);
        //
        //EurekaClientConfigBean eurekaClientConfig = eurekaClientConfiguration.eurekaClientConfigBean(this.env);
        //EurekaAutoServiceRegistration eurekaAutoServiceRegistration = new EurekaAutoServiceRegistration(this.context, new EurekaServiceRegistry(), this.registration);

        System.out.println(1);
    }

    @EventListener(WebServerInitializedEvent.class)
    public void onApplicationEvent(WebServerInitializedEvent event) {
        System.out.println(11);
    }

    @EventListener(ContextClosedEvent.class)
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(22);
    }

}
