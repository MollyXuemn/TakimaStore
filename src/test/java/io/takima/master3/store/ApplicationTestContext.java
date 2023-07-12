package io.takima.master3.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
class ApplicationTestContext{
    @Bean
    @Primary
    Clock fixedClock() { // LocalDateTime.now(clock) will always return 2050-01-01
        return Clock.fixed(Instant.parse("2010-12-15T12:00:00.00Z"), ZoneId.of("UTC"));
    }
}
