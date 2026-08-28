package org.SprintForge.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "sprintforge")
@Validated
@Getter
@Setter
public class SprintForgeProperties {

    private Security security = new Security();
    private Execution execution = new Execution();

    @Getter
    @Setter
    public static class Security {
        private Jwt jwt = new Jwt();
        private Cors cors = new Cors();
    }

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long expiration = 86400000L;
    }

    @Getter
    @Setter
    public static class Cors {
        private String allowedOrigins = "*";
    }

    @Getter
    @Setter
    public static class Execution {
        private int maxPageSize = 100;
        private int threadPoolCoreSize = 10;
        private int threadPoolMaxSize = 50;
        private int threadPoolQueueCapacity = 500;
    }
}
