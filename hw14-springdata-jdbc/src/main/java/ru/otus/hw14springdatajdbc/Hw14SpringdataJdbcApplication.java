package ru.otus.hw14springdatajdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw14SpringdataJdbcApplication {
    private static final Logger logger = LoggerFactory.getLogger(Hw14SpringdataJdbcApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Hw14SpringdataJdbcApplication.class, args);
        logger.info("Main page: http://localhost:8080");
    }
}
