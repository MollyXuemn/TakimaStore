package io.takima.master3.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class ApplicationContext {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
