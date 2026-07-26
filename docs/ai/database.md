
# SprintForge Engineering Standard
# Database Standards

# Part 1 – Database Philosophy & Architecture

## 1. Purpose

The database is SprintForge's system of record.

Its primary responsibilities are to:

- Persist business data
- Maintain integrity
- Enforce consistency
- Support efficient querying
- Preserve historical information where required

The database should never become a dumping ground for application logic.

---

## 2. Philosophy

SprintForge follows these principles:

- Database-first integrity
- Application-level business logic
- Strong normalization
- Explicit relationships
- Predictable schema evolution

The database stores facts. The application implements behavior.

---

## 3. Goals

SprintForge databases should be:

✓ Reliable

✓ Consistent

✓ Scalable

✓ Secure

✓ Maintainable

✓ Performant

✓ Easy to evolve

---

## 4. Technology Stack

SprintForge standardizes on:

```text id="db001"
PostgreSQL

↓

Spring Data JPA

↓

Hibernate ORM
```

Other persistence technologies may be introduced for specialized use cases, but PostgreSQL remains the primary relational datastore.

---

## 5. Responsibilities

The database is responsible for:

- Data persistence
- Referential integrity
- Constraints
- Indexes
- Transactions
- Historical consistency

---

## 6. Responsibilities That Do NOT Belong

The database should not contain:

✗ Business workflows

✗ Complex application rules

✗ Authentication logic

✗ Authorization logic

✗ UI-specific data formatting

These belong in the service layer.

---

## 7. ACID Principles

SprintForge relies on ACID guarantees.

Transactions should ensure:

- Atomicity
- Consistency
- Isolation
- Durability

Application code should be written with these guarantees in mind.

---

## 8. Single Source of Truth

Every business entity should have one authoritative location for its data.

Avoid duplicated data unless justified for performance or reporting.

---

## 9. Data Integrity

Integrity should be enforced through:

- Foreign keys
- Constraints
- Validation
- Transactions

Never rely solely on frontend validation.

---

## 10. Database Principles

SprintForge databases should emphasize:

- Explicit schemas
- Predictable migrations
- Strong typing
- Referential integrity
- Performance-aware design

---

## 11. Philosophy Checklist

✓ ACID

✓ Normalized

✓ Consistent

✓ Reliable

✓ Maintainable

---

# Part 2 – Schema Design Standards

## 12. Purpose

A consistent schema improves readability, onboarding, and long-term maintenance.

Developers should immediately understand a table's purpose.

---

## 13. Naming Conventions

Tables:

```text id="db002"
users

projects

tasks

task_comments
```

Use:

- lowercase
- snake_case
- plural nouns

---

## 14. Column Naming

Columns should also use snake_case.

Examples:

```text id="db003"
created_at

updated_at

workspace_id

display_name
```

Avoid abbreviations unless universally understood.

---

## 15. Primary Keys

All tables should use a single primary key.

Standard:

```text id="db004"
id
```

Prefer database-generated identifiers or UUIDs depending on system requirements.

---

## 16. Foreign Keys

Foreign keys should clearly indicate the referenced entity.

Example:

```text id="db005"
workspace_id

project_id

owner_id
```

Avoid ambiguous names such as:

```text id="db006"
parent
```

---

## 17. Data Types

Choose the narrowest appropriate type.

Examples:

- UUID
- BIGINT
- VARCHAR
- BOOLEAN
- TIMESTAMP
- DATE
- JSONB (only when justified)

Avoid storing structured relational data inside JSON.

---

## 18. Nullable Columns

Fields should only be nullable when business rules permit missing values.

Every nullable column should have a clear justification.

---

## 19. Default Values

Database defaults may be used for:

- timestamps
- boolean flags
- counters

Avoid embedding business logic into database defaults.

---

## 20. Constraints

Use constraints whenever possible.

Examples:

- UNIQUE
- CHECK
- FOREIGN KEY
- NOT NULL

Database constraints protect against invalid data regardless of the client.

---

## 21. Schema Checklist

✓ snake_case

✓ plural tables

✓ descriptive columns

✓ constraints

✓ foreign keys

---

# Part 3 – JPA & Entity Mapping Standards

## 22. Purpose

JPA maps Java objects to relational tables.

Mappings should be explicit, predictable, and optimized for maintainability.

---

## 23. Entity Mapping

Every entity should map to one table.

Example:

```text id="db007"
Task

↓

tasks
```

Avoid multiple entities mapping to the same table unless there is a compelling architectural reason.

---

## 24. Table Annotation

Explicitly declare table names.

Example:

```java id="db008"
@Table(name = "tasks")
```

Avoid relying solely on implicit naming strategies.

---

## 25. Column Mapping

Explicit column mappings improve clarity.

Example:

```java id="db009"
@Column(name = "created_at")
```

