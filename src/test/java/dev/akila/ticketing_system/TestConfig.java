package dev.akila.ticketing_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public String disableCLIForTests() {
        return "disable";
    }
}
