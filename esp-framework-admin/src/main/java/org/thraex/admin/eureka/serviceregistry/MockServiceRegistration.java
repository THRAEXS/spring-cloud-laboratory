package org.thraex.admin.eureka.serviceregistry;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadataProvider;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @Author 鬼王
 * @Date 2019/08/14 18:48
 */
@Component
@Log4j2
public class MockServiceRegistration implements ApplicationRunner {

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private InetUtils inetUtils;

    @Autowired
    private ManagementMetadataProvider managementMetadataProvider;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Services are registered to other eureka servers");

        EurekaClientConfiguration eurekaClientConfiguration = new EurekaClientConfiguration(this.env);
        EurekaInstanceConfigBean eurekaInstanceConfig = eurekaClientConfiguration.eurekaInstanceConfigBean(inetUtils, managementMetadataProvider);
        EurekaClientConfigBean eurekaClientConfig = eurekaClientConfiguration.eurekaClientConfigBean(this.env);


        System.out.println(1);
    }

}
