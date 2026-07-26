# ADR-004 — DTO-Only Service Contracts

**Status:** Accepted

## Context

Returning JPA entities from services exposes persistence details and tightly couples APIs to the database model.

## Decision

Public service methods shall accept request DTOs and return response DTOs.

JPA entities must never cross service boundaries.

## Alternatives Considered

### Returning entities directly

Rejected because it leaks internal implementation details and increases accidental data exposure.

### Mixing entities and DTOs

Rejected because it produces inconsistent APIs.

## Rationale

DTOs form stable contracts between layers while allowing the persistence model to evolve independently.

## Consequences

### Benefits

- Stable APIs
- Better encapsulation
- Improved security
- Easier versioning

### Trade-offs

- Requires mapper implementations

## Related Standards

- services.md
- mappers.md
- dtos.md