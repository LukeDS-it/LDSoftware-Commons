package it.ldsoftware.primavera.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by luca on 02/05/16.
 * This configuration enables jpa repositories and scans for base entities
 */
@Configuration
@EntityScan(basePackages = "it.ldsoftware.primavera.entities")
@EnableJpaRepositories(basePackages = "it.ldsoftware.primavera.dal")
public class EntitiesConfiguration {

}
