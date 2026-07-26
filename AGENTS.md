# SprintForge - Project Instructions for Antigravity

## Project Overview

SprintForge is an enterprise-grade project management platform built using Java 21, Spring Boot, PostgreSQL, Spring Security, JPA/Hibernate, Gradle, and REST APIs.

This repository follows strict engineering standards. These standards are considered the source of truth for every implementation decision.

Do not generate code that violates these standards.

---

# AI Workflow (MANDATORY)

Before writing or modifying any code:

1. Understand the user's request.
2. Identify which engineering standard(s) apply.
3. Read the relevant document(s) under `docs/ai/`.
4. Follow those standards exactly.
5. If multiple standards apply, satisfy all of them.
6. If documentation conflicts, ask for clarification instead of guessing.

Never skip this workflow.

---

# Engineering Standards

The following documents define SprintForge's architecture.

## Core Architecture

- docs/ai/architecture.md
- docs/ai/naming.md

---

## Domain Model

Read when working with entities or models.

- docs/ai/entities.md

---

## DTOs

Read when creating request/response models.

- docs/ai/dto.md

---

## Mapping

Read whenever converting between Entities and DTOs.

- docs/ai/mappers.md

---

## Persistence

Read whenever modifying repositories or database access.

- docs/ai/repositories.md
- docs/ai/database.md

---

## Business Logic

Read whenever implementing services.

- docs/ai/services.md

---

## REST API

Read whenever creating or modifying controllers or APIs.

- docs/ai/controllers.md
- docs/ai/api-standards.md

---

## Validation

Read whenever validating requests.

- docs/ai/validation.md

---

## Exception Handling

Read whenever throwing or handling exceptions.

- docs/ai/exceptions.md

---

## Security

Read whenever authentication, authorization, JWT, users, roles, or permissions are involved.

- docs/ai/security.md

---

## Logging

Read whenever implementing logging.

- docs/ai/logging.md

---

## Configuration

Read whenever adding configuration, properties, profiles, or environment variables.

- docs/ai/configuration.md

---

## Testing

Read whenever writing tests.

- docs/ai/testing.md

---

## Events

Read whenever implementing domain events or asynchronous workflows.

- docs/ai/events.md

---

# Development Rules

Always:

- Generate production-quality code.
- Follow SOLID principles.
- Follow Clean Architecture principles.
- Keep controllers thin.
- Keep business logic inside services.
- Use DTOs for API communication.
- Never expose JPA entities directly.
- Use constructor injection.
- Prefer immutability where appropriate.
- Write readable and maintainable code.
- Use meaningful names.
- Keep methods small and focused.
- Follow existing project conventions.

Never:

- Hardcode secrets.
- Duplicate business logic.
- Ignore validation.
- Return entities from controllers.
- Use field injection.
- Create unnecessary abstractions.
- Ignore transaction boundaries.
- Add code that contradicts the documented standards.

---

# Existing Code

When modifying existing code:

- Preserve project architecture.
- Maintain backward compatibility unless explicitly instructed otherwise.
- Refactor only when it improves maintainability.
- Avoid unnecessary file rewrites.
- Keep changes minimal and focused.

---

# Code Generation Expectations

Unless explicitly requested otherwise:

- Generate complete implementations.
- Include imports.
- Include JavaDoc only when useful.
- Follow Java 21 best practices.
- Follow Spring Boot best practices.
- Ensure code compiles.
- Avoid placeholder implementations.

---

# Priority Order

If multiple sources of truth exist, use the following precedence:

1. Explicit user instructions
2. This AGENTS.md
3. Relevant document(s) in docs/ai/
4. Existing project architecture
5. Spring Boot best practices
6. General Java best practices

---

# Goal

Every piece of generated code should feel as if it was written by the same senior backend engineer and should conform to the SprintForge engineering handbook.

handbook complete