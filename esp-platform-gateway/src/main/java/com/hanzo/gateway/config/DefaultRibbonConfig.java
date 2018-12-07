package com.hanzo.gateway.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 鬼王
 * @Date 2018/12/06 10:59
 */
//@Configuration
//@RibbonClient(name = "bus-admin", configuration = DefaultRibbonConfig.class)
public class DefaultRibbonConfig {

    @Bean
    public IRule ribbonRule() {
        //return new RoundRobinRule();
        //return new RandomRule();
        return new RetryRule();
        //return new SpecifyRule();
    }

}
