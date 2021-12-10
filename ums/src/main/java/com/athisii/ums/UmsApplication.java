package com.athisii.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.athisii"})
public class UmsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UmsApplication.class, args);
    }

}
