package org.SprintForge.database;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("prod")
public class CleanDatabaseApplicationStartupTest {

    @BeforeAll
    public static void migrateCleanDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/sprintforge_clean_test", "postgres", "root123")
                .locations("classpath:db/migration")
                .baselineOnMigrate(false)
                .outOfOrder(false)
                .load();
        flyway.migrate();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/sprintforge_clean_test");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "root123");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.baseline-on-migrate", () -> "false");
        registry.add("JWT_SECRET", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("jwt.secret", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("sprintforge.security.jwt.secret", () -> "0123456789012345678901234567890123456789012345678901234567890123");
        registry.add("sprintforge.security.jwt.secret-key", () -> "0123456789012345678901234567890123456789012345678901234567890123");
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads_WithCleanPostgreSqlAndDdlAutoValidate() {
        assertNotNull(applicationContext);
        System.out.println("Spring Boot Production-like Application context started successfully with ddl-auto=validate on clean PostgreSQL DB!");
    }
}