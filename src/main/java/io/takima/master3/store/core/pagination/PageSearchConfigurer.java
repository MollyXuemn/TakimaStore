package io.takima.master3.store.core.pagination;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configure Spring HateoasPageableHandlerMethodArgumentResolver to work with the {@link PageSearch} object..
 */
@Configuration
public class PageSearchConfigurer extends HateoasAwareSpringDataWebConfiguration implements WebMvcConfigurer {

    private PageSearchHandlerMethodArgumentResolver pageSearchHandlerMethodArgumentResolver;

    /**
     * @param context           must not be {@literal null}.
     * @param conversionService must not be {@literal null}.
     */
    public PageSearchConfigurer(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }

    @PostConstruct
    void init() {
        this.pageSearchHandlerMethodArgumentResolver = new PageSearchHandlerMethodArgumentResolver(sortResolver());
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageSearchHandlerMethodArgumentResolver);
    }

    @Bean
    @Override
    public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
        customizePageableResolver(pageSearchHandlerMethodArgumentResolver);
        return pageSearchHandlerMethodArgumentResolver;
    }
}

