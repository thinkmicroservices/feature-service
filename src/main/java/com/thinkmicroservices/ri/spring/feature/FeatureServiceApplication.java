package com.thinkmicroservices.ri.spring.feature;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FeatureServiceApplication {

    @Value("${configuration.source:DEFAULT}")
    String configSource;

    @Value("${spring.application.name:NOT-SET}")
    private String serviceName;

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(FeatureServiceApplication.class, args);
    }

    /**
     * display the service name and configuration source after the application 
     * this class has been constructed.
     * 
     */
    @PostConstruct
    private void displayInfo() {
        log.info("Service-Name:{}, configuration.source={}", serviceName, configSource);

    }
}
