# ADR-005 — Constructor Injection Only

**Status:** Accepted

## Context

Spring supports constructor, field, and setter injection. Using multiple approaches across a project leads to inconsistency and makes dependencies less explicit.

## Decision

SprintForge shall use constructor injection exclusively.

Field injection and setter injection are prohibited except where a framework explicitly requires them.

## Alternatives Considered

### Field injection

Rejected because dependencies are hidden, objects become harder to test, and immutability is lost.

### Setter injection

Rejected because mandatory dependencies become optional after object creation.

## Rationale

Constructor injection clearly communicates required dependencies, promotes immutability, and simplifies testing.

## Consequences

### Benefits

- Explicit dependencies
- Immutable objects
- Easier unit testing
- Consistent coding style

### Trade-offs

- Longer constructors for services with many dependencies

## Related Standards

- services.md
- testing.md