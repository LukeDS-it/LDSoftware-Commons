package it.ldsoftware.primavera.vaadin.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luca on 21/04/16.
 * This implements the basic configuration for a Vaadin-based application
 */
@Configuration
@ComponentScan(basePackages = {"it.ldsoftware.primavera.vaadin.controllers",
        "it.ldsoftware.primavera.vaadin.security", "it.ldsoftware.primavera.vaadin.grouping"})
public class CommonsVaadinConfig {
    public static final String ADDRESS_DB_CONSOLE = "/db_console";

    @Autowired
    ApplicationContext ctx;

    @Bean
    ServletRegistrationBean h2WebConsole() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet(), ADDRESS_DB_CONSOLE + "/*");
        bean.addInitParameter("webAllowOthers", "true");
        return bean;
    }
}
