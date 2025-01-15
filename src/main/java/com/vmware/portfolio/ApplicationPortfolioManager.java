package com.vmware.portfolio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
public class ApplicationPortfolioManager {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationPortfolioManager.class);
        app.run(args);
    }

}
