package com.barracuda.jcampaign;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        var container = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));
        container.setPortBindings(List.of("5432:5432"));
        container.withPassword("password").withUsername("postgres").withDatabaseName("test");
        return container;
    }

}
