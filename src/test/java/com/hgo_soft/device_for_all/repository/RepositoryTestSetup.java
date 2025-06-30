package com.hgo_soft.device_for_all.repository;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql(scripts = "/data/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class RepositoryTestSetup {

}