# SprintForge Engineering Standard
# Entity Layer

Version: 1.0
Status: Approved
Scope: Entire SprintForge Backend

---

# Part 1
# Entity Philosophy & Domain Modeling

---

# 1. Purpose

Entities represent the core business domain of SprintForge.

An entity models a real business concept whose identity persists throughout its lifecycle, regardless of changes to its attributes.

Examples include:

Workspace

Project

Sprint

Task

Comment

User

Invitation

Entities are the foundation of the application's domain model and form the bridge between business concepts and persistent storage.

---

# 2. Entity Philosophy

SprintForge follows a domain-driven approach to entity modeling.

Entities should model business concepts rather than database tables.

The database exists to persist the domain model—not to define it.

Entity design should prioritize:

✓ Business meaning

✓ Consistency

✓ Maintainability

✓ Long-term evolution

Database implementation details should never dictate the business model.

---

# 3. What Is an Entity?

An entity is an object that possesses a stable identity throughout its lifetime.

Its identity remains constant even when its attributes change.

Example

Task

↓

Title changes

↓

Description changes

↓

Priority changes

↓

Status changes

↓

Still the same Task

Identity—not attribute values—defines an entity.

---

# 4. Entity Responsibilities

Entities are responsible for representing persistent business state.

Responsibilities include:

✓ Maintaining business state

✓ Defining relationships

✓ Preserving identity

✓ Enforcing simple invariants

✓ Participating in persistence

Entities should accurately model the domain they represent.

---

# 5. Responsibilities That Do NOT Belong in Entities

Entities must never contain:

✗ HTTP logic

✗ REST endpoints

✗ Repository access

✗ Service calls

✗ Email sending

✗ AI integration

✗ External API communication

✗ Authentication

✗ Authorization

✗ DTO mapping

✗ JSON serialization logic

✗ Business workflows spanning multiple aggregates

Entities should remain persistence-aware but infrastructure-independent.

---

# 6. Entity Layer Architecture

Client

↓

Controller

↓

Service

↓

Repository

↓

Entity

↓

Database

Entities represent the persistent domain model used by the application.

They should not depend on higher architectural layers.

---

# 7. Rich vs Anemic Domain Model

SprintForge adopts a pragmatic domain model.

Entities may contain behavior that directly protects their own consistency.

Examples

Task.markCompleted()

Project.archive()

Invitation.accept()

Entities should not coordinate workflows involving multiple aggregates.

Complex business processes belong in services.

---

# 8. Aggregate Roots

Each aggregate has one root entity.

Examples

Workspace

↓

Projects

↓

Sprints

↓

Tasks

Workspace is the aggregate root.

External components should interact with an aggregate through its root whenever practical.

Aggregate roots preserve consistency within their boundaries.

---

# 9. Long-Term Vision

Entities should remain stable as the application evolves.

New features should extend the domain model rather than fundamentally restructuring it.

A well-designed entity model enables:

✓ Scalable persistence

✓ Predictable business behavior

✓ Maintainable services

✓ Stable APIs

✓ Long-term architectural evolution

Entities are long-lived representations of business concepts and should be designed with durability, clarity, and extensibility in mind.

---

---

# 10. Entity vs Value Object

SprintForge distinguishes between Entities and Value Objects.

An Entity is defined by its identity.

Its identity remains constant even when its attributes change.

Examples

Workspace

User

Project

Sprint

Task

Comment

Invitation

A Value Object is defined entirely by its attributes.

Two Value Objects with identical values are considered equal.

Examples

Address

DateRange

Money

GeoLocation

Color

TimeZone

Entities should reference Value Objects whenever a concept has no independent identity.

Value Objects should be:

✓ Immutable

✓ Self-contained

✓ Side-effect free

✓ Equality based on values

Where appropriate, Value Objects may be implemented using JPA Embeddables or immutable domain objects.

Choosing Value Objects instead of Entities reduces complexity and improves the expressiveness of the domain model.

---

# 11. Domain Invariants

Entities are responsible for protecting their own internal consistency.

A domain invariant is a rule that must always remain true for an entity.

Examples

A completed task cannot have a completion date earlier than its creation date.

A sprint cannot end before it starts.

A workspace must always have an owner.

An invitation cannot expire before it is created.

Entities should prevent themselves from entering invalid states whenever possible.

Simple invariants that involve only the entity's own state belong inside the entity.

Rules involving multiple aggregates or external systems belong in the service layer.

Examples

Entity Responsibility

✓ Validate internal state

✓ Preserve consistency

✓ Prevent impossible states

Service Responsibility

✓ Coordinate multiple entities

✓ Validate cross-aggregate rules

✓ Execute business workflows

Keeping invariants close to the entity ensures that invalid domain objects cannot be created or persisted.

---

# 12. Ubiquitous Language

SprintForge follows the principle of Ubiquitous Language from Domain-Driven Design.

The terminology used throughout the codebase should match the language used by the business domain.

Entity names, fields, methods, and relationships should use meaningful business terminology.

Correct Examples

Workspace

Project

Sprint

Task

Backlog

Member

Invitation

Milestone

Incorrect Examples

DataObject

Record

Item

Entry

Object1

TempEntity

Method names should also reflect business actions.

Correct

archive()

complete()

assign()

invite()

accept()

reject()

Incorrect

execute()

process()

updateData()

handle()

Business terminology should remain consistent across:

✓ Entities

✓ Services

✓ Repositories

✓ DTOs

✓ Controllers

✓ API Endpoints

✓ Database Schema

✓ Documentation

Using a consistent ubiquitous language improves communication between developers, product owners, designers, and stakeholders while making the codebase easier to understand and maintain.

---

End of Part 1

# SprintForge Engineering Standard
# Entity Layer

# Part 2
# Entity Structure & Organization

---

# 13. Package Structure

Every domain module should organize its entities consistently.

Example

workspace/

    entity/
        Workspace.java
        WorkspaceSettings.java
        WorkspaceMember.java

    repository/

    service/

    controller/

    dto/

    mapper/

Entity classes should remain inside the entity package of their owning module.

Supporting embeddables and value objects should also reside within the entity package unless shared across multiple modules.

---

# 14. One Class Per Entity

Each business entity should be represented by a single Java class.

Correct

Workspace.java

Project.java

Sprint.java

Task.java

Comment.java

Avoid splitting an entity's state across multiple persistence classes without strong architectural justification.

---

# 15. Entity Naming Convention

Entity names should be singular nouns representing business concepts.

Correct

Workspace

Project

Sprint

Task

Comment

Notification

Invitation

Incorrect

Tasks

WorkspaceTable

ProjectEntityModel

TaskRecord

Names should communicate business meaning rather than implementation details.

---

# 16. Table Naming Convention

Database table names should remain consistent throughout the application.

Recommended naming style:

snake_case

Examples

workspace

workspace_member

project

project_label

sprint

task

task_comment

Avoid mixing naming conventions within the same database schema.

---

# 17. Entity Visibility

Entities should be declared as public classes.

Fields should remain private.

State should be modified through well-defined methods rather than unrestricted field access.

Encapsulation preserves entity consistency and improves maintainability.

---

# 18. Constructors

Every entity should provide:

✓ A protected or public no-argument constructor for JPA.

✓ Constructors or factory methods that initialize required business state.

Avoid exposing constructors that allow creation of partially initialized entities.

---

# 19. Factory Methods

When entity creation requires business initialization, prefer static factory methods.

Example

Workspace.create(...)

Invitation.issue(...)

Sprint.start(...)

Factory methods improve readability and ensure required fields are initialized consistently.

Complex creation workflows should still belong in the service layer.

---

# 20. Field Organization

Fields should follow a consistent ordering.

Recommended order:

Identity

↓

Business Fields

↓

Relationships

↓

Audit Fields

↓

Version Field

↓

Soft Delete Fields

Consistent ordering improves readability across the codebase.

---

# 21. Encapsulation

Entity fields should not be publicly mutable.

Prefer exposing business methods instead of generic setters.

Correct

task.complete()

project.archive()

invitation.accept()

Incorrect

setStatus()

setArchived()

setCompleted()

Business methods communicate intent and help preserve invariants.

---

# 22. Immutability Where Appropriate

Not every field should be mutable.

Examples of commonly immutable fields:

id

createdAt

createdBy

Fields representing historical facts should not change after creation.

Mutable fields should reflect legitimate business state changes.

---

# 23. Equals and HashCode

Entity equality should be based on identity rather than mutable business attributes.

Avoid including mutable fields in equals() and hashCode() implementations.

Entity identity should remain stable throughout the entity's lifecycle.

---

# 24. ToString Implementation

Entity toString() methods should remain concise.

Include:

✓ Identifier

✓ Key business fields

Avoid including:

Large collections

Sensitive information

Recursive relationships

Verbose toString() implementations can impact performance and produce unreadable logs.

---

# 25. Sensitive Data

Entities containing sensitive information should prevent accidental exposure.

Examples

Password

Refresh Token

API Key

Secret

Verification Token

Sensitive fields should never appear in:

Logs

toString()

Debug output

Error messages

Protecting sensitive information is a core security responsibility.

---

# 26. Entity Size

Entities should remain focused on a single business concept.

Warning signs of oversized entities include:

Hundreds of fields

Dozens of relationships

Multiple unrelated responsibilities

When an entity becomes excessively large, consider introducing additional entities or value objects.

---

# 27. Inner Classes

Avoid defining business entities as inner classes.

Each entity should exist as an independent top-level class.

Independent classes improve discoverability, testing, and maintainability.

---

# 28. Serialization

Entities should not be designed as API contracts.

Avoid adding serialization-specific annotations solely to satisfy REST responses.

JSON serialization concerns belong to DTOs.

Entities should model the domain rather than API responses.

---

# 29. Documentation

Entities representing complex business concepts should include concise documentation describing:

Purpose

Business meaning

Aggregate ownership

Important invariants

Documentation should explain the domain rather than the implementation.

---

# 30. Design Goals

Entity structure should be:

✓ Consistent

✓ Encapsulated

✓ Readable

✓ Maintainable

✓ Persistence-Friendly

✓ Business-Oriented

✓ Easy to Extend

Every entity should clearly communicate the business concept it represents while remaining simple, cohesive, and aligned with SprintForge's architectural standards.

---

````md id="0g2x8u"
---

# 31. Base Entity Strategy

SprintForge should use a common Base Entity to centralize infrastructure-related fields shared by most persistent entities.

A typical Base Entity may include:

✓ Primary Identifier

✓ Audit Fields

✓ Version Field

Examples

id

createdAt

createdBy

updatedAt

updatedBy

version

Business-specific fields should never be placed in the Base Entity.

Examples

Workspace Name

Task Priority

Sprint Goal

Project Visibility

