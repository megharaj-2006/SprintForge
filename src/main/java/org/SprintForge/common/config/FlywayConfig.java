package org.SprintForge.common.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class FlywayConfig {

    @Bean(name = "flyway", initMethod = "migrate")
    @Order(1)
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .baselineVersion("1")
                .validateOnMigrate(false)
                .outOfOrder(false)
                .load();
    }
}
