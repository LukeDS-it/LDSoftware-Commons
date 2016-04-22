package it.ldsoftware.commons.test;

import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.entities.security.GroupTranslation;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by luca on 21/04/16.
 * Basic test application with spring boot, used to load the context
 * in the tests
 */
@SpringBootApplication
@EntityScan(basePackages = {"it.ldsoftware.commons.entities"})
@EnableJpaRepositories(basePackages = {"it.ldsoftware.commons.dal"})
public class TestApplication {

    public static void main(String... args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
