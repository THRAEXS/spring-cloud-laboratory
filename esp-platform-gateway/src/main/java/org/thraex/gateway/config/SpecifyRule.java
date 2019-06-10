package org.thraex.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

/**
 * @Author 鬼王
 * @Date 2018/12/06 11:29
 */
@Log4j2
public class SpecifyRule extends AbstractLoadBalancerRule {

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        while (server == null) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            for (Server svr : reachableServers) {
                //log.info(svr);
                Server.MetaInfo metaInfo = svr.getMetaInfo();
                //DomainExtractingServer deSvr = (DomainExtractingServer)
                DiscoveryEnabledServer deSvr = (DiscoveryEnabledServer) svr;
                Map<String, String> metadata = deSvr.getInstanceInfo().getMetadata();
                //System.out.println(metadata);
                metadata.forEach((s, s2) -> System.out.println(svr.getId()+"["+s+": "+s2+"]"));
            }

            server = reachableServers.get(0);
        }

        return server;
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

}
