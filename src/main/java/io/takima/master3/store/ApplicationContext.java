package io.takima.master3.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ComponentScan
public class ApplicationContext {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
