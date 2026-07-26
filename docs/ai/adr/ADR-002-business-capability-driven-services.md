# ADR-002 — Business Capability Driven Services

**Status:** Accepted

## Context

Many applications treat services as CRUD wrappers around repositories. SprintForge aims to model business workflows rather than persistence operations.

## Decision

Services shall represent business capabilities instead of CRUD operations.

Examples:

- inviteMember()
- archiveWorkspace()
- generateSprintPlan()
- recommendTaskDistribution()

Service methods must describe business intent.

## Alternatives Considered

### CRUD-based services

Rejected because they expose persistence concepts instead of business behavior.

### Repository-centric architecture

Rejected because business rules become fragmented and difficult to maintain.

## Rationale

Users think in terms of actions, not database operations. Designing services around capabilities results in clearer APIs and better separation of concerns.

## Consequences

### Benefits

- Rich business APIs
- Better maintainability
- Improved readability
- Easier future expansion

### Trade-offs

- Requires more design effort during development

## Related Standards

- services.md
- architecture.md