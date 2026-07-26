# ADR-003 — Thin Controller Architecture

**Status:** Accepted

## Context

Controllers often become bloated when they contain validation, business rules, and orchestration logic.

## Decision

Controllers are responsible only for:

- Receiving requests
- Delegating to services
- Returning responses

Business logic must reside exclusively in the service layer.

## Alternatives Considered

### Fat controllers

Rejected because they duplicate business logic and reduce maintainability.

### Mixed controller-service responsibilities

Rejected because responsibilities become unclear.

## Rationale

Controllers represent the HTTP layer. Business rules should remain independent of transport protocols.

## Consequences

### Benefits

- Clear separation of concerns
- Easier testing
- Better code organization

### Trade-offs

- Slight increase in service responsibilities

## Related Standards

- controllers.md
- services.md