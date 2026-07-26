# ADR-006 — One Aggregate Root Per Service

**Status:** Accepted

## Context

SprintForge consists of multiple business domains including Workspaces, Projects, Boards, Tasks, Sprints, Members, Notifications, Attachments, and AI.

Allowing services to own multiple aggregate roots increases coupling, duplicates business logic, and makes ownership unclear.

## Decision

Each service shall own exactly one aggregate root.

Examples:

- WorkspaceService → Workspace
- ProjectService → Project
- TaskService → Task
- SprintService → Sprint

Cross-domain operations must be performed through the owning service rather than directly accessing another aggregate.

## Alternatives Considered

### Multi-aggregate services

Rejected because business ownership becomes ambiguous and maintenance becomes increasingly difficult.

### Shared repository access

Rejected because it bypasses domain rules and weakens module boundaries.

## Rationale

Keeping one aggregate root per service aligns with Domain-Driven Design principles and establishes clear ownership of business rules.

## Consequences

### Benefits

- Clear ownership
- Better modularity
- Easier maintenance
- Reduced coupling

### Trade-offs

- More service-to-service communication

## Related Standards

- services.md
- architecture.md