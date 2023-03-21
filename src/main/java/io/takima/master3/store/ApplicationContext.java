package io.takima.master3.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationContext {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }


}