Base entities should provide common persistence infrastructure rather than common business behavior.

Inheritance should reduce duplication without weakening domain clarity.

---

# 32. Lombok Usage Standards

SprintForge uses Lombok to reduce boilerplate while preserving explicit domain modeling.

Recommended annotations include:

✓ @Getter

✓ @Setter (only where justified)

✓ @NoArgsConstructor

✓ @RequiredArgsConstructor

✓ @Builder (where appropriate)

✓ @AllArgsConstructor (sparingly)

Avoid using:

✗ @Data

✗ @Value (for JPA entities)

✗ @EqualsAndHashCode without explicit configuration

✗ @ToString without excluding relationships

The @Data annotation generates:

equals()

hashCode()

toString()

getters

setters

These generated methods frequently conflict with:

Lazy loading

Entity identity

Bidirectional relationships

Persistence proxies

Developers should explicitly control equality and string representations for JPA entities.

---

# 33. Java Record Usage

Java Records are not suitable for JPA entities.

Entities require:

✓ Mutable business state

✓ Lifecycle management

✓ Lazy-loading proxies

✓ Persistence context support

✓ No-argument constructors

Records are immutable by design and therefore better suited for:

DTOs

Projection Models

Value Objects

Configuration Objects

Repository projections

SprintForge entities should always be implemented as standard Java classes.

---

# 34. Entity Review Checklist

Before introducing a new entity into SprintForge, developers should review the following questions:

✓ Does this entity represent exactly one business concept?

✓ Does it have a stable identity?

✓ Is it the correct aggregate root or child entity?

✓ Are constructors appropriate?

✓ Are required fields initialized?

✓ Is encapsulation preserved?

✓ Are unnecessary public setters avoided?

✓ Are business methods intention-revealing?

✓ Is equals() based on identity?

✓ Is hashCode() based on identity?

✓ Is toString() concise and safe?

✓ Are sensitive fields protected?

✓ Are infrastructure fields inherited from the Base Entity where appropriate?

✓ Is @Data avoided?

✓ Are Java Records avoided for entities?

✓ Is the entity free from controller, repository, service, and DTO concerns?

✓ Does the entity comply with SprintForge architectural standards?

Every entity should be reviewed for correctness, maintainability, consistency, and long-term evolution before becoming part of the domain model.

---

End of Part 2
````
# SprintForge Engineering Standard
# Entity Layer

# Part 3
# Identity, Keys & Lifecycle

---

# 35. Identity Philosophy

Every entity must possess a stable identity that uniquely distinguishes it from every other entity.

Identity remains constant throughout the entity's lifetime, regardless of changes to business attributes.

Examples

Task

↓

Title changes

↓

Priority changes

↓

Status changes

↓

Still the same Task

Identity is the defining characteristic of an entity.

---

# 36. Primary Key Strategy

Every persistent entity must define a primary key.

SprintForge standardizes on:

UUID

for all aggregate roots unless a strong technical justification exists otherwise.

Primary keys should be immutable after creation.

---

# 37. UUID Usage

UUIDs provide globally unique identifiers without requiring centralized ID generation.

Benefits include:

✓ Globally unique

✓ Safer for distributed systems

✓ Difficult to guess

✓ Independent of database sequence generation

✓ Easier future scalability

UUIDs should be generated once and never modified.

---

# 38. Identifier Naming

Primary keys should always use the field name:

id

Examples

Workspace

↓

UUID id

Project

↓

UUID id

Task

↓

UUID id

Avoid alternative names such as:

workspaceId

taskId

identifier

primaryKey

Within an entity, the identifier field should always be named id.

---

# 39. Identifier Immutability

Entity identifiers must never change after creation.

Changing an entity's identifier effectively creates a different entity.

Repositories and services should always treat entity identity as immutable.

---

# 40. Natural Keys vs Surrogate Keys

SprintForge primarily uses surrogate keys.

Examples

UUID

Database-generated identifier

Natural business attributes such as:

Email

Slug

Username

Workspace Code

should be modeled as unique business fields rather than primary keys.

Business identifiers may change.

Entity identity should not.

---

# 41. Business Identifiers

Many entities contain business identifiers in addition to their primary key.

Examples

Workspace Slug

Project Code

Invitation Token

User Email

Business identifiers should be:

Unique where required

Validated by business rules

Independent of entity identity

Repositories may expose lookup methods using business identifiers.

---

# 42. Entity Lifecycle States

An entity progresses through several persistence states.

New

↓

Managed

↓

Detached

↓

Removed

Developers should understand these lifecycle states when designing persistence operations.

Business logic should generally remain independent of persistence state management.

---

# 43. New Entities

New entities exist only in application memory.

Characteristics

No database row

No persistence context

Eligible for persistence

New entities become managed after being persisted.

---

# 44. Managed Entities

Managed entities are tracked by the persistence context.

Changes made to managed entities are automatically detected and synchronized with the database during transaction commit.

Managed entities represent the normal working state during repository operations.

---

# 45. Detached Entities

Detached entities are no longer managed by the persistence context.

Changes made to detached entities are not automatically persisted.

Developers should avoid relying on detached entities for business operations.

Detached entities should generally be reattached through standard persistence mechanisms when necessary.

---

# 46. Removed Entities

Removed entities have been marked for deletion.

The actual database deletion occurs when the active transaction is committed.

Soft delete implementations may instead transition entities into an inactive state.

---

# 47. Entity Equality

Entity equality should be based on identity.

Business fields such as:

Name

Description

Status

Priority

must not determine entity equality.

Two entities representing the same identifier are considered equal regardless of attribute differences.

---

# 48. Hash Code Strategy

hashCode() should remain stable throughout the entity's lifetime.

Avoid including mutable business fields in hashCode() calculations.

Identity-based hash codes prevent inconsistent behavior in collections.

---

# 49. Lifecycle Callbacks

JPA lifecycle callbacks may be used for infrastructure-related concerns.

Examples

@PrePersist

@PostPersist

@PreUpdate

@PostUpdate

@PreRemove

@PostRemove

Lifecycle callbacks should remain lightweight.

Complex business workflows should never be implemented inside entity lifecycle methods.

---

# 50. Callback Responsibilities

Appropriate lifecycle callback responsibilities include:

✓ Initialize timestamps

✓ Normalize data

✓ Generate derived persistence values

✓ Maintain internal consistency

Avoid:

✗ Sending emails

✗ Publishing events

✗ Calling services

✗ Executing business workflows

Callbacks should remain focused on entity persistence.

---

# 51. Identifier Generation

Identifier generation should occur exactly once during entity creation.

Repositories should never regenerate identifiers.

Business services should not modify entity identities.

Entity identity is permanent.

---

# 52. Persistence Context Awareness

Entities should remain persistence-context aware without depending on persistence implementation details.

Developers should understand that:

Managed entities synchronize automatically.

Detached entities do not.

Repositories are responsible for persistence operations.

Entities should not manipulate the persistence context directly.

---

# 53. Entity Recreation

An entity should never be recreated merely because one of its business attributes changes.

Examples

Task Title Changed

↓

Update Existing Task

Not

↓

Delete Task

↓

Create New Task

Identity persists while business state evolves.

---

# 54. Design Goals

Entity identity should be:

✓ Stable

✓ Immutable

✓ Globally Unique

✓ Consistent

✓ Predictable

✓ Independent of Business Attributes

✓ Persistence-Friendly

Every entity should preserve its identity throughout its lifecycle while remaining easy to manage, persist, and evolve.

---

````md id="k3e8vn"
---

# 55. Composite Keys

SprintForge discourages the use of composite primary keys.

Prefer a single surrogate identifier, typically a UUID, for every entity.

Composite keys should only be introduced when they naturally represent the domain and cannot be modeled more effectively using a surrogate key.

Examples where composite keys may be acceptable include:

• Pure join tables

• Legacy database integration

• Immutable reference data

Even in these situations, carefully evaluate whether a surrogate UUID combined with unique constraints provides a simpler and more maintainable design.

Repository APIs should remain simple and consistent by avoiding composite identifiers whenever practical.

---

# 56. Entity State Transitions

Entities should expose explicit business methods for state transitions.

Business state should evolve through meaningful operations rather than arbitrary field updates.

Correct

task.start()

task.complete()

task.reopen()

project.archive()

invitation.accept()

Incorrect

task.setStatus(...)

project.setArchived(...)

invitation.setAccepted(...)

Explicit state transition methods:

✓ Preserve invariants

✓ Improve readability

✓ Prevent invalid transitions

✓ Communicate business intent

Entities should reject invalid state transitions whenever possible.

Example

Todo

↓

In Progress

↓

Completed

↓

Archived

Skipping required intermediate states should only be allowed when explicitly supported by business rules.

---

# 57. Immutable Historical Data

Some entity fields represent historical facts and should never change after the entity has been created.

Examples include:

createdAt

createdBy

originalInvitationToken

initialWorkspaceOwner

originalProjectCreator

Historical information provides:

✓ Audit integrity

✓ Business traceability

✓ Reliable reporting

Repositories and services should treat these fields as immutable.

Corrections to historical data should occur only through carefully controlled administrative processes where business policies permit.

Historical integrity is more valuable than convenience.

---

# 58. Identity & Lifecycle Review Checklist

Before introducing or modifying an entity, developers should verify the following:

✓ Does the entity have a stable UUID?

✓ Is the identifier immutable?

✓ Are business identifiers separate from the primary key?

✓ Does the entity preserve its identity throughout its lifecycle?

✓ Are lifecycle callbacks lightweight?

✓ Are callbacks free from business workflows?

✓ Is equals() based solely on identity?

✓ Is hashCode() identity-based?

✓ Are invalid state transitions prevented?

✓ Are historical fields protected from modification?

✓ Are entity states clearly understood?

✓ Does the entity comply with SprintForge identity standards?

Every entity should maintain a consistent identity throughout its lifetime while protecting its internal consistency and supporting predictable persistence behavior.

---

End of Part 3
````

# SprintForge Engineering Standard
# Entity Layer

# Part 4
# Relationships & Aggregate Design

---

# 59. Relationship Philosophy

Relationships should model real business relationships rather than merely satisfying database constraints.

Every relationship should answer a business question.

Examples

Workspace

owns

Projects

Project

contains

Sprints

Sprint

contains

Tasks

Task

contains

Comments

Relationships should improve the expressiveness of the domain model.

---

# 60. Aggregate Philosophy

SprintForge models the domain using Aggregates.

An Aggregate is a cluster of related entities that maintain consistency together.

Each Aggregate has exactly one Aggregate Root.

External components should communicate with the aggregate through its root.

Aggregates provide clear ownership boundaries and simplify transactional consistency.

---

# 61. Aggregate Root Responsibilities

