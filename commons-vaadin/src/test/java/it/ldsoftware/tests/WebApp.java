package it.ldsoftware.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by luca on 04/05/16.
 * Simple web application
 */
@SpringBootApplication
@EnableAutoConfiguration
public class WebApp {

    public static void main(String... args) {
        SpringApplication.run(WebApp.class, args);
    }

}