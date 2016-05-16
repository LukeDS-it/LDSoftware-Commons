package it.ldsoftware.tests.configuration;

import it.ldsoftware.primavera.vaadin.configuration.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by luca on 16/05/16.
 *
 */
@Configuration
public class TestSecurityConfiguration extends BaseSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/secured").authenticated();
    }
}
