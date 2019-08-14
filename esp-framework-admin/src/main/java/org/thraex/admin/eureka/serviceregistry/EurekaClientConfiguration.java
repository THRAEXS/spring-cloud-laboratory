package org.thraex.admin.eureka.serviceregistry;

import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author 鬼王
 * @Date 2019/08/14 19:14
 */
public class EurekaClientConfiguration extends EurekaClientAutoConfiguration {

    public EurekaClientConfiguration(ConfigurableEnvironment env) {
        super(env);
    }

}
