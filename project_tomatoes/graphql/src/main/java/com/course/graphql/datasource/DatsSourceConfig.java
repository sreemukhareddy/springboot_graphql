package com.course.graphql.datasource;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatsSourceConfig {

    @Bean
    public Faker faker(){
        return new Faker();
    }

}
