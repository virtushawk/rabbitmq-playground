package org.virtushawk.rabbitmqplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.virtushawk.rabbitmqplayground.dao")
@EnableJpaAuditing
public class RabbitmqPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqPlaygroundApplication.class, args);
    }

}
