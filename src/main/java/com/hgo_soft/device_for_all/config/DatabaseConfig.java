package com.hgo_soft.device_for_all.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.hgo_soft.device_for_all.repository")
public class DatabaseConfig {
}