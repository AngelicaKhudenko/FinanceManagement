package by.it_academy.jd2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebMvc
@EnableFeignClients(basePackages = {"by.it_academy.jd2.audit_service.service.feign"})
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class AuditServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuditServiceApplication.class,args);
    }
}