Aggregate Roots are responsible for:

✓ Protecting aggregate consistency

✓ Managing child entities

✓ Enforcing aggregate invariants

✓ Acting as the entry point for modifications

Examples

Workspace

↓

Projects

↓

Sprints

↓

Tasks

External services should normally modify Tasks through the aggregate's business operations rather than bypassing aggregate boundaries.

---

# 62. Aggregate Boundaries

Aggregate boundaries define what changes together.

Rules inside an aggregate should remain strongly consistent.

Rules spanning multiple aggregates belong in the service layer.

Small aggregates generally scale better than very large aggregates.

---

# 63. Relationship Ownership

Every bidirectional relationship must clearly define an owning side.

The owning side is responsible for maintaining the foreign key.

Inverse relationships should use:

mappedBy

Ownership should never be ambiguous.

---

# 64. Unidirectional vs Bidirectional Relationships

Prefer unidirectional relationships unless navigation is genuinely required in both directions.

Unidirectional relationships:

✓ Simpler

✓ Easier to maintain

✓ Lower coupling

Bidirectional relationships should only be introduced when they improve business modeling.

---

# 65. One-to-One Relationships

Use One-to-One relationships only when two entities always exist together.

Examples

Workspace

↓

WorkspaceSettings

Avoid unnecessary One-to-One relationships when simple embedded objects are sufficient.

---

# 66. One-to-Many Relationships

One-to-Many relationships represent parent-child ownership.

Examples

Workspace

↓

Projects

Project

↓

Sprints

Sprint

↓

Tasks

Parent entities should manage their children through business methods.

---

# 67. Many-to-One Relationships

Many-to-One relationships should represent child references to their owning aggregate.

Examples

Task

↓

Sprint

Sprint

↓

Project

Project

↓

Workspace

Many-to-One relationships are usually the owning side.

---

# 68. Many-to-Many Relationships

Avoid Many-to-Many relationships whenever possible.

Prefer introducing an explicit association entity.

Example

Instead of

User

↔

Workspace

Use

WorkspaceMember

↓

Role

↓

JoinedAt

↓

Permissions

Association entities allow richer domain modeling and future extensibility.

---

# 69. Embedded Value Objects

Use embedded value objects for concepts without independent identity.

Examples

Address

Money

DateRange

TimeZone

GeoLocation

Embedded objects improve cohesion and reduce unnecessary entities.

---

# 70. Collection Types

Choose collection types intentionally.

List

Ordered collections

Set

Unique elements

Map

Key-based lookup

Do not default to List without considering business requirements.

---

# 71. Collection Initialization

Collections should always be initialized.

Correct

Empty List

Empty Set

Empty Map

Never allow relationship collections to remain null.

Initialized collections simplify business logic and reduce NullPointerExceptions.

---

# 72. Managing Relationships

Relationships should be modified through business methods.

Examples

workspace.addProject()

project.removeSprint()

task.assignMember()

Avoid exposing raw collection setters.

Business methods preserve consistency on both sides of bidirectional relationships.

---

# 73. Bidirectional Consistency

When bidirectional relationships exist, both sides should remain synchronized.

Example

workspace.addProject(project)

↓

project.setWorkspace(workspace)

Consistency should be maintained by the entity rather than relying on calling code.

---

# 74. Orphan Removal

Enable orphan removal only when child entities cannot exist independently.

Examples

Workspace

↓

WorkspaceSettings

Project

↓

ProjectConfiguration

Do not enable orphan removal unless the business lifecycle requires it.

---

# 75. Cascade Operations

Cascade operations should be applied deliberately.

Common cascade types include:

PERSIST

MERGE

REMOVE

REFRESH

DETACH

Avoid CascadeType.ALL unless every cascade behavior is genuinely required.

Each cascade option should be chosen intentionally.

---

# 76. Cross-Aggregate References

Aggregates should reference other aggregates by identity whenever practical.

Examples

Task

↓

assignedUserId

instead of

Task

↓

User Entity

Reducing unnecessary aggregate references improves scalability and reduces coupling.

---

# 77. Circular Relationships

Avoid circular dependencies between aggregates.

Incorrect

Workspace

↓

Project

↓

Workspace

Circular object graphs complicate persistence, serialization, and maintenance.

---

# 78. Lazy Loading Philosophy

Relationships should default to lazy loading unless eager loading is clearly justified.

Lazy loading reduces unnecessary database access and improves scalability.

Eager loading should remain exceptional.

---

# 79. Entity Graph Awareness

When multiple related entities are required together, prefer Entity Graphs instead of changing fetch strategies globally.

Entity Graphs allow query-specific optimization while preserving sensible defaults.

---

# 80. Relationship Design Goals

Entity relationships should be:

✓ Intentional

✓ Consistent

✓ Encapsulated

✓ Business-Oriented

✓ Scalable

✓ Easy to Maintain

✓ Performance Conscious

Relationships should model the business domain while minimizing coupling and preserving aggregate boundaries.

---

````md id="4m9k8a"
---

# 81. Aggregate Size Guidelines

Aggregates should remain small, cohesive, and focused.

An aggregate should include only the entities that must remain transactionally consistent.

Avoid creating aggregates that contain:

• Hundreds of child entities

• Deep object graphs

• Multiple unrelated business concepts

Large aggregates:

• Increase memory usage

• Increase transaction duration

• Reduce concurrency

• Complicate persistence

If parts of an aggregate evolve independently, consider splitting them into separate aggregates.

Small aggregates generally provide better scalability and maintainability.

---

# 82. Domain Events Across Aggregates

Aggregates should not directly modify the internal state of other aggregates.

Communication between aggregates should occur through:

✓ Application Services

✓ Domain Events

✓ Application Events

Example

Task Completed

↓

TaskCompletedEvent

↓

Notification Service

↓

Notification Created

Instead of:

Task

↓

Notification Entity

↓

Save Notification

Keeping aggregates isolated reduces coupling and improves scalability.

Repositories persist aggregates.

Services coordinate aggregates.

Events notify aggregates.

---

# 83. Relationship Review Checklist

Before introducing a new relationship, developers should verify the following:

✓ Does this relationship represent a real business concept?

✓ Is the aggregate boundary clearly defined?

✓ Is this relationship truly necessary?

✓ Can it be unidirectional?

✓ Is a Many-to-Many relationship avoidable?

✓ Is lazy loading appropriate?

✓ Are cascade operations justified?

✓ Is orphan removal required?

✓ Is collection initialization handled correctly?

✓ Are both sides synchronized when bidirectional?

✓ Is relationship ownership clearly defined?

✓ Does the relationship preserve aggregate consistency?

Every relationship should improve the domain model rather than simply mirror database structure.

---

# 84. Composition vs Association

SprintForge distinguishes between composition and association.

Composition

A child entity cannot exist independently of its parent.

Examples

Workspace

↓

WorkspaceSettings

Project

↓

ProjectConfiguration

Sprint

↓

SprintGoal

Deleting the parent normally deletes the child.

Composition often uses:

✓ Cascade Operations

✓ Orphan Removal

Association

Associated entities have independent lifecycles.

Examples

Task

↓

Assigned User

Comment

↓

Author

Workspace

↓

Owner

Deleting one entity should not necessarily delete the other.

Choosing the correct relationship type improves domain clarity and prevents accidental data loss.

---

# 85. Referential Integrity

Relationships should maintain both persistence integrity and domain integrity.

Persistence integrity is enforced by:

✓ Foreign Keys

✓ Unique Constraints

✓ Database Constraints

Domain integrity is enforced by:

✓ Aggregate Rules

✓ Entity Invariants

✓ Business Services

Example

Database

↓

Task references existing Sprint

Business Rule

↓

Task may only belong to an active Sprint

The database guarantees structural correctness.

The domain model guarantees business correctness.

Both are necessary for a reliable system.

---

# 86. Relationship Naming Standards

Relationship names should reflect business terminology.

Single-valued relationships should use singular nouns.

Examples

workspace

project

owner

creator

assignee

Collections should use plural nouns.

Examples

projects

tasks

members

comments

attachments

Avoid technical or implementation-oriented names.

Incorrect

projectRef

workspaceObject

taskListData

userCollection

Relationship names should clearly communicate business meaning and remain consistent throughout the codebase.

---

End of Part 4
````
# SprintForge Engineering Standard
# Entity Layer

# Part 5
# Persistence Behavior & Performance

---

# 87. Persistence Philosophy

Entities should be designed for correctness first and performance second.

Performance optimizations should never compromise domain integrity or readability.

Every persistence optimization should have a measurable benefit.

Avoid premature optimization.

---

# 88. Persistence Context

Entities exist within a JPA Persistence Context.

Managed entities are automatically synchronized with the database during transaction commit.

Developers should understand that:

Managed Entity

↓

Field Modified

↓

Transaction Commit

↓

Database Updated

Repositories should rely on JPA's persistence context rather than manually synchronizing entity state.

---

# 89. Dirty Checking

SprintForge relies on JPA Dirty Checking.

Dirty checking automatically detects modifications made to managed entities.

Example

Task

↓

Status Changed

↓

Transaction Commit

↓

UPDATE Executed

Repositories should avoid unnecessary save() calls for already managed entities.

Dirty checking reduces boilerplate and keeps persistence logic simple.

---

# 90. Fetch Strategy Philosophy

Relationships should default to LAZY loading.

Reasons include:

✓ Better scalability

✓ Lower memory usage

✓ Faster initial queries

✓ Reduced object graph loading

EAGER loading should only be used when business requirements consistently require immediate access to related entities.

---

# 91. Lazy Loading

Lazy loading delays loading related entities until they are actually accessed.

Example

Workspace

↓

Projects

↓

Loaded Only When Needed

Lazy loading minimizes unnecessary database work.

Developers should be aware of LazyInitializationException and design service boundaries accordingly.

---

# 92. Eager Loading

Eager loading retrieves related entities immediately.

Use eager loading sparingly.

Acceptable scenarios include:

Small immutable lookup entities

Reference data

Frequently required One-to-One relationships

Avoid eager loading large collections.

---

# 93. N+1 Query Awareness

Poor relationship design can create N+1 query problems.

Example

Load 100 Tasks

↓

Each Task Loads User

↓

101 SQL Queries

Repositories should use:

✓ Entity Graphs

✓ Fetch Joins

✓ Projections

to eliminate unnecessary database queries.

---

# 94. Batch Fetching

Batch fetching reduces repeated database round trips.

Examples

Loading Comments

Loading Attachments

Loading Labels

Configure batch fetching only where measurable performance improvements exist.

---

# 95. Entity Graph Usage

Prefer Entity Graphs for query-specific fetch optimization.

Entity Graphs allow repositories to load only the required relationships without changing global fetch strategies.