This protects against future naming strategy changes.

---

## 26. Relationship Ownership

Define the owning side explicitly.

Understand which entity manages the foreign key.

Incorrect ownership often causes unexpected updates.

---

## 27. Lazy Loading

Default to:

```text id="db010"
LAZY
```

for associations.

Eager loading should be used only when there is a demonstrated need.

---

## 28. Cascading

Use cascading sparingly.

Typical candidates:

- Parent-child lifecycle
- Aggregate roots

Avoid blanket `CascadeType.ALL` without understanding its effects.

---

## 29. Orphan Removal

Use orphan removal only when child entities should not exist independently.

Deleting a parent should not accidentally remove unrelated data.

---

## 30. Bidirectional Relationships

Only create bidirectional mappings when both navigation directions are genuinely required.

Unnecessary bidirectional relationships increase complexity.

---

## 31. Mapping Checklist

✓ Explicit mappings

✓ LAZY loading

✓ Controlled cascading

✓ Clear ownership

✓ Minimal bidirectional relationships

---

# Part 4 – Relationships & Normalization

## 32. Purpose

Proper normalization reduces duplication while maintaining consistency.

Relationships should accurately reflect business rules.

---

## 33. One-to-One

Use one-to-one relationships only when both entities have the same lifecycle or represent distinct aspects of a single concept.

They should be relatively uncommon.

---

## 34. One-to-Many

Most SprintForge relationships will be one-to-many.

Example:

```text id="db011"
Workspace

↓

Projects
```

The many-side owns the foreign key.

---

## 35. Many-to-One

Many-to-one relationships should generally be modeled with lazy loading.

Example:

```text id="db012"
Task

↓

Sprint
```

---

## 36. Many-to-Many

Avoid direct many-to-many mappings for business entities.

Instead use a join entity.

Example:

```text id="db013"
UserProjectMembership
```

This allows additional attributes such as roles, permissions, or timestamps.

---

## 37. Normalization

Target approximately Third Normal Form (3NF).

Avoid:

- Duplicate user names
- Duplicate workspace titles
- Repeated configuration values

Denormalization should only occur for measured performance benefits.

---

## 38. Join Tables

Join tables should have meaningful names.

Example:

```text id="db014"
project_members
```

rather than generic names.

---

## 39. Referential Integrity

Always enforce relationships with foreign keys.

Do not rely solely on application code to maintain consistency.

---

## 40. Relationship Checklist

✓ Normalized

✓ Foreign keys

✓ Join entities

✓ LAZY associations

✓ Minimal duplication

---

# Part 5 – Transactions

## 41. Purpose

Transactions ensure that multiple database operations either complete successfully together or fail together.

---

## 42. Transaction Boundaries

Transactions belong primarily in the service layer.

Repositories should not manage transaction boundaries directly.

---

## 43. `@Transactional`

Use `@Transactional` on service methods that modify data.

Keep transactional scopes as small as practical.

---

## 44. Read-Only Transactions

Use read-only transactions for query operations where appropriate.

This communicates intent and may enable optimizations.

---

## 45. Rollback Behavior

Unexpected runtime exceptions should trigger transaction rollback.

Checked exceptions should be configured explicitly if rollback is required.

---

## 46. Nested Transactions

Avoid unnecessary nested transactions.

Complex transactional hierarchies increase maintenance difficulty and may lead to unexpected behavior.

---

## 47. Long Transactions

Avoid transactions that:

- Perform network calls
- Wait for user input
- Execute lengthy computations

Keep transactions focused on database work.

---

## 48. Consistency

Every transaction should leave the database in a valid state.

Partial updates should never be visible to other transactions.

---

## 49. Transaction Checklist

✓ Service layer

✓ Short-lived

✓ Consistent

✓ Rollback support

✓ Read-only when applicable

---

## 50. Midpoint Summary

At this stage, SprintForge database standards define:

- Database philosophy and architecture
- Schema design conventions
- JPA/Hibernate mapping standards
- Relationship modeling
- Transaction management

---
Perfect. This completes the `database.md` handbook.

---

# SprintForge Engineering Standard
# Database Standards

# Part 6 – Indexing, Query Optimization & Performance

## 51. Purpose

A well-designed schema is only effective when queries remain efficient as the database grows.

SprintForge prioritizes database performance through proper indexing, query optimization, and efficient ORM usage.

---

## 52. Indexing Philosophy

Indexes improve read performance by allowing the database to locate rows quickly.

However, every index also:

- Consumes storage
- Slows INSERT operations
- Slows UPDATE operations
- Slows DELETE operations

Indexes should exist because they improve measured query performance—not because they "might be useful."

---

## 53. Primary Key Index

Every primary key is automatically indexed.

Example:

```text id="db015"
users

↓

id

↓

Primary Key Index
```

