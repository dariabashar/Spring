package com.example.practice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.practice1.config.AppIdProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppIdProperties.class)
public class Practice1Application {

    public static void main(String[] args) {
        SpringApplication.run(Practice1Application.class, args);
    }
}
