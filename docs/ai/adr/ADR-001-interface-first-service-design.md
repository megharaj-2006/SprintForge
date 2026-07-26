# ADR-001 — Interface-First Service Design

**Status:** Accepted

## Context

SprintForge is intended to be a long-term, enterprise-grade platform with hundreds of services. Directly depending on concrete service implementations would increase coupling and reduce flexibility for testing and future refactoring.

## Decision

Every service shall be defined as an interface with a corresponding implementation.

Example:

- `WorkspaceService`
- `WorkspaceServiceImpl`

Controllers, schedulers, and other services must depend only on the service interface.

## Alternatives Considered

### Concrete service classes only

Rejected because it tightly couples consumers to implementations and reduces flexibility for testing.

### Functional utility classes

Rejected because they do not model business capabilities or integrate well with dependency injection.

## Rationale

Interface-driven design follows the Dependency Inversion Principle and provides clear separation between contracts and implementations.

## Consequences

### Benefits

- Loose coupling
- Easier testing and mocking
- Stable public contracts
- Easier implementation replacement

### Trade-offs

- Additional files
- Slightly more boilerplate

## Related Standards

- services.md
- testing.md