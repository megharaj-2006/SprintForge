package org.SprintForge.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Slf4j
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url:${DB_URL:${DATABASE_URL:${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/SprintForge}}}}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:${DB_USERNAME:${SPRING_DATASOURCE_USERNAME:postgres}}}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:${DB_PASSWORD:${SPRING_DATASOURCE_PASSWORD:postgres}}}")
    private String datasourcePassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        String url = datasourceUrl != null ? datasourceUrl.trim() : "";
        String username = datasourceUsername;
        String password = datasourcePassword;

        if (url.startsWith("postgres://") || url.startsWith("postgresql://")) {
            try {
                URI uri = new URI(url.replace("postgresql://", "postgres://"));
                String userInfo = uri.getUserInfo();
                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":", 2);
                    username = parts[0];
                    password = parts[1];
                }
                int port = uri.getPort() > 0 ? uri.getPort() : 5432;
                String query = uri.getQuery();
                String cleanPath = uri.getPath();
                url = "jdbc:postgresql://" + uri.getHost() + ":" + port + cleanPath + (query != null ? "?" + query : "");
                log.info("Converted cloud DATABASE_URL to JDBC format for host: {}", uri.getHost());
            } catch (Exception e) {
                log.warn("Failed to parse postgres URI, using raw URL: {}", e.getMessage());
            }
        }

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setPoolName("SprintForgeHikariPool");

        return new HikariDataSource(config);
    }
}