package org.example.case4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Case4Application {

    public static void main(String[] args) {
        SpringApplication.run(Case4Application.class, args);
    }

}
