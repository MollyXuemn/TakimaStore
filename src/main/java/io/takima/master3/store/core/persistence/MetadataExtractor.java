package io.takima.master3.store.core.persistence;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Holds the metamodel for future use.
 * https://vladmihalcea.com/how-to-get-access-to-database-table-metadata-with-hibernate-5/
 */
@Configuration
public class MetadataExtractor {
    public static final MetadataExtractor INSTANCE =
            new MetadataExtractor();

    final Integrator integrator = new Integrator() {
        @Override
        public void integrate(
                Metadata metadata,
                SessionFactoryImplementor sessionFactory,
                SessionFactoryServiceRegistry serviceRegistry) {

            INSTANCE.metadata = metadata;
        }

        @Override
        public void disintegrate(
                SessionFactoryImplementor sessionFactory,
                SessionFactoryServiceRegistry serviceRegistry) {
        }
    };

    private Metadata metadata;

    public Metadata getMetadata() {
        return metadata;
    }

    @Bean
    MetadataExtractor getMetadataExtractorIntegrator() {
        return MetadataExtractor.INSTANCE;
    }
}

/**
 * Hooks into hibernate bootstrap to intercept generated metamodels.
 * https://stackoverflow.com/questions/51501405/how-to-get-the-all-table-metadata-in-spring-boot-jpa-hibernate
 */
@Component
class HibernateConfig implements HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.integrator_provider",
                (IntegratorProvider) () -> Collections.singletonList(MetadataExtractor.INSTANCE.integrator));
    }
}

