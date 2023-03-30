package io.takima.master3.store;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SpringDocConfig {
    @Bean
    OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .title("Takima Store")
                        .description("This is a sample API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Meini")
                                .email("mxue@takima.fr"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
