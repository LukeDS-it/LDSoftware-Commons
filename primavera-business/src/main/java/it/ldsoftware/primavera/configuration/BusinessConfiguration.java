package it.ldsoftware.primavera.configuration;

import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.services.AbstractBusinessService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by luca on 02/05/16.
 * This configuration enables jpa repositories and scans for base entities
 */
@Configuration
@EntityScan(basePackages = "it.ldsoftware.primavera.model")
@ComponentScan(basePackageClasses = {
        AbstractBusinessService.class,
        Mapper.class
})
@EnableJpaRepositories(basePackages = "it.ldsoftware.primavera.dal")
public class BusinessConfiguration {

}
