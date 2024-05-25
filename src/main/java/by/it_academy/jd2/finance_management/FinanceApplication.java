package by.it_academy.jd2.finance_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebMvc
public class FinanceApplication {

    public static void main(String[] args) {

        SpringApplication.run(FinanceApplication.class,args);
    }
}
