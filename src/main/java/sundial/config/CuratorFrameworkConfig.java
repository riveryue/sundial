package sundial.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author yao
 */
@Configuration
@Slf4j
public class CuratorFrameworkConfig {

    @Autowired
    private Environment env;

    @Bean
    public CuratorFramework curatorFramework() {
        final CuratorFramework client = CuratorFrameworkFactory.newClient(env.getProperty("zkIp"), new RetryNTimes(10, 5000));
        client.start();
        log.info("zk status {}", client.getState());
        return client;
    }

}
