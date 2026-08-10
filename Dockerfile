# ============================================================================
# SprintForge Enterprise Platform - Multi-Stage Dockerfile
# Stage 1: Build fat JAR using Gradle toolchain
# Stage 2: Production JRE runtime image with non-root security hardening
# ============================================================================

# ----------------------------------------------------------------------------
# Build Stage
# ----------------------------------------------------------------------------
FROM eclipse-adoptium:21-jdk-alpine AS builder

WORKDIR /app

# Copy Gradle wrapper and configuration files first for layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable & pre-fetch dependencies
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon || true

# Copy application source code
COPY src src

# Build executable production JAR without running tests (tests execute in CI/CD pipeline)
RUN ./gradlew bootJar -x test --no-daemon

# ----------------------------------------------------------------------------
# Runtime Stage
# ----------------------------------------------------------------------------
FROM eclipse-adoptium:21-jre-alpine AS runner

# Create non-root system user and group
RUN addgroup -S sprintforge && adduser -S sprintforge -G sprintforge -u 10001

WORKDIR /app

# Create required directories with proper ownership
RUN mkdir -p /app/logs /app/uploads && chown -R sprintforge:sprintforge /app

# Copy compiled JAR from builder stage
COPY --from=builder --chown=sprintforge:sprintforge /app/build/libs/*.jar app.jar

# Switch to non-root user
USER sprintforge:sprintforge

# Expose Spring Boot HTTP port
EXPOSE 8080

# Environment variables with sensible container defaults
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError" \
    SPRING_PROFILES_ACTIVE="prod" \
    SERVER_PORT=8080

# Health check using Actuator readiness probe endpoint
HEALTHCHECK --interval=15s --timeout=5s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Entrypoint execution
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
