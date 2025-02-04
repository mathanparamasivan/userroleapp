package com.i2i.userrole.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;

@Configuration
public class MySqlDatabaseConfig {

    @Value("${spring.datasource.url}")  // Fetch the DB URL from application.properties
    private String dbUrl;

    @Value("${spring.datasource.driver-class-name}")  // Fetch the DB driver from application.properties
    private String dbDriver;

    // WebClient Builder Bean
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // DataSource Bean
    @Bean
    @Primary
    public DataSource getDataSource() {
        // Default DataSource setup
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);

        // Fetch additional configuration from Vault asynchronously
        fetchVaultSecretsAsync(dataSource);

        return dataSource;
    }

    // Asynchronous method to fetch secrets from Vault
    private void fetchVaultSecretsAsync(DriverManagerDataSource dataSource) {
        WebClient.Builder webClientBuilder = webClientBuilder();

        Mono<JsonNode> response = webClientBuilder.baseUrl("http://localhost:8200")
                .build()
                .get()
                .uri("/v1/secret/data/mysql_vault")
                .header("X-Vault-Token", "hvs.RAJ1TAr7TqZy0jOciaxiD2CE")// The appropriate endpoint
                .retrieve()
                .bodyToMono(JsonNode.class);

        JsonNode jsonNode = response.block();
        String userName = jsonNode.get("data")
                .get("data")
                .get("username")
                .asText();
         String password = jsonNode.get("data")
                .get("data")
                .get("password")
                .asText();

        dataSource.setUsername(userName);
        dataSource.setPassword(password);

    }
}
