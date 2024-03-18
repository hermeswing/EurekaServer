package octopus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <pre>
 * 참고URL : <a href="https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html">...</a>
 * </pre>
 */
@SpringBootApplication
@EnableEurekaServer               // 스프링 서비스에서 유레카 서버 활성화
public class EurekaServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
