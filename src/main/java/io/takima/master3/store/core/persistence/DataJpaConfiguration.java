package io.takima.master3.store.core.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.takima.master3",
        repositoryBaseClass = CustomJpaRepository.class)
public class DataJpaConfiguration {
}
