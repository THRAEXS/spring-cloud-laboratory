package org.thraex.admin.eureka.serviceregistry;

import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author 鬼王
 * @Date 2019/08/14 19:14
 */
public class ManualEurekaClientAutoConfiguration extends EurekaClientAutoConfiguration {

    public ManualEurekaClientAutoConfiguration(ConfigurableEnvironment env) {
        super(env);
    }

}
