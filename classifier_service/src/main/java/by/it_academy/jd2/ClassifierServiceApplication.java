package by.it_academy.jd2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableJpaRepositories
@EnableWebMvc
@EnableFeignClients(basePackages = {"by.it_academy.jd2.classifier_service.service.feign"})
@EnableAspectJAutoProxy
public class ClassifierServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClassifierServiceApplication.class,args);
    }
}
