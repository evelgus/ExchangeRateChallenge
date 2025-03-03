package com.exchange.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ExchangeRateChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateChallengeApplication.class, args);
    }

}
