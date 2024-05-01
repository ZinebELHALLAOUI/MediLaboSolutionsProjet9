package io.zineb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RiskDiabetesService {

    public static void main(String[] args) {
        SpringApplication.run(RiskDiabetesService.class, args);
    }

}