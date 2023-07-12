package io.takima.master3.store;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    OpenAPI api() {
        return new OpenAPI().info(new Info()
                .contact(new Contact())
                .license(new License().name("Takima"))
                .title("")
                .description("My first app")
                .version("Final :D"));
    }
}