Entity Graphs improve performance while preserving sensible defaults.

---

# 96. Query Optimization

Entities should support efficient repository queries.

Recommendations:

✓ Indexed identifiers

✓ Efficient relationships

✓ Small aggregates

✓ Lightweight object graphs

Persistence performance begins with good entity design.

---

# 97. Memory Efficiency

Avoid unnecessarily large entities.

Large object graphs:

Increase memory usage

Increase garbage collection

Increase serialization cost

Increase transaction duration

Entities should load only the information required for the current business operation.

---

# 98. Collection Performance

Collections should remain appropriately sized.

Avoid:

Huge Lists

Deep object graphs

Recursive collections

Large collections should typically be queried through repositories rather than loaded through aggregate navigation.

---

# 99. Read vs Write Optimization

Read-heavy operations often require different optimization strategies than write-heavy operations.

Read Operations

↓

Projections

↓

Pagination

↓

Entity Graphs

Write Operations

↓

Managed Entities

↓

Dirty Checking

↓

Optimistic Locking

Design entities to support both efficiently.

---

# 100. Persistence Lifecycle Cost

Every managed entity consumes resources.

Developers should avoid:

Loading unnecessary entities

Holding entities longer than required

Managing excessively large persistence contexts

Smaller persistence contexts generally improve application performance.

---

# 101. Second-Level Cache Awareness

SprintForge repositories and entities should remain compatible with optional second-level caching.

Caching should improve performance without changing business behavior.

Entity correctness must never depend on cache availability.

---

# 102. Database Round Trips

Minimize unnecessary database interactions.

Prefer:

Optimized repository queries

Batch operations

Projections

Entity Graphs

Reducing database round trips often provides greater performance improvements than optimizing Java code.

---

# 103. SQL Awareness

Developers should understand the SQL generated by JPA.

Review generated SQL when introducing:

Complex relationships

Large queries

Entity Graphs

Specifications

Performance optimization begins with understanding database behavior.

---

# 104. Persistence Testing

Entity persistence behavior should be tested using realistic datasets.

Testing should include:

Relationship loading

Dirty checking

Lazy loading

Cascade behavior

Entity Graphs

Optimistic locking

Performance assumptions should always be verified through testing.

---

# 105. Design Goals

Entity persistence behavior should be:

✓ Efficient

✓ Predictable

✓ Scalable

✓ Database-Friendly

✓ Memory Conscious

✓ Easy to Optimize

✓ Framework-Compliant

Entities should support efficient persistence without sacrificing domain clarity or long-term maintainability.

---

````md id="d2m8qk"
---

# 106. Persistence Context Size Management

The persistence context should remain appropriately sized throughout a transaction.

Every managed entity consumes memory and participates in dirty checking.

Very large persistence contexts may result in:

• Increased memory consumption

• Slower dirty checking

• Longer transaction times

• Reduced application throughput

For bulk operations involving thousands of entities, consider processing data in batches.

Typical batch processing pattern:

Load Batch

↓

Process

↓

Flush

↓

Clear Persistence Context

↓

Load Next Batch

Normal business transactions should remain small and focused.

---

# 107. Read-Only Entity Usage

Not every database query requires fully managed entities.

For read-only operations, prefer:

✓ Read-only transactions

✓ DTO Projections

✓ Interface Projections

✓ Lightweight query models

Avoid modifying entities retrieved solely for reporting or display purposes.

Examples

Dashboard Statistics

Reports

Analytics

Search Results

Activity Feeds

Using projections instead of full entities reduces:

• Memory usage

• Persistence context size

• Dirty checking overhead

Choose entities for business operations.

Choose projections for data retrieval.

---

# 108. Database Vendor Independence

SprintForge aims to remain portable across JPA providers and relational databases.

Entity classes should rely primarily on standard Jakarta Persistence (JPA) annotations.

Avoid database-specific features unless they provide significant measurable benefits.

Examples of vendor-specific features include:

• Proprietary annotations

• Database-specific SQL

• Vendor-specific identifier generators

• Database-specific column definitions

When vendor-specific behavior is unavoidable:

✓ Document the reason

✓ Isolate the dependency

✓ Minimize its impact

Portability should remain the default design goal.

---

# 109. Performance Review Checklist

Before introducing or modifying an entity, developers should verify the following:

✓ Is LAZY loading the default?

✓ Could this relationship introduce N+1 queries?

✓ Would an Entity Graph improve performance?

✓ Are repository queries optimized?

✓ Are large collections avoided?

✓ Is dirty checking sufficient?

✓ Are unnecessary save() calls avoided?

✓ Is pagination available for large datasets?

✓ Has the generated SQL been reviewed?

✓ Are indexes available for commonly queried fields?

✓ Is memory usage reasonable?

✓ Has performance been validated through testing?

Performance should be measured rather than assumed.

---

# 110. Future Evolution

The entity model should support future architectural evolution without requiring fundamental redesign.

Entity design should remain compatible with future capabilities such as:

✓ CQRS

✓ Read Replicas

✓ Second-Level Caching

✓ Event-Driven Architecture

✓ Horizontal Scaling

✓ Database Sharding

✓ Multi-Tenancy

✓ Distributed Systems

Future architectural improvements should build upon a stable domain model rather than replacing it.

A well-designed entity model remains valuable regardless of changes in persistence technology or system architecture.

---

End of Part 5
````
# SprintForge Engineering Standard
# Entity Layer

# Part 6
# Auditing, Versioning & Soft Delete

---

# 111. Auditing Philosophy

Every important business entity should maintain an audit trail.

Auditing answers questions such as:

Who created this?

Who modified this?

When was it created?

When was it last updated?

Auditing improves:

✓ Accountability

✓ Debugging

✓ Compliance

✓ Operational transparency

Audit information should be managed automatically whenever possible.

---

# 112. Standard Audit Fields

Persistent entities should inherit standard audit fields from the Base Entity.

Typical audit fields include:

id

createdAt

createdBy

updatedAt

updatedBy

version

These fields should remain consistent across the entire application.

Business-specific audit fields should remain within the corresponding entity.

---

# 113. Creation Timestamp

Every persistent entity should record its creation time.

Example

Task

↓

Created

↓

createdAt = 2026-07-26T10:15:42Z

Creation timestamps should:

✓ Be assigned once

✓ Never change

✓ Represent the actual creation time

Creation timestamps should be immutable.

---

# 114. Last Updated Timestamp

Entities should record the timestamp of the most recent modification.

updatedAt should automatically change whenever a managed entity is successfully updated.

Manual modification of updatedAt should be avoided.

This field represents the latest persisted change rather than every in-memory modification.

---

# 115. Created By

Where authentication is available, entities should record the creator.

Examples

User ID

Username

System Account

Service Account

The creator should remain immutable after entity creation.

This field establishes ownership and accountability.

---

# 116. Updated By

Entities should record the user or system responsible for the latest modification.

updatedBy should automatically update whenever the entity changes.

Maintaining updatedBy improves:

✓ Traceability

✓ Auditing

✓ Operational diagnostics

---

# 117. Automatic Auditing

Audit fields should be populated automatically through the persistence infrastructure.

Business services should not manually assign:

createdAt

updatedAt

createdBy

updatedBy

Automation ensures consistency and eliminates repetitive code.

---

# 118. Optimistic Locking Philosophy

SprintForge uses optimistic locking to prevent lost updates.

Concurrent modifications may occur when multiple users edit the same entity simultaneously.

Optimistic locking detects conflicting updates before they overwrite each other.

This approach provides good scalability for typical web applications.

---

# 119. Version Field

Entities participating in optimistic locking should include a version field.

Example

version

↓

0

↓

1

↓

2

↓

3

The persistence provider automatically increments the version after successful updates.

Business logic should never manually modify the version.

---

# 120. Concurrent Updates

When concurrent modifications occur:

User A

↓

Reads Task

User B

↓

Reads Task

User A

↓

Updates Task

↓

Version = 2

User B

↓

Attempts Update

↓

Version Conflict

↓

Update Rejected

Rejecting conflicting updates preserves data consistency.

---

# 121. Optimistic Lock Exceptions

Applications should gracefully handle optimistic locking failures.

Possible responses include:

✓ Retry

✓ Refresh entity

✓ Notify user

✓ Merge changes when appropriate

Applications should never silently overwrite conflicting updates.

---

# 122. Soft Delete Philosophy

SprintForge prefers soft deletion for business entities that may require historical retention.

Instead of physically removing data:

Record Exists

↓

Marked Deleted

↓

Hidden From Normal Queries

Soft deletion preserves historical information while preventing accidental data loss.

---

# 123. Soft Delete Fields

Typical soft delete fields include:

deleted

deletedAt

deletedBy

These fields indicate whether an entity has been logically removed.

Soft-deleted entities remain stored in the database.

---

# 124. Deletion Timestamp

When an entity is soft deleted, the deletion time should be recorded.

deletedAt provides:

✓ Historical tracking

✓ Audit support

✓ Recovery information

Deletion timestamps should remain immutable after deletion.

---

# 125. Deleted By

Soft deletion should record who performed the deletion.

Examples

Administrator

Workspace Owner

System Process

Scheduled Cleanup Job

Maintaining deletedBy improves accountability and recovery.

---

# 126. Soft Delete Behavior

Soft-deleted entities should behave as though they no longer exist for normal business operations.

Repositories should exclude deleted entities unless explicitly requested.

Business services should not accidentally process deleted records.

Administrative tools may expose deleted entities for auditing or restoration.

---

# 127. Restore Operations

Some entities may support restoration after soft deletion.

Example

Task

↓

Deleted

↓

Restored

↓

Visible Again

Restoration should:

✓ Preserve original identity

✓ Preserve audit history

✓ Clear deletion markers

Only business-approved entities should support restoration.

---

# 128. Physical Deletion

Physical deletion should be reserved for exceptional situations.

Examples

Temporary data

Expired sessions

Verification tokens

Cache entries

Generated exports

Historical business records should generally not be permanently removed.

---

# 129. Audit Data Integrity

Audit information should accurately reflect historical events.

Audit fields should never be modified merely for convenience.

If historical corrections are necessary, they should occur through controlled administrative processes.

Reliable audit information increases trust in the system.

---

# 130. Design Goals

Auditing, versioning, and soft deletion should be:

✓ Automatic

✓ Consistent

✓ Reliable

✓ Transparent

✓ Non-Intrusive

✓ Framework-Compliant

✓ Business-Friendly

Every important entity should preserve sufficient historical information while supporting safe concurrent updates and recoverable deletion.

---
````md id="n8w4kp"
---

# 131. Audit Exclusions

Not every entity requires comprehensive auditing.

Some entities are purely technical or temporary and provide little value when fully audited.

Examples include:

