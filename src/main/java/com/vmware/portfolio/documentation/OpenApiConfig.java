package com.vmware.portfolio.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("https://localhost:443");

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Application Portfolio Manager API")
                .description(
                        "Application Portfolio Manager ")
                .version("1.0.0")
        );
        openAPI.setServers(Arrays.asList(localServer));

        return openAPI;
    }

}
