package it.ldsoftware.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the project to scan the basic REST services
 */
@Configuration
@ComponentScan(basePackages = {"it.ldsoftware.rest.services", "it.ldsoftware.rest.mapper"})
public class RestConfiguration {
}