No additional index is required.

---

## 54. Foreign Key Indexes

Frequently joined foreign keys should also be indexed.

Examples:

```text id="db016"
workspace_id

project_id

owner_id

assignee_id
```

Without indexes, joins become increasingly expensive as data grows.

---

## 55. Unique Indexes

Columns with uniqueness requirements should use unique indexes.

Examples:

```text id="db017"
email

username
```

This improves lookups while enforcing uniqueness.

---

## 56. Composite Indexes

When queries commonly filter by multiple columns, composite indexes may be appropriate.

Example:

```text id="db018"
workspace_id

+

status
```

Composite indexes should reflect actual query patterns rather than theoretical possibilities.

---

## 57. Avoid Over-Indexing

Avoid creating indexes on:

- Small tables
- Rarely queried columns
- Frequently changing fields without justification

Review index usage periodically.

---

## 58. Query Optimization

Optimize queries by:

- Selecting only required columns
- Filtering at the database level
- Using pagination
- Avoiding unnecessary joins

The database should perform the heavy lifting—not the application.

---

## 59. The N+1 Query Problem

A common ORM issue:

```text id="db019"
Load Tasks

↓

Load Project

↓

Load Workspace

↓

Repeat for Every Task
```

This results in excessive SQL queries.

Prevent N+1 issues using:

- Fetch joins
- Entity graphs
- DTO projections
- Batch fetching

---

## 60. Query Performance Checklist

✓ Indexed foreign keys

✓ Pagination

✓ No N+1 queries

✓ Optimized joins

✓ Measured performance

---

# Part 7 – Migrations, Auditing & Concurrency

## 61. Purpose

Database schemas evolve throughout the application's lifecycle.

Schema evolution should be controlled, repeatable, and versioned.

---

## 62. Database Migrations

SprintForge standardizes on migration-based schema evolution.

Recommended tools:

```text id="db020"
Flyway

or

Liquibase
```

Manual schema changes should never be applied directly to production.

---

## 63. Migration Principles

Every schema modification should be:

- Versioned
- Repeatable
- Reviewed
- Tested
- Deployable

Database changes are code and should follow the same review process.

---

## 64. Migration Rules

Each migration should perform one logical change.

Examples:

- Create table
- Add column
- Create index
- Rename column
- Add constraint

Avoid large migrations that perform unrelated operations.

---

## 65. Auditing

Most business entities should record:

```text id="db021"
created_at

updated_at

created_by

updated_by
```

Auditing supports troubleshooting, compliance, and historical analysis.

---

## 66. Soft Deletes

Some entities should support soft deletion.

Example:

```text id="db022"
deleted

deleted_at
```

Soft deletes preserve historical data while hiding records from normal application queries.

---

## 67. Optimistic Locking

Concurrent updates should be managed using optimistic locking where appropriate.

Standard JPA mechanism:

```text id="db023"
@Version
```

This helps prevent accidental overwrites when multiple users edit the same entity.

---

## 68. Pessimistic Locking

Use pessimistic locking only when absolutely necessary.

It is appropriate for scenarios where concurrent modifications must be prevented immediately.

Because it reduces concurrency, it should be applied sparingly.

---

## 69. Data Retention

Historical records should follow defined retention policies.

Examples:

- Audit logs
- Notifications
- Archived sprints
- Activity history

Retention requirements should align with business and legal needs.

---

## 70. Migration & Concurrency Checklist

✓ Versioned migrations

✓ Auditing

✓ Optimistic locking

✓ Soft deletes where appropriate

✓ Controlled schema evolution

---

# Part 8 – Database Anti-Patterns

## 71. Purpose

Poor database practices often remain hidden until the application reaches production scale.

Avoid the following anti-patterns.

---

## 72. Business Logic in the Database

Avoid implementing business workflows using:

- Complex triggers
- Stored procedures
- Database-side business rules

SprintForge keeps business logic in the service layer.

---

## 73. Excessive EAGER Fetching

Bad:

```text id="db024"
Entity

↓

Loads Everything

↓

Huge Object Graph
```

Default to lazy loading.

---

## 74. Missing Transactions

Executing related updates without a transaction risks inconsistent data.

Always define appropriate transaction boundaries.

---

## 75. Missing Constraints

Never rely solely on application validation.

Examples:

- Duplicate usernames
- Invalid foreign keys
- Null required values

The database should enforce structural integrity.

---

## 76. Loading Entire Tables

Avoid:

```text id="db025"
findAll()
```

on large production tables unless absolutely necessary.

Prefer:

- Pagination
- Streaming
- Filtering

---

## 77. Ignoring Execution Plans

Slow queries should be investigated using PostgreSQL execution plans (`EXPLAIN` / `EXPLAIN ANALYZE`) rather than guessing the cause.

Performance tuning should be evidence-based.

