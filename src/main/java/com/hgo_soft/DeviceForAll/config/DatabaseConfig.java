package com.hgo_soft.DeviceForAll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.hgo_soft.DeviceForAll.repository")
public class DatabaseConfig {
}