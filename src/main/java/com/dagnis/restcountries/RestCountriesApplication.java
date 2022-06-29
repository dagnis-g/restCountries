package com.dagnis.restcountries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.dagnis.restcountries.*")
@ComponentScan(basePackages = {"com.dagnis.restcountries.*"})
@EntityScan("com.dagnis.restcountries.model")
public class RestCountriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestCountriesApplication.class, args);
    }

}
