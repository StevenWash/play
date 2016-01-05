package com.test.start;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration    
@ComponentScan("com.test")
@EnableAutoConfiguration    
public class Start {  
    public static void main(String[] args) {  
        SpringApplication app = new SpringApplication(Start.class);  
        app.run(args);  
    }    
}