---

## 78. Using JSON Instead of Relationships

Avoid storing relational data inside JSON columns when proper relational tables provide a better model.

Use `JSONB` only for genuinely semi-structured or evolving data.

---

## 79. Anti-Pattern Checklist

Avoid:

✗ Business logic in SQL

✗ EAGER fetching

✗ Missing transactions

✗ Missing constraints

✗ Loading entire tables

✗ Ignoring query plans

✗ Improper JSON usage

---

# Part 9 – Reference Templates & Implementation Blueprints

## 80. Entity Lifecycle

```text id="db026"
Create

↓

Persist

↓

Update

↓

Archive

↓

Delete
```

---

## 81. Relationship Model

```text id="db027"
Workspace

↓

Projects

↓

Sprints

↓

Tasks
```

---

## 82. Transaction Flow

```text id="db028"
Service

↓

@Transactional

↓

Repositories

↓

Commit

or

Rollback
```

---

## 83. Migration Flow

```text id="db029"
Migration Script

↓

Review

↓

Test

↓

Deploy

↓

Database Updated
```

---

## 84. Query Optimization Flow

```text id="db030"
Slow Query

↓

Analyze

↓

Index

↓

Optimize

↓

Measure Again
```

---

## 85. Auditing Model

```text id="db031"
created_at

updated_at

created_by

updated_by
```

---

## 86. Database Checklist

✓ Constraints

✓ Transactions

✓ Indexes

✓ Migrations

✓ Auditing

---

# Part 10 – Governance & Final Principles

## 87. Purpose

Database standards ensure SprintForge stores and retrieves data consistently, efficiently, and safely across all environments.

A disciplined persistence strategy protects data integrity while supporting long-term application growth.

---

## 88. Ownership

Database responsibilities should remain clearly separated.

| Concern | Owner |
|---------|-------|
| Schema Design | Database Standards |
| Entity Mapping | JPA Entities |
| Business Transactions | Service Layer |
| Data Access | Repositories |
| Schema Evolution | Migration Tool |
| Integrity | Database Constraints |

---

## 89. Code Review Requirements

Every persistence-related review should verify:

- Correct entity mappings
- Appropriate relationships
- Lazy loading by default
- Proper transaction boundaries
- Necessary indexes
- Constraints present
- Migration included (when schema changes)

---

## 90. Documentation

Maintain documentation for:

- Entity relationships
- Migration history
- Index strategy
- Retention policies
- Soft-delete behavior
- Locking strategy (where applicable)

Documentation should evolve alongside the schema.

---

## 91. Testing Expectations

Database-related testing should include:

- Repository tests
- Migration tests
- Transaction rollback tests
- Constraint validation
- Optimistic locking behavior
- Performance testing for critical queries

Production database behavior should be validated before deployment.

---

## 92. Evolution Strategy

As SprintForge grows:

- Introduce indexes only after measuring need
- Remove obsolete columns through controlled migrations
- Refactor relationships deliberately
- Review slow queries periodically
- Keep the schema normalized unless justified otherwise

Schema evolution should be incremental, predictable, and reversible where practical.

---

## 93. AI-Assisted Development

AI tools can generate entities, repositories, and migration scripts, but generated persistence code should be reviewed to ensure it:

- Uses explicit mappings
- Avoids N+1 query issues
- Applies correct transaction boundaries
- Includes necessary constraints and indexes
- Follows SprintForge naming conventions
- Produces safe and reviewable migrations

AI should accelerate development while preserving database integrity.

---

## 94. Final Database Principles

Every SprintForge database should be:

✓ Reliable

✓ Consistent

✓ Normalized

✓ Transactional

✓ Secure

✓ Performant

✓ Scalable

✓ Well indexed

✓ Easy to migrate

✓ Easy to maintain

---

## 95. Database Compliance Checklist

Before merging persistence-related changes:

### Schema

✓ Naming conventions followed

✓ Constraints defined

✓ Foreign keys present

✓ Appropriate data types selected

### ORM

✓ Explicit mappings

✓ LAZY loading

✓ Controlled cascading

✓ Transaction boundaries reviewed

### Performance

✓ Necessary indexes

✓ Pagination for collections

✓ No obvious N+1 queries

✓ Query plans reviewed for critical operations

### Operations

✓ Migration added

✓ Auditing maintained

✓ Locking strategy considered

✓ Tests updated

---

## 96. Closing Statement

The database is SprintForge's authoritative source of truth. Every design decision—from schema structure and entity mapping to indexing and migrations—should prioritize correctness, consistency, and long-term maintainability.

By combining PostgreSQL best practices, disciplined JPA usage, controlled schema evolution, and evidence-based performance optimization, SprintForge ensures that its persistence layer remains robust, scalable, and capable of supporting future growth without sacrificing data integrity.

---
