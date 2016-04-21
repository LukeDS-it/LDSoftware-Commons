package it.ldsoftware.commons.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by luca on 21/04/16.
 * Basic test application with spring boot, used to load the context
 * in the tests
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = {"it.ldsoftware.commons.entities"})
public class TestApplication {

    public static void main(String... args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