• Refresh Tokens

• Password Reset Tokens

• Verification Tokens

• Session Records

• Cache Entries

• Temporary Import Data

• One-Time Authentication Codes

For these entities, only the minimum required metadata should be maintained.

Avoid adding unnecessary audit fields simply for consistency.

Auditing should provide business value rather than increasing storage and maintenance costs.

---

# 132. Data Retention Policy

Soft deletion does not imply indefinite data retention.

The application should define clear retention policies for different categories of data.

Examples

Business Records

↓

Retain indefinitely or according to legal requirements

Audit Logs

↓

Retain according to compliance policy

Temporary Files

↓

Automatically purge after expiration

Authentication Tokens

↓

Delete immediately after expiration

Historical information should be retained only as long as it continues to provide operational, legal, or business value.

Data retention policies should be documented and enforced consistently.

---

# 133. Audit Security

Audit fields represent system-managed metadata and must be protected.

Clients should never be allowed to directly modify:

createdAt

createdBy

updatedAt

updatedBy

version

deletedAt

deletedBy

These values should only be managed by the application infrastructure.

REST APIs should ignore or reject attempts to modify audit fields.

Audit information must accurately represent system activity and should never be influenced by client requests.

---

# 134. Recovery & Archival Strategy

SprintForge distinguishes between restoration, archival, and permanent deletion.

Restore

A soft-deleted entity becomes active again while preserving its identity and audit history.

Archive

Inactive records are moved to long-term storage or excluded from operational workloads while remaining available for future reference.

Purge

Records are permanently removed from the system after business and retention requirements have been satisfied.

Example

Task Created

↓

Task Completed

↓

Archived

↓

Retention Period Ends

↓

Purged

Each operation serves a different business purpose and should be implemented intentionally.

---

# 135. Auditing Review Checklist

Before introducing or modifying an entity, developers should verify the following:

✓ Are standard audit fields inherited from the Base Entity?

✓ Is createdAt assigned automatically?

✓ Is createdAt immutable?

✓ Is updatedAt maintained automatically?

✓ Are createdBy and updatedBy populated by the auditing infrastructure?

✓ Is optimistic locking enabled where required?

✓ Is the version field managed automatically?

✓ Are optimistic locking conflicts handled appropriately?

✓ Are soft-deleted entities excluded from normal business queries?

✓ Is restoration supported only where business rules allow?

✓ Is physical deletion limited to temporary or disposable data?

✓ Are audit fields protected from client modification?

✓ Does the entity comply with SprintForge auditing standards?

Consistent auditing practices improve accountability, simplify troubleshooting, and ensure reliable historical records across the entire application.

---

End of Part 6

# SprintForge Engineering Standard
# Entity Layer

# Part 7
# Validation & Business Rules

---

# 136. Validation Philosophy

Validation exists to protect the integrity of the domain model.

An entity should never exist in an invalid business state.

Validation should ensure:

✓ Correctness

✓ Consistency

✓ Predictability

✓ Business Integrity

Validation should occur as close to the source of data as practical.

---

# 137. Layers of Validation

Validation occurs at multiple layers.

Client

↓

Controller

↓

DTO Validation

↓

Service Validation

↓

Entity Invariants

↓

Database Constraints

Each layer has different responsibilities.

Entities represent the final line of defense for business correctness.

---

# 138. Entity Invariants

An invariant is a business rule that must always remain true.

Examples

Sprint End Date

>

Sprint Start Date

Task Completion Date

≥

Creation Date

Workspace

Must Always Have Owner

Entities should enforce their own invariants.

Invalid state should never be allowed.

---

# 139. Constructor Validation

Required business rules should be validated during entity creation.

Example

Workspace

↓

Name Required

↓

Owner Required

↓

Creation Allowed

Entities should never be instantiated with incomplete mandatory data.

Constructors and factory methods should reject invalid input immediately.

---

# 140. Business Method Validation

Business methods should validate every state transition.

Example

task.complete()

Should verify:

Task is not already completed.

Task is not archived.

Completion date is valid.

Business methods should reject invalid operations before modifying state.

---

# 141. Null Safety

Mandatory business fields should never be null.

Examples

Workspace Name

Project Name

Task Title

Sprint Start Date

Nullability should clearly communicate whether information is optional or required.

Avoid representing missing required data using null.

---

# 142. Optional Fields

Optional business information may be absent without violating domain rules.

Examples

Task Description

User Bio

Project Icon

Workspace Banner

Optional fields should remain clearly distinguishable from mandatory fields.

---

# 143. String Validation

Business strings should satisfy domain requirements.

Typical validation includes:

Minimum length

Maximum length

Non-blank

Allowed characters

Normalization

Validation rules should reflect business meaning rather than arbitrary technical limits.

---

# 144. Numeric Validation

Numeric fields should remain within meaningful business ranges.

Examples

Sprint Capacity

>

0

Task Estimate

≥

0

Progress Percentage

0–100

Business rules should define acceptable ranges explicitly.

---

# 145. Date Validation

Dates should remain logically consistent.

Examples

Sprint End

>

Sprint Start

Due Date

≥

Created Date

Reminder

≤

Due Date

Temporal validation prevents inconsistent scheduling.

---

# 146. Enumeration Validation

Enums restrict business state to predefined values.

Examples

TaskStatus

WorkspaceRole

SprintState

Priority

Entities should reject invalid enum transitions through business methods.

---

# 147. Cross-Field Validation

Some rules depend on multiple fields.

Examples

Archived Task

↓

Completion Date Required

Private Workspace

↓

Invitation Required

Recurring Task

↓

Recurrence Rule Required

Cross-field validation belongs inside the business logic responsible for maintaining the entity.

---

# 148. Cross-Entity Rules

Rules involving multiple entities should generally be enforced by services rather than individual entities.

Examples

Workspace Member Limit

Project Quotas

Subscription Validation

Permission Checks

Entity invariants belong inside entities.

Cross-aggregate rules belong in services.

---

# 149. Database Constraints

The database provides structural validation.

Examples

NOT NULL

UNIQUE

FOREIGN KEY

CHECK Constraint

Database constraints complement, but do not replace, domain validation.

Business rules should not rely solely on database exceptions.

---

# 150. Validation Messages

Validation failures should communicate meaningful business information.

Good

Project name cannot be empty.

Sprint end date must be after the start date.

Task cannot be completed twice.

Poor

Validation failed.

Invalid input.

Meaningful messages simplify debugging and improve user experience.

---

# 151. Defensive Programming

Entities should protect themselves against invalid operations.

Business methods should fail immediately when preconditions are violated.

Reject invalid input early rather than attempting to recover from inconsistent state.

Fail-fast behavior improves reliability and simplifies debugging.

---

# 152. Consistency Over Convenience

Convenience methods should never bypass business validation.

Avoid introducing helper methods that allow invalid entity states merely to simplify development.

Every modification should preserve the entity's invariants.

Maintaining domain consistency is more important than reducing code.

---

# 153. Validation Testing

Business validation should be thoroughly tested.

Typical tests include:

✓ Valid entity creation

✓ Missing required fields

✓ Invalid state transitions

✓ Boundary values

✓ Cross-field validation

✓ Business invariant enforcement

Validation logic should remain deterministic and easy to verify through unit tests.

---

# 154. Design Goals

Validation should be:

✓ Predictable

✓ Consistent

✓ Business-Oriented

✓ Fail-Fast

✓ Easy to Understand

✓ Testable

✓ Framework Independent

Every entity should actively protect its own business integrity while collaborating with higher application layers to enforce broader business rules.

---
````md id="v4n9ke"
---

# 155. Bean Validation Standards

SprintForge uses Jakarta Bean Validation to enforce structural validation rules.

Common validation annotations include:

@NotNull

@NotBlank

@NotEmpty

@Size

@Email

@Pattern

@Positive

@PositiveOrZero

@Min

@Max

These annotations should primarily validate:

✓ Required fields

✓ String length

✓ Numeric ranges

✓ Email format

✓ Basic input structure

Bean Validation should not enforce complex business rules.

Example

Good

@NotBlank
private String name;

Business Rule

Workspace owner cannot remove themselves.

This belongs in business logic rather than Bean Validation.

Structural validation and business validation serve different purposes and should remain separate.

---

# 156. Validation Responsibility Matrix

Validation responsibilities should be clearly distributed across application layers.

| Layer | Primary Responsibility |
|--------|------------------------|
| Client | User experience validation, required fields, immediate feedback |
| Controller | Validate incoming DTOs and request format |
| Service | Cross-aggregate rules, permissions, business workflows |
| Entity | Business invariants and valid state transitions |
| Database | Structural integrity, foreign keys, unique constraints |

Each validation rule should have a clear owner.

Avoid duplicating the same validation logic across multiple layers unless redundancy is intentionally required for security or user experience.

A clear separation of responsibilities keeps validation predictable and maintainable.

---

# 157. Domain Exception Strategy

Business validation failures should result in meaningful domain-specific exceptions.

Examples

WorkspaceAlreadyArchivedException

TaskAlreadyCompletedException

InvalidSprintDateException

MemberLimitExceededException

Avoid throwing generic exceptions such as:

RuntimeException

Exception

IllegalStateException

IllegalArgumentException

unless they accurately describe a programming error rather than a business rule violation.

Domain exceptions improve:

✓ Readability

✓ Error handling

✓ API responses

✓ Logging

Business failures should communicate business meaning.

---

# 158. Validation Review Checklist

Before introducing or modifying an entity, developers should verify the following:

✓ Are all required fields validated?

✓ Are entity invariants protected?

✓ Are constructors preventing invalid creation?

✓ Are business methods validating state transitions?

✓ Are optional fields clearly distinguished from required fields?

✓ Are cross-field rules enforced?

✓ Are cross-aggregate rules handled by services?

✓ Are Bean Validation annotations used appropriately?

✓ Are meaningful validation messages provided?

✓ Are domain-specific exceptions used where appropriate?

✓ Are database constraints aligned with business rules?

✓ Have validation rules been covered by unit tests?

Validation should prevent invalid data from entering the domain rather than attempting to correct it later.

---

# 159. Validation Principles Summary

SprintForge follows these core validation principles:

• Validate as early as practical.

• Protect business invariants at all times.

• Never allow an entity to enter an invalid state.

• Keep business rules close to the domain model.

• Keep structural validation separate from business validation.

• Prefer explicit business methods over unrestricted setters.

• Fail fast when business rules are violated.

• Provide meaningful error messages and domain-specific exceptions.

• Use database constraints as a safety net, not as the primary validation mechanism.

• Continuously verify validation behavior through automated tests.

A robust validation strategy ensures that every entity remains trustworthy, predictable, and aligned with the business domain throughout its lifecycle.

---

End of Part 7

