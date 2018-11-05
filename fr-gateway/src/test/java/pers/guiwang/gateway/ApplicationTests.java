package pers.guiwang.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 鬼王
 * @Date 2018/11/02 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Value("${app.name}")
    private String appName;

    @Value("${rabbitmq.hostname}")
    private String rabbitmq;

    @Test
    public void test() {
        System.out.println("appName is " + appName);
        System.out.println("rabbitmq is " + rabbitmq);
    }

}
