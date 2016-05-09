package it.ldsoftware.primavera.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by luca on 21/04/16.
 * Basic test application with spring boot, used to load the context
 * in the tests
 */
@SpringBootApplication
@EnableAutoConfiguration
public class TestApplication {

    public static void main(String... args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