# SprintForge Engineering Standard
# Entity Layer

# Part 8
# Entity Anti-Patterns & Code Smells

---

# 160. Purpose

Even well-designed architectures gradually degrade when developers introduce shortcuts, unnecessary complexity, or misplaced responsibilities.

This chapter identifies common entity design mistakes and establishes practices that should be avoided throughout SprintForge.

Recognizing anti-patterns is as important as understanding best practices.

---

# 161. Anemic Domain Model

An anemic domain model consists of entities that contain only fields with getters and setters while placing all business logic inside services.

Example

Task

↓

Fields Only

↓

Service Performs Everything

Problems

• Weak encapsulation

• Scattered business rules

• Poor maintainability

• Difficult testing

Entities should own their own behavior whenever possible.

---

# 162. God Entity

A God Entity attempts to represent multiple business concepts in one class.

Symptoms include:

• Hundreds of fields

• Dozens of relationships

• Excessive methods

• Multiple responsibilities

Large entities become difficult to understand, maintain, and evolve.

Prefer multiple focused entities over one massive object.

---

# 163. Excessive Setters

Public setters allow unrestricted modification of entity state.

Example

task.setStatus(...)

task.setPriority(...)

task.setDueDate(...)

This bypasses business validation.

Prefer explicit business methods.

Example

task.complete()

task.assignUser()

task.changePriority()

Business methods preserve invariants.

---

# 164. Business Logic in Controllers

Controllers should never implement business rules.

Incorrect

Controller

↓

Validate Business Rules

↓

Modify Entity

↓

Persist

Correct

Controller

↓

Service

↓

Entity Business Method

↓

Repository

Controllers coordinate requests.

Entities enforce business rules.

---

# 165. Business Logic in Repositories

Repositories exist solely for persistence.

Repositories should never:

• Calculate business values

• Validate workflows

• Enforce permissions

• Change business state

Repository responsibilities should remain limited to persistence operations.

---

# 166. Bidirectional Relationship Abuse

Not every relationship requires navigation in both directions.

Excessive bidirectional relationships lead to:

• Complex object graphs

• Recursive serialization

• Higher memory usage

• Maintenance difficulties

Prefer unidirectional relationships unless two-way navigation is genuinely required.

---

# 167. Many-to-Many Abuse

Direct Many-to-Many relationships often hide important business concepts.

Incorrect

User

↔

Workspace

Preferred

WorkspaceMember

↓

Role

↓

JoinedAt

↓

Permissions

Association entities provide greater flexibility and better domain modeling.

---

# 168. Eager Loading Everything

Using EAGER fetch indiscriminately causes unnecessary database work.

Problems include:

• Large SQL joins

• Increased memory consumption

• Slow application startup

• Poor scalability

Relationships should default to LAZY loading.

---

# 169. Large Object Graphs

Loading an entire object graph for every request wastes memory and database resources.

Example

Workspace

↓

Projects

↓

Sprints

↓

Tasks

↓

Comments

↓

Attachments

↓

Labels

↓

History

Most business operations require only a small portion of this graph.

Load only what is needed.

---

# 170. Mutable Identity

An entity's identifier must never change.

Incorrect

task.setId(...)

Changing identity creates ambiguity and breaks persistence consistency.

Identity should remain immutable.

---

# 171. Using Entities as DTOs

Entities should never serve as REST request or response models.

Problems

• Overexposure of data

• Tight API coupling

• Serialization issues

• Security risks

Always use dedicated DTOs for external communication.

---

# 172. Leaking Persistence Concerns

Business logic should not depend on persistence implementation details.

Examples

Checking whether an entity is managed

Calling EntityManager from entities

Persistence concerns belong in repositories and infrastructure.

Entities should remain persistence-aware but not persistence-dependent.

---

# 173. Circular Dependencies

Entities should avoid circular object references whenever possible.

Example

Workspace

↓

Project

↓

Workspace

↓

Project

↓

...

Circular dependencies complicate:

• Serialization

• Debugging

• Persistence

• Memory management

Keep relationship graphs simple and intentional.

---

# 174. Massive Constructors

Constructors with numerous parameters are difficult to read and maintain.

Example

Task(

title,

description,

priority,

dueDate,

status,

estimate,

owner,

reviewer,

...)

Prefer:

✓ Factory methods

✓ Builders

✓ Value Objects

✓ Smaller aggregates

---

# 175. Primitive Obsession

Avoid representing business concepts using raw primitive types when richer models provide better clarity.

Examples

String

↓

Email Address

String

↓

Workspace Slug

int

↓

Story Points

String

↓

Phone Number

Value Objects communicate business meaning more effectively than primitive types.

---

# 176. Duplicate Business Rules

The same business rule should not be implemented in multiple places.

Incorrect

Controller validates

↓

Service validates

↓

Entity validates

↓

Repository validates

Determine the appropriate layer and keep the rule there.

Duplicated validation increases maintenance costs and inconsistency.

---

# 177. Catching Business Errors Too Late

Do not rely on database exceptions to detect business problems.

Incorrect

Insert

↓

Database Constraint Failure

↓

Business Error

Correct

Business Validation

↓

Persist

↓

Success

Validate before persistence whenever practical.

---

# 178. Overusing Inheritance

Inheritance should model genuine "is-a" relationships.

Avoid deep inheritance hierarchies.

Prefer:

Composition

↓

Interfaces

↓

Value Objects

Inheritance increases coupling and reduces flexibility.

---

# 179. Ignoring Aggregate Boundaries

Services should not modify child entities belonging to another aggregate without going through the Aggregate Root.

Incorrect

Repository

↓

Load Child

↓

Modify Directly

Correct

Aggregate Root

↓

Business Method

↓

Consistency Preserved

Aggregate boundaries protect business invariants.

---

# 180. Design Goals

SprintForge entities should avoid:

✗ God Objects

✗ Anemic Models

✗ Mutable Identity

✗ Excessive Setters

✗ Large Object Graphs

✗ Unnecessary Relationships

✗ Business Logic Leakage

✗ Persistence Leakage

✗ Duplicate Validation

✗ Poor Aggregate Design

Instead, entities should remain:

✓ Focused

✓ Cohesive

✓ Encapsulated

✓ Business-Oriented

✓ Maintainable

✓ Scalable

✓ Easy to Test

Recognizing and avoiding these anti-patterns ensures that the entity layer remains clean, expressive, and resilient as SprintForge evolves.

---
````md id="x8n2vf"
---

# 181. Premature Optimization

Optimization should always be driven by measurable evidence rather than assumptions.

Avoid introducing unnecessary complexity solely for hypothetical performance improvements.

Examples include:

• Replacing simple object models with complex caching mechanisms

• Introducing custom persistence logic without profiling

• Optimizing rarely executed code paths

• Sacrificing readability for minor performance gains

SprintForge follows the principle:

Make it correct.

↓

Make it clean.

↓

Measure.

↓

Optimize where necessary.

Maintainable code almost always outlives premature optimizations.

---

# 182. Code Smell Detection Checklist

During code reviews, developers should evaluate entities for common design smells.

Warning signs include:

✓ Excessive fields

✓ Too many relationships

✓ Large constructors

✓ Numerous public setters

✓ Generic method names

✓ Business logic scattered across services

✓ Mutable identifiers

✓ Deep inheritance hierarchies

✓ Excessive bidirectional relationships

✓ Frequent use of Many-to-Many mappings

✓ Large object graphs

✓ Repeated validation logic

✓ Framework-specific business code

✓ Frequent null checks

✓ Excessive primitive types instead of Value Objects

✓ Low cohesion

If multiple code smells appear within the same entity, consider refactoring before introducing additional functionality.

Code smells should be addressed early before they become architectural problems.

---

# 183. Refactoring Guidelines

Entity design should evolve incrementally while preserving business correctness.

Common refactoring strategies include:

Extract Value Objects

Example

Address

Money

DateRange

EmailAddress

Split Large Aggregates

Separate unrelated business concepts into smaller aggregates with clear boundaries.

Replace Generic Setters

Convert unrestricted setters into meaningful business methods.

Extract Association Entities

Replace Many-to-Many relationships with explicit business entities.

Move Misplaced Logic

Relocate business rules from controllers or repositories into the appropriate entities or domain services.

Introduce Factory Methods

Simplify complex construction logic while ensuring required invariants are enforced.

Every refactoring should preserve existing business behavior while improving readability, maintainability, and cohesion.

---

# 184. Legacy Entity Modernization

Not every existing entity will immediately conform to SprintForge standards.

Legacy entities should be modernized gradually rather than rewritten entirely.

Recommended modernization approach:

Identify Code Smells

↓

Prioritize High-Risk Areas

↓

Extract Business Logic

↓

Reduce Coupling

↓

Improve Relationships

↓

Strengthen Validation

↓

Introduce Tests

↓

Repeat Incrementally

Large-scale rewrites introduce unnecessary risk.

Continuous incremental improvement is generally safer, easier to review, and simpler to validate.

The objective is steady architectural improvement rather than immediate perfection.

---

# 185. Anti-Pattern Summary

SprintForge entities should model the business domain—not the database, framework, or API.

Well-designed entities exhibit the following characteristics:

✓ Single Responsibility

✓ Stable Identity

✓ Strong Encapsulation

✓ Protected Business Invariants

✓ Clear Aggregate Boundaries

✓ Meaningful Business Methods

✓ Minimal Coupling

✓ Small, Cohesive Relationships

✓ Predictable Persistence Behavior

✓ Framework Independence where practical

Developers should continually ask:

• Does this entity represent a single business concept?

• Are its responsibilities clearly defined?

• Can it protect its own invariants?

• Is its behavior expressed through meaningful business methods?

• Does it collaborate cleanly with other aggregates?

If the answer to these questions is consistently "yes," the entity is likely aligned with SprintForge's architectural principles.

Clean entities form the foundation of a clean domain model. By avoiding common anti-patterns and continuously refining the model, SprintForge remains easier to understand, maintain, test, and evolve as the system grows.

---

End of Part 8

# SprintForge Engineering Standard
# Entity Layer

# Part 9
# Reference Templates & Implementation Blueprints

---

# 186. Purpose

This chapter provides standardized implementation templates for SprintForge entities.

These templates serve as reference implementations for:

✓ New Features

✓ Code Reviews

✓ Onboarding Developers

✓ AI Coding Assistants

✓ Architectural Consistency

Developers should adapt these templates to business requirements while preserving the architectural principles established throughout this document.

---

# 187. Standard Aggregate Root Template

Every Aggregate Root should generally include:

• Stable UUID identifier

• Business attributes

• Relationships

• Audit fields (via Base Entity)

• Version field

• Business methods

• Protected constructors

• Static factory methods when appropriate

