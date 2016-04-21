package it.ldsoftware.commons.vaadin.configuration;

import it.ldsoftware.commons.i18n.WildcardMessageSource;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by luca on 21/04/16.
 */
@Configuration
@ComponentScan(basePackages = {"it.ldsoftware.commons.vaadin.controllers"})
public class CommonsVaadinConfig {
    public static final String ADDRESS_DB_CONSOLE = "/db_console";

    @Autowired
    ApplicationContext ctx;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new WildcardMessageSource();

        messageSource.setBasenames("classpath*:it/ldsoftware/*/i18n/errors*.properties",
                "classpath*:it/ldsoftware/*/i18n/messages*.properties",
                "classpath*:it/ldsoftware/*/i18n/nations*.properties");
        messageSource.setCacheSeconds(3600);
        messageSource.setFallbackToSystemLocale(false);

        return messageSource;
    }

//    @Bean
//    public GroupFinder finder() {
//        return new GroupFinder(ctx);
//    }

    @Bean
    ServletRegistrationBean h2WebConsole() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet(), ADDRESS_DB_CONSOLE + "/*");
        bean.addInitParameter("webAllowOthers", "true");
        return bean;
    }
}
