# ADR-008 — Event-Driven Collaboration

**Status:** Accepted

## Context

Many business operations affect multiple modules.

For example, creating a task may require:

- Activity logging
- Notifications
- Analytics
- AI indexing
- Audit logging

Directly invoking every dependent module increases coupling.

## Decision

Business services shall publish domain events rather than directly invoking unrelated modules.

Examples:

- TaskCreatedEvent
- WorkspaceArchivedEvent
- MemberInvitedEvent

Interested modules subscribe to these events independently.

## Alternatives Considered

### Direct service calls

Rejected because every new feature increases coupling.

### Repository callbacks

Rejected because persistence should not coordinate business workflows.

## Rationale

An event-driven architecture allows SprintForge to evolve without modifying existing business services whenever new integrations are introduced.

## Consequences

### Benefits

- Loose coupling
- Better extensibility
- Improved scalability
- Easier integrations

### Trade-offs

- More complex event management

## Related Standards

- services.md
- events.md