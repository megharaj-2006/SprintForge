# ADR-007 — Services Communicate Through Services

**Status:** Accepted

## Context

Business rules belong to the service that owns the corresponding domain.

Direct repository access across modules bypasses business logic and creates tight coupling.

## Decision

A service must never directly access another module's repository.

Instead, services communicate through the public interface of the owning service.

Correct:

WorkspaceService
→ ProjectService
→ ProjectRepository

Incorrect:

WorkspaceService
→ ProjectRepository

## Alternatives Considered

### Cross-module repository access

Rejected because it violates encapsulation.

### Shared repositories

Rejected because repositories are implementation details of their owning module.

## Rationale

Business rules remain centralized within the owning service, ensuring consistency and maintainability.

## Consequences

### Benefits

- Strong module boundaries
- Consistent business rules
- Easier future microservice migration

### Trade-offs

- Additional service dependencies

## Related Standards

- services.md
- repositories.md