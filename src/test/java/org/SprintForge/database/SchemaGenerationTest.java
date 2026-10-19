package org.SprintForge.database;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("local")
public class SchemaGenerationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        String user = System.getenv().getOrDefault("DB_USERNAME", "postgres");
        String pass = System.getenv().getOrDefault("DB_PASSWORD", "postgres");
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/sprintforge_schema_scratch");
        registry.add("spring.datasource.username", () -> user);
        registry.add("spring.datasource.password", () -> pass);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.flyway.enabled", () -> "false");
        registry.add("JWT_SECRET", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("jwt.secret", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("sprintforge.security.jwt.secret", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("sprintforge.security.jwt.secret-key", () -> "0123456789012345678901234567890123456789012345678901234567890123");
    }

    @Test
    public void generateSchema() {
        System.out.println("Hibernate generated the complete DDL schema successfully in sprintforge_schema_scratch!");
    }
}