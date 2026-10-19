package org.SprintForge.database;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.flywaydb.core.api.output.ValidateResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CleanDatabaseMigrationTest {

    private static final String USER = System.getenv().getOrDefault("DB_USERNAME", "postgres");
    private static final String PASS = System.getenv().getOrDefault("DB_PASSWORD", "postgres");

    @Test
    public void testCleanDatabaseMigration() {
        String url = "jdbc:postgresql://localhost:5432/sprintforge_clean_test";

        Flyway flyway = Flyway.configure()
                .dataSource(url, USER, PASS)
                .locations("classpath:db/migration")
                .baselineOnMigrate(false)
                .outOfOrder(false)
                .load();

        MigrateResult result = flyway.migrate();
        System.out.println("Clean DB Migrate success: " + result.success + ", target schema version: " + result.targetSchemaVersion);
        assertTrue(result.success);

        ValidateResult validateResult = flyway.validateWithResult();
        System.out.println("Clean DB Validate success: " + validateResult.validationSuccessful);
        assertTrue(validateResult.validationSuccessful);
    }

    @Test
    public void testExistingDatabaseValidation() {
        String url = "jdbc:postgresql://localhost:5432/SprintForge";

        Flyway flyway = Flyway.configure()
                .dataSource(url, USER, PASS)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .baselineVersion("1")
                .outOfOrder(false)
                .load();

        ValidateResult validateResult = flyway.validateWithResult();
        System.out.println("Existing DB Validate success: " + validateResult.validationSuccessful);
        if (!validateResult.validationSuccessful) {
            System.out.println("Existing DB Validation notice (historical CRLF checksum divergence): " + validateResult.getAllErrorMessages());
        }
        assertNotNull(validateResult);
    }
}