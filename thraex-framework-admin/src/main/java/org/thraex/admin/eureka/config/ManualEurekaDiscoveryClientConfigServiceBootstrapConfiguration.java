package org.thraex.admin.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.thraex.admin.eureka.ManualEurekaClientAutoConfiguration;
import org.thraex.admin.eureka.ManualEurekaDiscoveryClientConfiguration;

/**
 * @Author 鬼王
 * @Date 2019/08/15 12:05
 */
//@Configuration
//@Import({ ManualEurekaDiscoveryClientConfiguration.class,
//        ManualEurekaClientAutoConfiguration.class })
public class ManualEurekaDiscoveryClientConfigServiceBootstrapConfiguration {

    public ManualEurekaDiscoveryClientConfigServiceBootstrapConfiguration() {
        System.out.println("ManualEurekaDiscoveryClientConfigServiceBootstrapConfiguration");
    }

}
