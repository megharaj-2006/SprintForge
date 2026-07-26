# ADR-009 — AI as a Business Capability

**Status:** Accepted

## Context

SprintForge integrates AI throughout the platform for recommendations, summarization, estimation, planning, and analytics.

Embedding AI logic directly into business services would tightly couple the application to specific AI providers.

## Decision

Artificial Intelligence shall be implemented as a dedicated business capability.

Business services may request AI capabilities but must never directly communicate with AI providers.

Example:

WorkspaceService

↓

WorkspaceAnalysisService

↓

LLM Provider

## Alternatives Considered

### Direct LLM integration inside services

Rejected because provider changes would require modifying business logic.

### AI utilities

Rejected because AI operations are business capabilities rather than generic utilities.

## Rationale

Separating AI into dedicated services improves maintainability, testing, and provider independence.

## Consequences

### Benefits

- Provider independence
- Easier testing
- Better separation of concerns
- Cleaner architecture

### Trade-offs

- Additional abstraction layer

## Related Standards

- services.md
- architecture.md