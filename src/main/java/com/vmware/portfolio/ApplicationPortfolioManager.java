package com.vmware.portfolio;

import com.vmware.portfolio.config.SpringApplicationContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@Slf4j
public class ApplicationPortfolioManager {


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ApplicationPortfolioManager.class);
        //app.addInitializers(new SpringApplicationContextInitializer());
        //SpringApplicationBuilder app = new SpringApplicationBuilder(ApplicationPortfolioManager.class).initializers(new SpringApplicationContextInitializer());
        //SpringApplication application = app.application();
        //application.run(args);
        app.run(args);
    }

}
