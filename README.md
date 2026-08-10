# SprintForge — Enterprise Project Management Platform

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot 4.1.0](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL 16](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker Compose](https://img.shields.io/badge/Docker_Compose-v2+-blue.svg)](https://docs.docker.com/compose/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

SprintForge is a high-performance, enterprise-grade project management, workflow automation, and AI execution platform built using Java 21, Spring Boot, Spring Security, JPA/Hibernate, Flyway, PostgreSQL, Redis, Nginx, Prometheus, and Grafana.

---

## Quick Start (Local Production Environment)

### Prerequisites

- **Docker Desktop / Engine**: `v24.0.0` or higher
- **Docker Compose**: `v2.20.0` or higher
- **Git**: `v2.40.0` or higher

### 1. Clone & Configure

```bash
git clone https://github.com/megharaj-2006/SprintForge.git
cd SprintForge

# Create local environment configuration from template
cp .env.example .env
```

### 2. Start the Entire Platform

Run a single command to spin up the complete containerized stack:

```bash
docker compose up -d
```

> [!TIP]
> The backend automatically waits for PostgreSQL and Redis health checks before initializing Flyway schema migrations and booting Spring Boot.

---

## 🛠️ Docker Compose Profiles

SprintForge supports modular Docker Compose profiles for flexible development and deployment workflows:

| Profile | Included Services | Use Case | Command |
| :--- | :--- | :--- | :--- |
| **Default** | All Services | Full local production environment | `docker compose up -d` |
| **`core`** | `postgres`, `sprintforge-backend` | Core backend & DB development | `docker compose --profile core up -d` |
| **`infra`** | `redis`, `nginx` | Edge proxy & in-memory cache | `docker compose --profile infra up -d` |
| **`monitoring`** | `prometheus`, `grafana` | Performance & observability stack | `docker compose --profile monitoring up -d` |

### Profile Combination Examples

```bash
# Minimal core development
docker compose --profile core up -d

# Core development + edge infrastructure
docker compose --profile core --profile infra up -d

# Complete stack with full monitoring
docker compose --profile core --profile infra --profile monitoring up -d
```

---

## Service Endpoints & Access Points

| Service | Container Alias | Host Access URL | Description |
| :--- | :--- | :--- | :--- |
| **Nginx Reverse Proxy** | `nginx` | `http://localhost/` | Edge reverse proxy with security headers |
| **SprintForge Backend** | `sprintforge-backend` | `http://localhost:8080/` | Spring Boot REST API |
| **Swagger UI** | `sprintforge-backend` | `http://localhost:8080/swagger-ui.html` | OpenAPI documentation |
| **Actuator Health** | `sprintforge-backend` | `http://localhost:8080/actuator/health` | Health & readiness probes |
| **Prometheus** | `prometheus` | `http://localhost:9090/` | Metrics scraper & TSDB |
| **Grafana Dashboard** | `grafana` | `http://localhost:3000/` | Metrics visualization (`admin` / `admin_secure_password_123`) |
| **PostgreSQL Database** | `postgres` | `localhost:5432` | Relational DB (`SprintForge`) |
| **Redis Cache** | `redis` | `localhost:6379` | In-memory key-value store |

---

## Environment Variables Reference

Environment variables can be customized in `.env` or passed via Docker runtime:

| Variable | Default Value | Description |
| :--- | :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | `prod` | Spring active profile (`prod`, `dev`, `local`, `test`) |
| `SERVER_PORT` | `8080` | Backend HTTP port |
| `POSTGRES_DB` | `SprintForge` | PostgreSQL database name |
| `POSTGRES_USER` | `postgres` | Database username |
| `POSTGRES_PASSWORD` | `postgres` | Database password |
| `DB_URL` | `jdbc:postgresql://postgres:5432/SprintForge` | JDBC connection string |
| `JWT_SECRET` | *(64-char hex key)* | HMAC SHA-512 JWT signing secret |
| `JWT_EXPIRATION` | `86400000` (24 hrs) | JWT expiration time in milliseconds |
| `REDIS_PASSWORD` | `redis_secure_password_123` | Redis access password |
| `NGINX_PORT` | `80` | External Nginx port |
| `GRAFANA_ADMIN_PASSWORD`| `admin_secure_password_123` | Grafana admin account password |

---

## Operational Commands & Maintenance

### View Container Status & Health

```bash
docker compose ps
```

### Inspect Live Logs

```bash
# View combined logs
docker compose logs -f

# View specific service logs
docker compose logs -f sprintforge-backend
docker compose logs -f postgres
docker compose logs -f nginx
```

### Restart Platform

```bash
docker compose restart
```

### Shutdown & Clean Up

```bash
# Stop containers keeping volumes intact
docker compose down

# Stop containers and purge persistent volumes
docker compose down -v
```

---

## 🔒 Security & Architecture Standards

SprintForge strictly adheres to clean architecture principles:
- **Non-Root Execution**: Runs as unprivileged user `sprintforge:10001` inside Docker.
- **Stateless Authentication**: JWT token-based security filter chain.
- **Database Migrations**: Controlled strictly by Flyway (`V1` to `V14`).
- **Resource Constraints**: G1GC tuned JVM with max RAM percentage rules (`MaxRAMPercentage=75.0`).

---

## License

This project is released under the [MIT License](LICENSE).
