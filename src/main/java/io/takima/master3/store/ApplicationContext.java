package io.takima.master3.store;
import io.takima.master3.store.presentation.CliServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

@Configuration
public class ApplicationContext {


    @Bean
    ServletRegistrationBean<CliServlet> myServletRegistration () {
        ServletRegistrationBean<CliServlet> srb = new ServletRegistrationBean<>();
        srb.setServlet(new CliServlet());
        srb.setUrlMappings(Collections.singletonList("/cli"));

        return srb;
    }
}
