package org.SprintForge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootprojectApplication.class, args);
    }
}