Typical structure:

Identity

↓

Business Fields

↓

Relationships

↓

Business Methods

↓

Infrastructure

Aggregate roots coordinate modifications within the aggregate.

---

# 188. Standard Child Entity Template

Child entities belong to an Aggregate Root and should never exist independently.

Typical characteristics:

✓ Parent reference

✓ Business fields

✓ Internal validation

✓ Limited visibility

✓ Lifecycle controlled by parent

Example

Project

↓

Sprint

↓

Task

↓

Checklist Item

Child entities should expose only the behavior required by the aggregate.

---

# 189. Standard Value Object Template

Value Objects should be:

✓ Immutable

✓ Equality based on values

✓ Side-effect free

✓ Self-validating

Examples

Address

Money

DateRange

EmailAddress

WorkspaceSlug

PriorityLevel

Value Objects improve readability while reducing primitive obsession.

---

# 190. Standard Association Entity Template

When a relationship contains business information, introduce an Association Entity.

Example

Workspace

↓

WorkspaceMember

↓

User

WorkspaceMember contains:

Role

JoinedAt

Permissions

InvitationStatus

Association entities often become important business concepts over time.

---

# 191. Business Method Blueprint

Business methods should follow a consistent structure.

Recommended sequence:

Validate Preconditions

↓

Enforce Business Rules

↓

Modify State

↓

Maintain Relationships

↓

Return Result (if applicable)

Example

task.complete()

↓

Validate

↓

Set Status

↓

Record Completion Time

↓

Increment Version (handled automatically)

Business methods should remain focused and expressive.

---

# 192. Factory Method Blueprint

Complex entity creation should occur through factory methods.

Typical flow:

Validate Input

↓

Initialize Required Fields

↓

Assign Identity

↓

Initialize Collections

↓

Return Entity

Factory methods centralize creation logic and reduce invalid initialization.

---

# 193. Relationship Management Blueprint

Relationship management should occur through dedicated helper methods.

Example

workspace.addProject(project)

↓

Validate

↓

Set Parent

↓

Update Collection

↓

Maintain Bidirectional Consistency

Relationship synchronization should never depend on calling code.

---

# 194. Validation Blueprint

Every business operation should follow a consistent validation pattern.

Input

↓

Structural Validation

↓

Business Validation

↓

State Transition Validation

↓

Persist

Validation should occur before state modification.

---

# 195. Soft Delete Blueprint

Entities supporting logical deletion should follow a predictable workflow.

Active

↓

Soft Delete

↓

Set Deleted Flag

↓

Set DeletedAt

↓

Set DeletedBy

↓

Hide From Queries

↓

Optional Restore

Business history should remain intact throughout the lifecycle.

---

# 196. Audit Blueprint

Audit information should be managed automatically.

Entity Created

↓

createdAt

↓

createdBy

Entity Updated

↓

updatedAt

↓

updatedBy

Audit metadata should never require manual maintenance by business services.

---

# 197. Optimistic Locking Blueprint

Concurrent modification should follow this pattern.

Load Entity

↓

Read Version

↓

Modify

↓

Commit

↓

Version Increment

If another transaction modifies the entity first:

↓

Optimistic Lock Exception

↓

Retry or Notify User

This prevents silent overwriting of data.

---

# 198. Repository Interaction Blueprint

Entity interaction should follow the standard application flow.

Controller

↓

DTO

↓

Service

↓

Repository

↓

Entity

↓

Database

Entities should never directly interact with repositories.

Repositories should never contain business logic.

---

# 199. Aggregate Modification Blueprint

External components should modify aggregates only through their Aggregate Root.

Correct

Workspace

↓

addProject()

↓

Project Created

Incorrect

Repository

↓

Save Child Directly

↓

Bypass Aggregate Rules

Aggregate Roots preserve consistency.

---

# 200. Entity Lifecycle Blueprint

Typical entity lifecycle:

Create

↓

Validate

↓

Persist

↓

Business Updates

↓

Soft Delete

↓

Archive (Optional)

↓

Purge (Exceptional)

Entity identity remains stable throughout every stage.

---

# 201. Testing Blueprint

Every entity should be accompanied by comprehensive unit tests.

Recommended test categories:

✓ Constructor validation

✓ Factory methods

✓ Business methods

✓ State transitions

✓ Relationship management

✓ Validation failures

✓ Soft delete

✓ Restore

✓ Equality

✓ Optimistic locking behavior (integration tests)

Tests should verify business behavior rather than implementation details.

---

# 202. Documentation Blueprint

Every entity should document:

Purpose

Business Meaning

Aggregate Root

Relationships

Invariants

Lifecycle

Special Business Rules

Good documentation improves maintainability and accelerates onboarding.

---

# 203. Design Goals

Reference implementations should be:

✓ Consistent

✓ Reusable

✓ Framework-Compliant

✓ Easy to Understand

✓ Easy to Extend

✓ Business-Oriented

✓ Production Ready

These templates establish a common implementation style across SprintForge, ensuring that every entity follows the same architectural principles while remaining adaptable to evolving business requirements.

---
You're right—I think you meant the **remaining sections of Part 9** (since we already completed Part 6). Here are **Sections 204–208** to complete **Part 9 – Reference Templates & Implementation Blueprints**.

````md id="m7r2ka"
---

# 204. Entity Skeleton Example

The following structure represents the recommended organization of a SprintForge entity.

```
@Entity
@Table(name = "example_entity")
public class ExampleEntity extends BaseEntity {

    // =========================================================
    // Identity
    // =========================================================

    @Id
    private UUID id;

    // =========================================================
    // Business Fields
    // =========================================================

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    // =========================================================
    // Relationships
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    private ParentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<ChildEntity> children = new ArrayList<>();

    // =========================================================
    // Constructors
    // =========================================================

    protected ExampleEntity() {
        // Required by JPA
    }

    private ExampleEntity(...) {
        ...
    }

    // =========================================================
    // Factory Methods
    // =========================================================

    public static ExampleEntity create(...) {
        ...
    }

    // =========================================================
    // Business Methods
    // =========================================================

    public void activate() {
        ...
    }

    public void archive() {
        ...
    }

    // =========================================================
    // Relationship Helpers
    // =========================================================

    public void addChild(...) {
        ...
    }

    public void removeChild(...) {
        ...
    }

    // =========================================================
    // Internal Validation
    // =========================================================

    private void validateState() {
        ...
    }
}
```

Recommended ordering:

Identity

↓

Business Fields

↓

Relationships

↓

Constructors

↓

Factory Methods

↓

Business Methods

↓

Relationship Helpers

↓

Private Validation Helpers

A consistent class layout improves readability and accelerates onboarding.

---

# 205. Annotation Reference

SprintForge primarily relies on standard Jakarta Persistence annotations.

| Annotation | Purpose | Recommended Usage |
|------------|----------|-------------------|
| @Entity | Marks a persistent entity | Every persistent domain entity |
| @Table | Configures table mapping | When custom table names are required |
| @Id | Primary key | Every entity |
| @GeneratedValue | Identifier generation | When using generated identifiers |
| @Version | Optimistic locking | Mutable business entities |
| @Column | Column configuration | Length, uniqueness, nullability, etc. |
| @Enumerated(EnumType.STRING) | Enum persistence | Always prefer STRING over ORDINAL |
| @Embedded | Embed Value Objects | Value Objects without identity |
| @Embeddable | Defines embedded Value Object | Immutable business concepts |
| @OneToOne | One-to-One relationship | Only when lifecycle is tightly coupled |
| @OneToMany | Parent-child relationship | Aggregate ownership |
| @ManyToOne | Child-to-parent reference | Most common relationship |
| @ManyToMany | Many-to-Many relationship | Discouraged; prefer association entities |
| @JoinColumn | Foreign key configuration | Owning side of relationships |
| @MappedSuperclass | Shared infrastructure fields | BaseEntity and similar abstractions |

Prefer standard JPA annotations over vendor-specific extensions whenever practical.

---

# 206. Blueprint Selection Guide

Choose the appropriate blueprint based on the business concept being modeled.

| Business Concept | Recommended Blueprint |
|------------------|-----------------------|
| Independent business object with its own lifecycle | Aggregate Root |
| Exists only as part of another entity | Child Entity |
| No independent identity | Value Object |
| Relationship containing business data | Association Entity |

Examples

Workspace

↓

Aggregate Root

Project

↓

Aggregate Root

Sprint

↓

Aggregate Root

Task

↓

Aggregate Root

Checklist Item

↓

Child Entity

Money

↓

Value Object

DateRange

↓

Value Object

WorkspaceMember

↓

Association Entity

Choosing the correct blueprint early leads to cleaner domain models and fewer refactoring efforts later.

---

# 207. Entity Creation Checklist

Before introducing a new entity into SprintForge, developers should complete the following checklist.

### Business Design

✓ Does the entity represent a real business concept?

✓ Is the aggregate boundary clearly defined?

✓ Is a new entity actually required?

### Identity

✓ Stable UUID

✓ Immutable identity

✓ Appropriate business identifiers

### Structure

✓ Correct package

✓ Proper naming

✓ Appropriate relationships

✓ Collections initialized

### Behavior

✓ Business methods implemented

✓ Invariants protected

✓ Relationship helpers added

✓ Constructors restricted

### Persistence

✓ Audit fields inherited

✓ Version field configured

✓ Fetch strategy reviewed

✓ Cascade behavior verified

### Validation

✓ Required fields validated

✓ Cross-field validation implemented

✓ State transitions protected

### Testing

✓ Unit tests

✓ Relationship tests

✓ Validation tests

✓ Persistence tests

### Documentation

✓ Business purpose documented

✓ Aggregate ownership documented

✓ Special rules documented

Following this checklist ensures consistency across the entire domain model.

---

# 208. Reference Implementation Summary

The templates and blueprints presented in this chapter establish the standard implementation style for SprintForge entities.

These templates are intended to:

✓ Promote consistency

✓ Reduce onboarding time

✓ Simplify code reviews

✓ Improve AI-assisted development

✓ Encourage maintainable domain models

They are reference standards rather than rigid boilerplate.

Developers should adapt each blueprint to the specific business requirements while preserving the architectural principles established throughout this handbook.

Whenever a new entity is introduced, the preferred workflow is:

Understand the Business Concept

↓

Select the Appropriate Blueprint

↓

Implement the Entity

↓

Protect Business Invariants

↓

Add Relationships

↓

Implement Validation

↓

Write Tests

↓

Document the Entity

By following these reference implementations, SprintForge maintains a consistent, expressive, and production-ready domain model that remains easy to understand, extend, and evolve over time.

---

End of Part 9

# SprintForge Engineering Standard
# Entity Layer

# Part 10
# Entity Governance & Final Principles

---

