package com.hgo_soft.device_for_all.common.audit;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@TestConfiguration
public class JpaAuditingTestConfig {

    @Bean(name = "securityAuditorAware")
    public AuditorAware<Long> securityAuditorAware() {
        return Optional::empty;
    }
}
