package it.ldsoftware.commons.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by luca on 02/05/16.
 * This configuration enables jpa repositories and scans for base entities
 */
@Configuration
@EntityScan(basePackages = "it.ldsoftware.entities")
@EnableJpaRepositories(basePackages = "it.ldsoftware.commons.dal")
public class EntitiesConfiguration {

}