# 209. Purpose

The Entity Layer is the foundation of the SprintForge domain model.

Every service, repository, controller, DTO, and business workflow ultimately depends on the correctness of the entity model.

This chapter establishes the governance principles that ensure the entity layer remains clean, consistent, and maintainable throughout the lifetime of the project.

---

# 210. Entity Governance Philosophy

Entity design is an architectural responsibility rather than an implementation detail.

Every entity introduced into SprintForge becomes part of the long-term domain model.

Therefore, entity design decisions should prioritize:

✓ Business correctness

✓ Consistency

✓ Maintainability

✓ Simplicity

✓ Long-term evolution

Architectural quality should always take precedence over short-term convenience.

---

# 211. Architectural Consistency

All entities should follow the same architectural conventions.

Consistency includes:

• Naming conventions

• Package organization

• Identity strategy

• Relationship modeling

• Validation approach

• Auditing

• Versioning

• Business methods

Consistency reduces cognitive load and makes the codebase easier to understand.

---

# 212. Entity Review Process

Every new entity should undergo architectural review before being merged.

The review should verify:

✓ Business purpose

✓ Aggregate boundaries

✓ Relationships

✓ Identity

✓ Validation

✓ Persistence behavior

✓ Testing

✓ Documentation

Architecture reviews help prevent long-term design problems before they enter the codebase.

---

# 213. Backward Compatibility

Entity changes should preserve compatibility whenever practical.

Avoid:

• Renaming fields without migration

• Breaking relationships

• Removing identifiers

• Changing aggregate boundaries unnecessarily

Schema evolution should occur through controlled database migrations.

Business continuity is more important than rapid structural changes.

---

# 214. Domain Evolution

The business domain will evolve over time.

Entity design should accommodate:

New Features

↓

Additional Business Rules

↓

New Relationships

↓

Expanded Workflows

without requiring unnecessary rewrites.

Well-designed entities evolve through extension rather than replacement.

---

# 215. Refactoring Policy

Entity refactoring is encouraged when it improves:

✓ Readability

✓ Cohesion

✓ Encapsulation

✓ Aggregate Design

✓ Maintainability

Refactoring should preserve existing business behavior.

Large architectural improvements should be implemented incrementally rather than through disruptive rewrites.

---

# 216. Documentation Requirements

Every important entity should include documentation describing:

Purpose

Business Meaning

Aggregate Ownership

Relationships

Business Invariants

Lifecycle

Special Validation Rules

Documentation should evolve alongside the implementation.

Outdated documentation should be treated as a defect.

---

# 217. Testing Requirements

Every entity should have automated tests covering:

✓ Construction

✓ Factory Methods

✓ Business Methods

✓ State Transitions

✓ Validation

✓ Relationship Management

✓ Equality

✓ Persistence Behavior

Tests should verify business behavior rather than internal implementation.

---

# 218. Framework Independence

Business entities should remain focused on the domain rather than framework-specific behavior.

Entities should avoid:

• HTTP concerns

• Controller logic

• Repository logic

• Service dependencies

• UI concerns

Frameworks may change.

The business domain should remain stable.

---

# 219. Simplicity First

Prefer the simplest design that correctly models the business.

Avoid introducing complexity without a clear business justification.

Examples include:

• Unnecessary inheritance

• Overly generic abstractions

• Premature optimization

• Excessive configuration

Simple designs are easier to understand, maintain, and evolve.

---

# 220. Long-Term Maintainability

SprintForge is intended to evolve over many years.

Entity design should therefore emphasize:

✓ Readability

✓ Explicitness

✓ Stability

✓ Testability

✓ Predictability

Future developers should understand the model without extensive historical knowledge.

---

# 221. AI-Assisted Development

AI coding assistants should follow the standards defined throughout this handbook.

Generated entities should:

✓ Respect aggregate boundaries

✓ Preserve business invariants

✓ Use approved naming conventions

✓ Follow validation standards

✓ Apply appropriate relationships

✓ Maintain architectural consistency

AI-generated code should be reviewed using the same standards as manually written code.

---

# 222. Decision Priority

When multiple design choices appear reasonable, prefer the option that best preserves:

Business Correctness

↓

Domain Clarity

↓

Maintainability

↓

Consistency

↓

Performance

↓

Convenience

Performance optimizations should never compromise the integrity of the domain model.

---

# 223. Continuous Improvement

Entity standards should evolve alongside the project.

When new architectural patterns emerge:

Evaluate

↓

Discuss

↓

Document

↓

Standardize

↓

Apply Consistently

Architectural improvements should be intentional and well documented.

---

# 224. Design Goals

The SprintForge Entity Layer should remain:

✓ Business-Centric

✓ Consistent

✓ Encapsulated

✓ Cohesive

✓ Maintainable

✓ Testable

✓ Scalable

✓ Performance Conscious

✓ Framework Compliant

✓ Easy to Understand

Every entity should accurately model the business domain while remaining simple to maintain, evolve, and extend.

---

# 225. Final Principles

SprintForge follows the following core principles for entity design:

• Model the business, not the database.

• Protect business invariants.

• Keep aggregate boundaries clear.

• Prefer explicit business methods over unrestricted setters.

• Maintain stable identity throughout the entity lifecycle.

• Separate business concerns from infrastructure concerns.

• Favor composition over unnecessary inheritance.

• Keep entities cohesive and focused.

• Validate before modifying state.

• Preserve audit history and data integrity.

• Optimize only after measurement.

• Continuously improve the model without sacrificing stability.

These principles provide the foundation for a robust, expressive, and maintainable domain model capable of supporting SprintForge as it grows in complexity and scale.

---

````md id="q7m4dx"
---

# 226. Entity Decision Tree

Before introducing a new domain model, developers should determine which type of object best represents the business concept.

Follow this decision process:

```
Does it have an independent business identity?

        │
   ┌────┴────┐
   │         │
  Yes       No
   │         │
Aggregate   Is it defined only by its values?
Root             │
                 ├──── Yes → Value Object
                 │
                 └──── No
                      │
        Does it exist only within another Aggregate?
                      │
                 ├──── Yes → Child Entity
                 │
                 └──── No
                      │
Does it primarily represent a relationship with business data?
                      │
                 ├──── Yes → Association Entity
                 │
                 └──── Re-evaluate the domain model
```

Choosing the correct type of domain object early significantly reduces future refactoring effort.

---

# 227. Entity Compliance Checklist

Every entity introduced into SprintForge should satisfy the following requirements.

### Identity

✓ Stable UUID

✓ Immutable identifier

✓ Appropriate business identifiers

---

### Structure

✓ Single business responsibility

✓ Clear aggregate ownership

✓ Proper package organization

✓ Consistent naming

---

### Relationships

✓ Aggregate boundaries respected

✓ LAZY loading by default

✓ Bidirectional relationships justified

✓ Many-to-Many avoided unless unavoidable

✓ Collections initialized

---

### Behavior

✓ Business methods implemented

✓ Entity invariants protected

✓ State transitions validated

✓ Relationship helper methods included

---

### Persistence

✓ Audit fields inherited

✓ Version field configured where applicable

✓ Appropriate cascade behavior

✓ Soft delete implemented when required

---

### Validation

✓ Required fields validated

✓ Cross-field validation implemented

✓ Business exceptions used appropriately

✓ Database constraints aligned

---

### Quality

✓ Unit tests written

✓ Documentation completed

✓ Code review completed

✓ Conforms to SprintForge standards

Entities should satisfy this checklist before being merged into the main branch.

---

# 228. Common Review Questions

During architectural and code reviews, reviewers should consider the following questions.

### Business Modeling

• Does this entity represent a genuine business concept?

• Is a new entity actually necessary?

• Could this concept be modeled as a Value Object instead?

---

### Identity

• Is the identifier stable?

• Is identity immutable?

• Are business identifiers separate from entity identity?

---

### Aggregate Design

• Is the Aggregate Root clearly defined?

• Are aggregate boundaries respected?

• Are child entities appropriately encapsulated?

---

### Relationships

• Is this relationship necessary?

• Can it be unidirectional?

• Is Many-to-Many avoidable?

• Are cascade operations justified?

---

### Business Logic

• Are invariants protected?

• Are business methods expressive?

• Are unrestricted setters avoided?

• Is validation performed before state changes?

---

### Persistence

• Is LAZY loading the default?

• Could this design create N+1 queries?

• Are collections reasonably sized?

---

### Maintainability

• Is the entity cohesive?

• Does it have a single responsibility?

• Is the code easy to understand?

• Will future developers easily extend it?

Review questions encourage thoughtful design discussions rather than mechanical code inspection.

---

# 229. Evolution Policy

The SprintForge entity model is expected to evolve throughout the lifetime of the project.

Architectural evolution should follow these principles:

### Preserve Stability

Avoid unnecessary breaking changes.

Favor additive evolution over disruptive redesign.

---

### Document Architectural Decisions

Significant entity design changes should be documented through Architecture Decision Records (ADRs).

Examples include:

• New aggregate boundaries

• Identity strategy changes

• Auditing strategy modifications

• Relationship modeling changes

---

### Refactor Incrementally

Prefer continuous, incremental improvements rather than large-scale rewrites.

Small refactorings are:

✓ Easier to review

✓ Easier to test

✓ Lower risk

✓ Easier to deploy

---

### Maintain Backward Compatibility

Where practical:

• Preserve database compatibility

• Provide migration scripts

• Deprecate before removal

• Avoid unnecessary API changes

---

### Keep Documentation Current

Whenever entity standards change:

Update Documentation

↓

Update Templates

↓

Update Examples

↓

Update Checklists

↓

Communicate Changes

Documentation should always reflect the current architecture.

---

# 230. Closing Statement

The Entity Layer is the foundation upon which SprintForge is built.

Repositories persist entities.

Services coordinate entities.

Controllers expose entities through DTOs.

Every business workflow ultimately depends upon the correctness of the domain model.

A well-designed entity model provides:

✓ Clear business representation

✓ Strong encapsulation

✓ Reliable persistence

✓ Safe concurrency

✓ Consistent validation

✓ Long-term maintainability

SprintForge therefore adopts the following enduring philosophy:

Model the business before the database.

Protect invariants before optimizing performance.

Prefer explicit business behavior over unrestricted data mutation.

Keep aggregates small, cohesive, and well-defined.

Continuously improve the model while preserving architectural consistency.

By adhering to these principles, the SprintForge Entity Layer remains understandable, extensible, and resilient as the application grows in size, complexity, and functionality.

The standards defined throughout this document are intended to guide both human developers and AI coding assistants toward a shared goal:

Building a clean, expressive, and maintainable domain model that accurately represents the business while remaining practical for long-term development.

---

**End of Part 10**

**End of `entities.md`**
````