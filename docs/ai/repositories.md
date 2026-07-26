# SprintForge Engineering Standard
# Repository Layer

Version: 1.0
Status: Approved
Scope: Entire SprintForge Backend

---

# Part 1
# Repository Philosophy & Responsibilities

---

# 1. Purpose

The Repository Layer provides a clean abstraction over data persistence.

Repositories are responsible for interacting with the database and exposing persistence operations required by the service layer.

Repositories do not implement business workflows.

Repositories do not enforce business rules.

Repositories exist solely to manage persistence.

---

# 2. Repository Philosophy

SprintForge follows the Repository Pattern.

Repositories abstract the persistence mechanism from the business layer.

Business Services

↓

Repository

↓

Database

Services should never concern themselves with SQL, JPA, or database implementation details.

Repositories should never concern themselves with business decisions.

---

# 3. Single Responsibility Principle

Each repository owns one aggregate.

Examples

WorkspaceRepository

↓

Workspace

ProjectRepository

↓

Project

TaskRepository

↓

Task

Repositories should never manage multiple unrelated aggregates.

---

# 4. Persistence First

Repositories answer questions about data.

Examples

Find Workspace

Save Workspace

Delete Workspace

Find Active Tasks

Exists By Slug

Repositories do not answer business questions.

Incorrect

Can user archive workspace?

Should sprint close?

May member invite users?

Those belong inside services.

---

# 5. Repository Responsibilities

Repositories are responsible for:

✓ Persisting entities

✓ Loading entities

✓ Updating entities

✓ Deleting entities

✓ Executing queries

✓ Pagination

✓ Sorting

✓ Filtering

✓ Projections

✓ Specifications

✓ Locking

✓ Batch persistence

Nothing more.

---

# 6. Responsibilities That DO NOT Belong Here

Repositories must NEVER contain:

✗ Business logic

✗ Permission validation

✗ Authentication

✗ Authorization

✗ Event publishing

✗ Logging business events

✗ Email sending

✗ AI calls

✗ HTTP handling

✗ DTO mapping

✗ JSON serialization

✗ Validation

✗ Calling other repositories

✗ Calling services

If business knowledge is required,

the code belongs inside a service.

---

# 7. Repository Layer Architecture

Client

↓

Controller

↓

Service

↓

Repository

↓

Database

Repositories are an implementation detail of the service layer.

Controllers must never communicate directly with repositories.

---

# 8. Aggregate Ownership

Every aggregate has exactly one owning repository.

Workspace

↓

WorkspaceRepository

Task

↓

TaskRepository

Board

↓

BoardRepository

Comment

↓

CommentRepository

Repositories must not expose another aggregate's persistence concerns.

---

# 9. Long-Term Vision

Repositories should remain stable even if the underlying persistence technology changes.

Future migrations should be possible.

Examples

PostgreSQL

↓

MySQL

↓

CockroachDB

↓

Aurora

↓

MongoDB (where appropriate)

Business services should require minimal or no changes during persistence migrations.

The repository layer should remain focused, predictable, and technology-aware while shielding higher layers from persistence details.

---

End of Part 1

# SprintForge Engineering Standard
# Repository Layer

# Part 2
# Repository Structure & Naming Standards

---

# 10. Package Structure

Every domain module shall follow a consistent package structure.

Example

workspace/

    controller/

    service/

    repository/
        WorkspaceRepository.java

    mapper/

    dto/

    entity/

    validation/

Repository packages should contain only persistence-related components.

Supporting implementations such as Specifications or custom repository implementations should remain inside the repository package.

Consistency takes priority over personal preference.

---

# 11. Repository Declaration

Every repository should be declared as a Java interface.

Example

WorkspaceRepository

↓

extends JpaRepository<Workspace, UUID>

Spring Data JPA generates the implementation automatically whenever possible.

Concrete repository implementations should only exist when custom persistence logic is required.

---

# 12. Repository Naming Convention

Repositories should follow the naming pattern:

<Entity>Repository

Examples

WorkspaceRepository

ProjectRepository

BoardRepository

SprintRepository

TaskRepository

CommentRepository

AttachmentRepository

NotificationRepository

Never invent alternative names.

Incorrect

WorkspaceDAO

WorkspaceStorage

WorkspacePersistence

WorkspaceDatabase

Repository names should clearly identify the aggregate they own.

---

# 13. One Repository Per Aggregate

Each aggregate root owns exactly one repository.

Correct

WorkspaceRepository

↓

Workspace

Incorrect

WorkspaceRepository

↓

Workspace

Project

Board

Repositories should never become shared persistence layers for unrelated entities.

---

# 14. Interface-First Design

Repositories should expose only the persistence operations required by the business layer.

The service layer communicates only with repository interfaces.

Persistence implementation details remain hidden.

---

# 15. Repository Visibility

Repositories should only be injected into their owning service.

Correct

WorkspaceService

↓

WorkspaceRepository

Incorrect

ProjectService

↓

WorkspaceRepository

Cross-module repository access is prohibited.

Services communicate with other services, not their repositories.

---

# 16. Method Organization

Repository methods should be grouped logically.

Recommended order

Inherited CRUD Operations

↓

Existence Queries

↓

Lookup Queries

↓

Collection Queries

↓

Projection Queries

↓

Specification Methods

↓

Custom Queries

Maintain a predictable structure across all repositories.

---

# 17. Method Naming Standards

Repository methods should describe the persistence operation being performed.

Good Examples

findById()

findBySlug()

findByOwner()

existsBySlug()

countByWorkspace()

deleteById()

findAllByWorkspace()

Bad Examples

execute()

load()

process()

query()

handle()

Method names should be intention-revealing and follow Spring Data JPA conventions whenever possible.

---

# 18. Query Derivation First

Prefer Spring Data JPA's query derivation whenever it produces a clear and efficient query.

Examples

findByEmail()

existsBySlug()

findAllByWorkspaceId()

countByStatus()

Only introduce custom queries when derived queries become difficult to read or inefficient.

---

# 19. Custom Repository Extensions

When repository logic becomes too complex for a single interface, extract dedicated repository extensions.

Example

WorkspaceRepository

↓

extends

WorkspaceRepositoryCustom

↓

WorkspaceRepositoryImpl

Keep the primary repository interface focused and easy to navigate.

---

# 20. Generic Repository Usage

Repositories should extend only the required Spring Data interfaces.

Examples

JpaRepository

JpaSpecificationExecutor

Avoid creating custom base repositories unless a project-wide capability genuinely requires one.

Prefer composition over unnecessary inheritance.

---

# 21. Design Goals

Every repository should be:

✓ Focused

✓ Predictable

✓ Lightweight

✓ Consistent

✓ Persistence-Oriented

✓ Easy to Test

✓ Easy to Extend

Repository implementations should remain simple while providing a stable persistence abstraction for the service layer.

---

End of Part 2

# SprintForge Engineering Standard
# Repository Layer

# Part 3
# Query Design Standards

---

# 22. Query Philosophy

Repository methods should describe business-relevant persistence operations rather than implementation details.

Queries should be:

✓ Readable

✓ Predictable

✓ Efficient

✓ Intention-Revealing

The repository API should communicate what data is required, not how it is retrieved.

---

# 23. Query Derivation First

Always prefer Spring Data JPA query derivation when it produces a clear and maintainable method.

Examples

findByEmail()

findByWorkspaceId()

findByStatus()

existsBySlug()

countByWorkspaceId()

deleteById()

Derived queries improve readability and reduce boilerplate.

---

# 24. When to Use @Query

Use @Query only when:

• Query derivation becomes difficult to read

• Multiple joins are required

• Aggregations are required

• Complex filtering is required

• Performance optimization requires explicit control

Do not use @Query simply because it is familiar.

---

# 25. JPQL vs Native SQL

Prefer JPQL whenever possible.

Use Native SQL only when:

✓ Database-specific features are required

✓ Performance cannot be achieved with JPQL

✓ Complex reporting queries require native syntax

Native queries should remain the exception rather than the rule.

---

# 26. Query Naming Standards

Repository method names should describe exactly what data they return.

Good

findBySlug()

findByWorkspaceId()

findAllByProjectId()

existsByEmail()

countByStatus()

Bad

load()

execute()

fetch()

getData()

query()

Names should remain concise while clearly expressing intent.

---

# 27. Optional Usage

Single-object lookup methods should return Optional<T> when absence is expected.

Examples

Optional<Workspace>

Optional<Project>

Optional<Task>

Never return null from repository methods.

Collection queries should return empty collections instead.

---

# 28. Collection Return Types

Use the most appropriate collection type.

Examples

List<T>

Set<T>

Page<T>

Slice<T>

Stream<T> (when appropriate)

Avoid returning generic Collection<T> unless flexibility is specifically required.

---

# 29. Pagination Standards

Large datasets should always support pagination.

Preferred types

Page<T>

Slice<T>

Cursor-based pagination (where appropriate)

Repositories should never return unbounded collections for user-facing operations.

---

# 30. Sorting Standards

Sorting should be delegated to the database whenever possible.

Examples

Sort.by("createdAt")

Sort.by("priority")

Sort.by("dueDate")

Avoid sorting large collections in application memory.

---

# 31. Filtering Standards

Filtering should occur inside database queries whenever practical.

Examples

Active Tasks

Archived Projects

Completed Sprints

Workspace Members

Avoid loading unnecessary records and filtering them in memory.

---

# 32. Projection Standards

Use projections when only partial data is required.

Examples

WorkspaceSummary

TaskSummary

ProjectStatistics

Avoid loading complete entities when only a subset of fields is needed.

Projections improve performance and reduce memory usage.

---

# 33. Specification Usage

Dynamic filtering should use Specifications.

Examples

Search Projects

Advanced Task Filters

Workspace Search

Audit Search

Specifications should remain composable and reusable.

Avoid creating extremely large derived query methods.

---

# 34. Aggregate Queries

Repositories may expose aggregate queries.

Examples

countByWorkspace()

countCompletedTasks()

sumEstimatedHours()

averageVelocity()

Aggregation belongs in repositories because it is a persistence concern.

Business interpretation of aggregates belongs in services.

---

# 35. Exists Queries

Use exists queries when only existence matters.

Correct

existsBySlug()

existsByEmail()

Incorrect

findBySlug()

↓

if(entity != null)

Exists queries reduce unnecessary database work.

---

# 36. Count Queries

Use count queries whenever only totals are required.

Correct

countByWorkspace()

Incorrect

findAll()

↓

.size()

Database engines calculate counts more efficiently.

---

# 37. Delete Queries

Delete operations should remain explicit.

Examples

deleteById()

deleteAllByWorkspaceId()

Avoid exposing destructive bulk delete operations without clear business requirements.

Business authorization always belongs in the service layer.

---

# 38. Bulk Update Queries

Bulk updates should be used carefully.

Examples

Archive completed tasks

Close expired sprints

Deactivate old invitations

Bulk updates bypass parts of the persistence lifecycle.

Services should understand these implications before invoking them.

---

# 39. Stream Queries

Streams should only be used for processing large datasets.

Examples

Large exports

Migration jobs

Analytics

Always close streams appropriately to prevent resource leaks.

---

# 40. Query Readability

Readability takes priority over cleverness.

Prefer several clear repository methods over one unreadable method.

Repository APIs should remain self-explanatory.

---

# 41. Design Goals

Repository queries should be:

✓ Readable

✓ Predictable

✓ Efficient

✓ Composable

✓ Pageable

✓ Testable

✓ Database-Oriented

Every query should retrieve exactly the data required—no more and no less.


---

# 42. Index Awareness

Repository queries should be designed with database indexes in mind.

Frequently executed queries should target indexed columns whenever possible.

Examples

findByEmail()

findBySlug()

findByWorkspaceId()

findByProjectId()

findByCreatedAt()

Avoid repository methods that repeatedly perform full table scans on large datasets.

Index design should be driven by actual query patterns rather than assumptions.

Repository developers should collaborate with database schema designers to ensure commonly executed queries benefit from appropriate indexes.

Performance-critical queries should always be reviewed for index utilization.

---

# 43. Fetch Strategy Awareness

Repositories should retrieve only the data required by the current business operation.

Avoid loading unnecessary associations or entire object graphs.

Prefer fetching related entities only when they are required by the service.

Examples

Workspace Summary

↓

Workspace only

Workspace Details

↓

Workspace + Members

Project Dashboard

↓

Project + Active Tasks

Fetching excessive relationships increases:

• Memory usage

• Query execution time

• Serialization cost

Repository methods should remain intentional about the amount of data they retrieve.

---

# 44. Read vs Write Repository Mindset

Repository methods should clearly distinguish between read operations and write operations.

Read Operations

Examples

findById()

findAllByWorkspace()

existsBySlug()

countByStatus()

Projection queries

Search queries

Write Operations

Examples

save()

saveAll()

delete()

deleteById()

Bulk update queries

This separation improves readability and prepares the architecture for future evolution toward CQRS if required.

Although SprintForge currently uses a unified repository model, developers should think of reads and writes as distinct responsibilities during repository design.

---

End of Part 3


# SprintForge Engineering Standard
# Repository Layer

# Part 4
# Custom Queries, Specifications & Advanced Query Techniques

---

# 45. Philosophy

Custom queries should be introduced only when standard Spring Data repository methods cannot express the required persistence operation clearly or efficiently.

Complexity should remain inside the persistence layer.

Business decisions should never migrate into repository queries.

---

# 46. When Custom Queries Are Appropriate

Use custom queries for:

✓ Complex joins

✓ Aggregate calculations

✓ Reporting

✓ Search

✓ Dynamic filtering

✓ Performance optimization

✓ Projection queries

Avoid custom queries for simple CRUD operations.

---

# 47. Specification First

For dynamic filtering, prefer Spring Data Specifications.

Examples

Task Search

Workspace Search

Project Filters

Sprint Filters

Specifications improve reuse and reduce duplicated query logic.

---

# 48. Specification Design

Each Specification should represent one filtering rule.

Examples

hasStatus()

belongsToWorkspace()

assignedToUser()

createdAfter()

priorityEquals()

Specifications should remain small and composable.

---

# 49. Specification Composition

Multiple Specifications should be combined using logical operators.

Examples

Status

AND

Workspace

AND

Priority

AND

Due Date

Avoid creating one large Specification containing every possible condition.

---

# 50. Criteria API Usage

Use the JPA Criteria API only when Specifications cannot adequately express the query.

Examples

Dynamic joins

Subqueries

Complex predicates

Conditional projections

Criteria queries should remain readable.

Avoid unnecessary complexity.

---

# 51. JPQL Standards

JPQL should remain readable and business-oriented.

Prefer named parameters.

Correct

:workspaceId

:status

:ownerId

Avoid positional parameters whenever possible.

---

# 52. Native Query Standards

Native SQL should only be used when:

Database-specific features are required.

Window functions are required.

Recursive queries are required.

Performance cannot be achieved using JPQL.

Every native query should include documentation explaining why JPQL was insufficient.

---

# 53. Projection Queries

Prefer projections when complete entities are unnecessary.

Examples

WorkspaceSummary

ProjectStatistics

TaskDashboard

SprintMetrics

Avoid loading entire aggregates for read-only summary views.

---

# 54. Interface-Based Projections

Prefer interface projections for simple read models.

Example

WorkspaceSummary

↓

Name

Slug

Owner

CreatedAt

Interface projections reduce unnecessary object creation.

---

# 55. DTO Projections

Use DTO projections when:

Business reports

Analytics

Dashboard responses

Complex joins

Multiple aggregates

DTO projections should remain immutable whenever possible.

---

# 56. Entity Graph Usage

Use Entity Graphs when specific relationships must be eagerly loaded.

Examples

Workspace

↓

Members

↓

Owner

↓

Settings

Avoid unnecessary eager loading.

Fetch only the associations required by the current use case.

---

# 57. Query Composition

Large queries should be decomposed whenever possible.

Instead of

One enormous query

Prefer

Reusable Specifications

Reusable projections

Composable predicates

Maintain readability over cleverness.

---

# 58. Search Queries

Search repositories should support:

Filtering

Sorting

Pagination

Optional keyword searching

Search queries should remain database-efficient.

Avoid loading complete datasets into memory.

---

# 59. Aggregate Queries

Repositories may expose reporting queries.

Examples

Average Sprint Velocity

Completed Task Count

Workspace Statistics

Project Progress

Repositories calculate data.

Services interpret data.

---

# 60. Read Model Queries

Repositories may expose optimized read models.

Examples

Dashboard View

Kanban Summary

Sprint Overview

Calendar View

Read models should prioritize performance.

They should not be reused for write operations.

---

# 61. Query Reuse

Avoid duplicating similar query logic.

Extract reusable Specifications.

Extract reusable projections.

Extract reusable predicates.

Query reuse improves maintainability.

---

# 62. Documentation

Every complex query should include documentation explaining:

Purpose

Expected inputs

Performance considerations

Reason for custom implementation

Especially document:

Native queries

Recursive queries

Large join queries

Future maintainers should understand why the query exists.

---

# 63. Design Goals

Advanced repository queries should be:

✓ Readable

✓ Reusable

✓ Efficient

✓ Composable

✓ Database Friendly

✓ Pageable

✓ Well Documented

✓ Easy to Test

Complex persistence should remain inside repositories while business interpretation remains inside services.

---

---

# 64. Query Hints

Query hints may be used to optimize repository performance for specific persistence scenarios.

Examples include:

• Read-only queries

• Query timeouts

• Fetch size optimization

• Second-level cache hints

Query hints should be applied only after identifying a measurable performance need.

Avoid premature optimization.

Every query hint should improve either:

✓ Performance

✓ Resource utilization

✓ Database efficiency

Developers should document non-obvious query hints to aid future maintenance.

---

# 65. Locking Strategies

Repositories may expose locking queries when concurrent access to shared data must be controlled.

SprintForge supports two primary locking strategies.

Optimistic Locking

Uses a version field to detect concurrent modifications.

Recommended for most business operations where conflicts are expected to be infrequent.

Examples

Updating Tasks

Editing Projects

Modifying Workspace Settings

Optimistic locking improves scalability by avoiding unnecessary database locks.

Pessimistic Locking

Acquires a database lock before modifying data.

Use only when concurrent updates could lead to unacceptable business inconsistencies.

Examples

Financial transactions

Inventory allocation

Critical workflow state transitions

Pessimistic locking should remain exceptional because it reduces concurrency and may increase contention.

Repository methods exposing locking behavior should clearly indicate their intent.

Examples

findByIdForUpdate()

findWorkspaceWithLock()

The service layer remains responsible for deciding when locking is required.

---

# 66. Stored Procedure Usage

Stored procedures are supported only under exceptional circumstances.

Acceptable use cases include:

✓ Legacy database integration

✓ Database-managed business processes

✓ Performance-critical reporting

✓ Vendor-specific database capabilities

Repositories invoking stored procedures should clearly document:

• Why a stored procedure is required

• Why JPQL or the Criteria API is insufficient

• Expected inputs and outputs

Business rules should remain inside the application whenever possible.

Repositories should not migrate application logic into database procedures simply for convenience.

---

# 67. Query Complexity Guidelines

Repository queries should remain understandable and maintainable.

When query complexity begins to reduce readability, developers should consider refactoring.

Preferred approaches include:

• Breaking large queries into reusable Specifications

• Using projections for read models

• Extracting reusable predicates

• Creating dedicated reporting queries

Avoid repository methods that attempt to solve every filtering scenario in a single query.

As a general guideline:

• Minimize unnecessary joins.

• Avoid excessively large query methods.

• Prefer several focused queries over one monolithic query when readability improves without introducing significant performance costs.

Repository code should optimize for long-term maintainability as well as runtime performance.

---

End of Part 4

# SprintForge Engineering Standard
# Repository Layer

# Part 5
# Performance & Optimization

---

# 68. Performance Philosophy

Repositories should retrieve only the data required by the current business operation.

Every unnecessary query, column, row, or entity increases application latency and resource consumption.

Performance should be considered during repository design rather than treated as an afterthought.

---

# 69. Minimize Database Round Trips

Each repository operation should minimize the number of database interactions.

Avoid unnecessary repeated queries.

Examples

Incorrect

Find Workspace

↓

Find Owner

↓

Find Members

↓

Find Settings

Correct

Fetch the required data using an optimized query or Entity Graph when appropriate.

Reducing database round trips generally has a greater performance impact than optimizing application code.

---

# 70. Prevent N+1 Query Problems

Repositories should be designed to avoid N+1 query issues.

Common solutions include:

✓ Entity Graphs

✓ Fetch Joins

✓ Batch Fetching

✓ Optimized Projections

Developers should verify generated SQL when introducing new repository methods involving relationships.

---

# 71. Retrieve Only Required Columns

Do not load complete entities when only a subset of fields is required.

Prefer:

Interface Projections

DTO Projections

Summary Views

Examples

Workspace Summary

Task Card

Project Statistics

Loading fewer columns reduces network traffic, memory usage, and object creation.

---

# 72. Pagination by Default

Repositories should paginate all potentially large result sets.

Examples

Task Lists

Search Results

Activity Logs

Notifications

Audit Records

Avoid returning thousands of records in a single query.

---

# 73. Prefer Slice Over Page When Counts Are Unnecessary

Use Slice<T> instead of Page<T> when the total number of records is not required.

Examples

Infinite scrolling

Activity feeds

Notification lists

Slice avoids executing an additional COUNT query, improving performance.

---

# 74. Batch Operations

Prefer batch operations when processing multiple entities.

Examples

saveAll()

deleteAll()

Bulk updates

Batch inserts

Avoid executing individual persistence operations inside loops whenever possible.

---

# 75. Bulk Updates

Use bulk update queries when modifying large datasets.

Examples

Archive completed tasks

Deactivate expired invitations

Close completed sprints

Bulk operations reduce database round trips.

Be aware that bulk updates bypass parts of the JPA persistence context.

---

# 76. Efficient Existence Checks

When only existence matters, use exists queries.

Correct

existsByEmail()

existsBySlug()

existsByWorkspaceId()

Incorrect

findByEmail()

↓

entity != null

Database engines optimize existence queries more efficiently than loading complete entities.

---

# 77. Efficient Count Queries

When only totals are required, use count queries.

Correct

countByWorkspace()

countByStatus()

Incorrect

findAll()

↓

collection.size()

Counting should be delegated to the database.

---

# 78. Index Utilization

Repository methods should take advantage of indexed database columns.

Frequently queried fields should typically include:

Email

Slug

Workspace ID

Project ID

Status

Created Date

Repositories should avoid query patterns that cannot benefit from indexes unless justified.

---

# 79. Avoid In-Memory Processing

Filtering

Sorting

Grouping

Aggregation

should be delegated to the database whenever practical.

Repositories should avoid retrieving large datasets solely for in-memory processing.

---

# 80. Fetch Size Awareness

Large read operations should consider fetch size optimization where supported.

Examples

Exports

Analytics

Migration Jobs

Reporting

Fetch size optimization reduces memory pressure during large result processing.

---

# 81. Streaming Large Results

Stream repository results only when processing very large datasets.

Examples

CSV Export

Data Migration

Reporting

Streams should always be properly closed to release database resources.

---

# 82. Caching Considerations

Repositories should remain cache-aware but not cache-dependent.

Caching strategies belong at higher architectural layers unless a persistence-specific cache is explicitly configured.

Repositories should function correctly regardless of cache availability.

---

# 83. Query Monitoring

Performance-critical repository methods should be monitored.

Metrics may include:

Execution time

Rows returned

Database latency

Slow query frequency

Repositories generating consistently slow queries should be reviewed and optimized.

---

# 84. Database Execution Plans

Slow or frequently executed queries should be analyzed using database execution plans.

Review:

Index usage

Join strategy

Full table scans

Sorting operations

Execution plan analysis should precede significant query optimization efforts.

---

# 85. Performance Testing

Repository performance should be validated using realistic data volumes.

Testing should consider:

Large workspaces

High task counts

Many concurrent users

Long activity histories

Performance assumptions should be verified through measurement rather than intuition.

---

# 86. Design Goals

Repository implementations should be:

✓ Efficient

✓ Scalable

✓ Database-Friendly

✓ Resource Conscious

✓ Predictable

✓ Measurable

✓ Optimized for Growth

Performance optimization should improve measurable outcomes without sacrificing readability or maintainability.

---

---

# 87. Connection Pool Awareness

Every repository operation consumes a database connection.

Repository methods should execute efficiently and release database connections as quickly as possible.

Avoid:

• Long-running transactions

• Unnecessary database round trips

• Blocking operations inside repository methods

• Fetching excessive amounts of data

Repositories should never perform operations unrelated to persistence while holding a database connection.

Examples of inappropriate work include:

• Calling external services

• Sending emails

• AI inference

• File processing

Such operations belong in higher architectural layers after persistence has completed.

Efficient connection usage improves application throughput and reduces connection pool exhaustion under high load.

---

# 88. Read Replicas & Future Scalability

SprintForge is initially designed around a single primary database.

However, repository design should support future horizontal scaling through read replicas.

Future architecture may separate:

Write Operations

↓

Primary Database

Read Operations

↓

Read Replica(s)

Repository methods should therefore distinguish between read-oriented and write-oriented operations.

Repositories should avoid assumptions that all database interactions target the same database instance.

This architectural flexibility enables SprintForge to scale without requiring significant changes to business services.

---

# 89. Database Vendor Independence

Repositories should prioritize database portability whenever practical.

Prefer:

✓ Standard JPA

✓ JPQL

✓ Specifications

✓ Standard SQL

Avoid relying heavily on vendor-specific features unless they provide measurable business value.

Examples of vendor-specific features include:

• Proprietary SQL functions

• Vendor-specific locking syntax

• Database-specific extensions

When vendor-specific functionality is necessary, the repository should clearly document:

• Why it is required

• Which database feature is being used

• Why a portable alternative was insufficient

Keeping repositories vendor-aware but vendor-independent simplifies future database migrations.

---

# 90. Performance Review Checklist

Before introducing a new repository method, developers should review its performance characteristics.

Questions to consider:

✓ Does the query retrieve only the required data?

✓ Should a projection be used instead of a full entity?

✓ Is pagination required?

✓ Can the query benefit from an existing database index?

✓ Does the query avoid N+1 problems?

✓ Is filtering performed by the database instead of application memory?

✓ Are sorting and aggregation delegated to the database?

✓ Would an exists() query be more appropriate?

✓ Would a count() query be more efficient?

✓ Has the query been tested using realistic data volumes?

✓ Has the generated SQL been reviewed when appropriate?

✓ Is the query readable and maintainable?

Every repository should optimize for both correctness and long-term scalability.

Performance improvements should be measurable, justified, and maintainable.

---

End of Part 5

# SprintForge Engineering Standard
# Repository Layer

# Part 6
# Transactions & Locking

---

# 91. Transaction Philosophy

Repositories participate in transactions but do not define business transaction boundaries.

The service layer owns transactional workflows.

Repositories execute persistence operations within the active transaction.

Transaction management should remain transparent to repository consumers.

---

# 92. Transaction Ownership

Business services are responsible for:

✓ Starting transactions

✓ Committing transactions

✓ Rolling back transactions

✓ Defining transaction boundaries

Repositories should never decide when a transaction begins or ends.

---

# 93. Repository Responsibilities Within Transactions

Repositories should perform only persistence operations.

Examples

Persist Entity

Update Entity

Delete Entity

Execute Query

Repositories should not perform business validation or coordinate multiple persistence operations across aggregates.

---

# 94. Read-Only Operations

Repository methods performing only read operations should execute within read-only transactions whenever configured by the service layer.

Read-only transactions may improve database performance and communicate developer intent.

Repositories should remain compatible with both read-only and read-write transaction contexts.

---

# 95. Transaction Scope

Repository methods should execute quickly and avoid extending transaction duration.

Avoid:

• Long-running database operations

• Waiting on external resources

• Holding locks longer than necessary

Short transactions improve scalability and reduce contention.

---

# 96. Locking Philosophy

Locking should be used only when required to preserve data consistency.

Most business operations should rely on optimistic locking.

Pessimistic locking should remain the exception.

---

# 97. Optimistic Locking

SprintForge primarily uses optimistic locking through entity versioning.

Optimistic locking detects concurrent modifications without acquiring database locks.

Recommended for:

Workspace Updates

Project Updates

Task Editing

Sprint Planning

Optimistic locking provides better scalability for most applications.

---

# 98. Pessimistic Locking

Pessimistic locking reserves database rows before modification.

Use only when concurrent updates could create unacceptable business inconsistencies.

Examples

Financial Operations

Inventory Allocation

Critical State Transitions

Repositories exposing pessimistic locking should clearly communicate this behavior through method names and documentation.

---

# 99. Versioned Entities

Entities requiring concurrent update protection should include a version field.

Example

@Version

private Long version;

Repositories should rely on JPA's optimistic locking mechanism rather than implementing manual version checks.

---

# 100. Locking Query Standards

Locking repository methods should clearly indicate their behavior.

Examples

findByIdForUpdate()

findWorkspaceWithLock()

findProjectForModification()

Avoid generic method names that conceal locking behavior.

Repository consumers should immediately recognize when a database lock will be acquired.

---

# 101. Deadlock Awareness

Repository queries should be designed to minimize deadlock risk.

Recommendations

• Keep transactions short

• Acquire resources in a consistent order

• Avoid unnecessary locking

• Update only required records

Deadlocks should be treated as exceptional situations and handled appropriately by the service layer.

---

# 102. Bulk Operations

Bulk update and delete queries bypass portions of the JPA persistence context.

Developers should understand that bulk operations:

• Do not trigger entity lifecycle callbacks

• May bypass optimistic locking

• May require persistence context synchronization

Repositories should document bulk operations that have special behavior.

---

# 103. Persistence Context Awareness

Repositories operate within the active persistence context managed by JPA.

Developers should understand that:

Loaded entities remain managed until the persistence context is cleared or the transaction completes.

Repository methods should not manually manipulate the persistence context unless absolutely necessary.

---

# 104. Flush Behavior

Repositories should normally rely on the transaction manager to flush changes automatically.

Explicit flush operations should be used only when:

✓ Immediate database synchronization is required

✓ Constraint validation must occur before additional processing

✓ Batch processing requires periodic flushing

Unnecessary flushing reduces performance.

---

# 105. Refresh Operations

Refreshing entities should be uncommon.

Use refresh only when:

• Database triggers modify values

• External processes update records

• Immediate synchronization is required

Repositories should avoid unnecessary refresh operations because they increase database traffic.

---

# 106. Retry Strategy

Repositories should not implement retry logic.

Transient failures such as:

Deadlocks

Optimistic Lock Exceptions

Temporary database failures

should be handled by higher architectural layers where appropriate.

Repositories remain focused solely on persistence.

---

# 107. Isolation Levels

Repositories should remain independent of database isolation level configuration.

Isolation policies belong to transaction management within the service layer or infrastructure configuration.

Repository code should function correctly regardless of the configured isolation level.

---

# 108. Exception Propagation

Persistence exceptions should propagate naturally to the service layer.

Repositories should not suppress, translate, or ignore persistence failures.

Exception translation into business-specific exceptions belongs within the service layer.

---

# 109. Transaction Testing

Repository tests should verify:

✓ Correct persistence

✓ Locking behavior

✓ Version handling

✓ Constraint enforcement

✓ Rollback behavior

✓ Concurrent update handling

Testing concurrent scenarios improves confidence in repository correctness.

---

# 110. Design Goals

Repository transaction behavior should be:

✓ Predictable

✓ Consistent

✓ Efficient

✓ Safe

✓ Concurrent

✓ Scalable

✓ Framework-Compliant

Repositories should participate in transactions without assuming responsibility for business transaction management.

---

---

# 111. Transaction Propagation Awareness

Transaction propagation defines how transactions interact across service boundaries.

Examples include:

• REQUIRED

• REQUIRES_NEW

• MANDATORY

• SUPPORTS

• NOT_SUPPORTED

• NEVER

Repositories should remain completely independent of transaction propagation policies.

Repository methods should neither assume nor depend on a specific propagation mode.

Transaction propagation is an application-level concern and should be configured by the service layer.

Keeping propagation decisions outside repositories preserves separation of concerns and simplifies future architectural changes.

---

# 112. Idempotent Persistence Operations

Repository methods should support idempotent business workflows whenever possible.

An idempotent operation produces the same final state regardless of how many times it is executed with the same input.

Examples

Creating a Workspace

↓

Verify unique slug before persistence

Accepting an Invitation

↓

Prevent duplicate acceptance

Completing a Task

↓

Ignore repeated completion requests

Repositories provide the persistence primitives that enable idempotent behavior.

Business services remain responsible for enforcing business-level idempotency rules.

Repositories should never assume that an operation will only be executed once.

---

# 113. Multi-Database Transactions

SprintForge is designed around a single transactional database.

Repositories should therefore assume a single persistence context during normal operation.

If future architectural requirements introduce:

• Multiple databases

• Distributed transactions

• Polyglot persistence

• Cross-database workflows

the coordination of those operations belongs to the service layer or dedicated infrastructure components.

Repositories should remain responsible only for interacting with the database they own.

Keeping repositories isolated from distributed transaction concerns improves maintainability and scalability.

---

# 114. Concurrency Review Checklist

Before introducing or modifying a repository method that changes persistent data, developers should review its concurrency characteristics.

Questions to consider:

✓ Can concurrent updates occur?

✓ Is optimistic locking sufficient?

✓ Is pessimistic locking genuinely required?

✓ Is the transaction kept as short as possible?

✓ Could this operation participate in a deadlock?

✓ Does a bulk update bypass entity lifecycle callbacks?

✓ Will retries produce duplicate or inconsistent data?

✓ Has concurrent behavior been tested?

✓ Is the locking behavior clearly documented?

✓ Are persistence exceptions allowed to propagate naturally?

Every repository should be designed to preserve data integrity while maximizing concurrency and application throughput.

---

End of Part 6
```

# SprintForge Engineering Standard
# Repository Layer

# Part 7
# Auditing & Soft Delete

---

# 115. Auditing Philosophy

SprintForge should maintain an accurate history of important persistence operations.

Auditing provides accountability, traceability, and historical visibility into changes made to persistent data.

Repositories participate in persistence operations that may generate audit information but should not contain business auditing logic.

---

# 116. Auditable Entities

Entities representing important business data should support auditing.

Examples

Workspace

Project

Sprint

Task

Comment

Invitation

Notification

Reference entities and lookup tables generally do not require auditing unless mandated by business requirements.

---

# 117. Standard Audit Fields

Auditable entities should include common metadata.

Recommended fields:

createdAt

createdBy

updatedAt

updatedBy

These fields should be maintained automatically whenever possible.

Consistent audit metadata simplifies reporting and troubleshooting.

---

# 118. Entity Auditing

Prefer framework-supported auditing mechanisms.

Examples include:

✓ Automatic timestamp generation

✓ Automatic user tracking

✓ Entity listeners

Avoid manually setting audit fields throughout the application unless a specific business requirement exists.

---

# 119. Repository Responsibilities

Repositories persist audit information as part of normal entity persistence.

Repositories should not determine:

• Who performed an action

• Why an action occurred

• Whether an action should be audited

Those decisions belong to higher architectural layers.

---

# 120. Audit Integrity

Audit metadata should accurately represent persistence history.

Repositories should never intentionally overwrite or manipulate historical audit information outside legitimate update operations.

Audit fields should remain trustworthy throughout the lifetime of an entity.

---

# 121. Soft Delete Philosophy

Soft deletion marks data as inactive instead of permanently removing it.

Soft delete preserves historical information while hiding inactive records from normal application behavior.

Soft delete is preferred whenever business data may need to be recovered or audited.

---

# 122. When to Use Soft Delete

Soft delete is recommended for:

✓ Workspaces

✓ Projects

✓ Tasks

✓ Comments

✓ Attachments

✓ Invitations

✓ User-generated content

Hard deletion may still be appropriate for temporary or disposable data.

---

# 123. Soft Delete Implementation

Soft-deleted entities should contain a deletion indicator.

Examples

deleted

deletedAt

deletedBy

Repositories should treat soft-deleted records as inactive during normal operations.

---

# 124. Default Query Behavior

Repository queries should exclude soft-deleted records by default.

Normal application operations should behave as though deleted entities no longer exist.

Administrative or recovery workflows may expose dedicated repository methods for retrieving deleted records.

---

# 125. Hard Delete Usage

Hard deletion permanently removes records from the database.

Use hard delete only when:

✓ Business requirements permit permanent removal

✓ Data retention policies allow deletion

✓ Temporary system data is being cleaned up

Hard delete should remain exceptional for core business entities.

---

# 126. Restore Operations

Repositories may support restoration of soft-deleted entities.

Examples

Restore Workspace

Restore Project

Restore Task

Restore operations should clear soft delete indicators while preserving original audit history whenever possible.

Business authorization for restoration belongs to the service layer.

---

# 127. Cascade Behavior

Soft delete behavior should be explicitly defined for related entities.

Examples

Workspace

↓

Projects

↓

Tasks

↓

Comments

Avoid accidental orphaned data.

Repository implementations should follow the application's aggregate ownership rules.

---

# 128. Audit Queries

Repositories may expose audit-related queries.

Examples

findByCreatedBy()

findByUpdatedAfter()

findByCreatedBetween()

findRecentlyModified()

Audit queries should support filtering, pagination, and sorting when appropriate.

---

# 129. Data Retention

Soft-deleted records may eventually be permanently removed according to business or regulatory requirements.

Repositories responsible for cleanup operations should clearly distinguish between:

Soft Delete

↓

Retention Period

↓

Permanent Deletion

Retention policies should be enforced consistently across the application.

---

# 130. Design Goals

Repository auditing and soft delete behavior should be:

✓ Consistent

✓ Reliable

✓ Recoverable

✓ Traceable

✓ Secure

✓ Predictable

✓ Business-Compliant

Repositories should preserve data history while supporting efficient application behavior and future recovery operations.

---

---

# 131. Audit Trail vs Entity Audit Fields

SprintForge distinguishes between entity audit fields and full audit trails.

Entity Audit Fields

Provide the latest metadata about an entity.

Examples

createdAt

createdBy

updatedAt

updatedBy

These fields answer questions such as:

• Who created this entity?

• Who last modified it?

• When was it last updated?

Full Audit Trail

Records every significant change made throughout the entity's lifetime.

Examples

Task Created

↓

Task Assigned

↓

Priority Changed

↓

Status Updated

↓

Task Archived

Audit trails provide historical accountability and support compliance, reporting, and troubleshooting.

Entity audit fields summarize the current state.

Audit trails preserve the complete history.

These mechanisms complement each other rather than replace one another.

---

# 132. Data Privacy & Regulatory Compliance

Although SprintForge prefers soft deletion for business data, legal or regulatory requirements may require permanent removal of specific information.

Examples include:

• Personal data erasure requests

• Expired retention periods

• Regulatory compliance requirements

Repositories should support compliant permanent deletion where business policies require it.

When permanent deletion occurs:

• Audit requirements should be respected.

• Referential integrity should be maintained.

• Sensitive information should not remain recoverable.

Privacy requirements always take precedence over the default soft delete strategy when mandated by applicable regulations or organizational policies.

---

# 133. Archiving Strategy

Archiving and soft deletion serve different purposes.

Archived Data

• Still exists

• Accessible through dedicated workflows

• Used for reporting

• Considered inactive

Examples

Completed Projects

Closed Sprints

Finished Workspaces

Soft Deleted Data

• Hidden from normal application behavior

• Recoverable

• Considered logically removed

Examples

Deleted Tasks

Deleted Comments

Deleted Attachments

Repositories should clearly distinguish between archived records and soft-deleted records.

A record should never be treated as archived simply because it has been soft deleted.

Business services determine when data should be archived or restored.

Repositories persist the resulting state.

---

# 134. Auditing & Soft Delete Review Checklist

Before introducing or modifying an auditable entity, developers should review the following questions:

✓ Should this entity include audit fields?

✓ Is full audit history required?

✓ Should the entity support soft deletion?

✓ Should deleted records be excluded from normal queries?

✓ Can administrators restore deleted records?

✓ Is permanent deletion ever required?

✓ Are retention policies documented?

✓ Are audit fields maintained automatically?

✓ Is restoration behavior clearly defined?

✓ Are cascading delete and restore rules documented?

✓ Does the implementation preserve historical integrity?

✓ Does the repository remain focused on persistence rather than business auditing logic?

Repositories should provide reliable persistence support while preserving the integrity, recoverability, and traceability of business data.

---

End of Part 7
```
# SprintForge Engineering Standard
# Repository Layer

# Part 8
# Repository Anti-Patterns & Code Smells

---

# 135. Philosophy

Repositories should remain focused, predictable, and persistence-oriented.

Most repository problems arise when responsibilities from other architectural layers gradually migrate into the persistence layer.

Developers should actively recognize and eliminate these anti-patterns.

---

# 136. Business Logic Inside Repositories

Repositories must never implement business rules.

Incorrect

approveWorkspace()

calculateVelocity()

assignTask()

closeSprint()

Repositories persist data.

Services make business decisions.

---

# 137. Calling Services From Repositories

Repositories must never depend on services.

Incorrect

WorkspaceRepository

↓

UserService

↓

NotificationService

↓

AIService

Repository dependencies should point only toward persistence infrastructure.

---

# 138. Repository-to-Repository Communication

Repositories should never invoke other repositories.

Incorrect

TaskRepository

↓

WorkspaceRepository

↓

ProjectRepository

Cross-aggregate coordination belongs inside the service layer.

---

# 139. DTO Mapping Inside Repositories

Repositories should return entities, projections, or explicitly defined query models.

Repositories should never construct:

Request DTOs

Response DTOs

API Models

JSON Objects

DTO mapping belongs in dedicated mappers or the service layer.

---

# 140. Validation Inside Repositories

Repositories should not perform:

Business validation

Permission validation

Request validation

Input validation

Repository responsibility begins only after the business layer has determined that persistence should occur.

---

# 141. Overusing Native SQL

Native queries should remain exceptional.

Avoid replacing straightforward JPQL or derived queries with database-specific SQL without measurable justification.

Overusing native SQL reduces portability and increases maintenance costs.

---

# 142. Giant Repository Interfaces

Repositories should remain focused.

Avoid interfaces containing dozens of unrelated query methods.

Symptoms include:

Hundreds of methods

Multiple business domains

Reporting

Search

Administration

Analytics

When repositories become excessively large, extract specialized repository extensions or dedicated read repositories.

---

# 143. Loading Entire Object Graphs

Avoid retrieving complete entity graphs when only a subset of data is required.

Incorrect

Workspace

↓

Projects

↓

Tasks

↓

Comments

↓

Attachments

Prefer projections, Entity Graphs, or focused queries that load only the required associations.

---

# 144. Returning Unbounded Collections

Repositories should avoid returning extremely large collections.

Incorrect

findAll()

↓

Millions of rows

Prefer:

Pagination

Slices

Streaming

Cursor-based retrieval

Large result sets should be processed incrementally.

---

# 145. In-Memory Filtering

Repositories should not load large datasets merely to filter them in application memory.

Incorrect

findAll()

↓

Java Stream

↓

filter()

Correct

Filtering should occur inside the database whenever practical.

---

# 146. In-Memory Sorting

Sorting large collections inside application memory wastes resources.

Prefer database sorting through repository queries.

Database engines are optimized for sorting large datasets efficiently.

---

# 147. Ignoring N+1 Problems

Repository implementations should proactively prevent N+1 query issues.

Repeated lazy loading inside loops often leads to unnecessary database traffic.

Developers should review generated SQL whenever new repository methods introduce relationships.

---

# 148. Premature Optimization

Do not introduce complexity without measurable benefit.

Examples

Complex native SQL

Manual caching

Custom persistence infrastructure

Vendor-specific optimizations

Optimize only after identifying an actual performance bottleneck.

---

# 149. Ignoring Database Indexes

Repository queries that repeatedly scan large tables should be reviewed.

Frequently executed repository methods should target indexed columns whenever practical.

Performance problems caused by missing indexes should be addressed at the database design level rather than compensated for in application code.

---

# 150. Leaking Persistence Details

Repositories should hide persistence implementation details from higher layers.

Services should not depend on:

Database-specific SQL

Persistence provider behavior

Vendor-specific APIs

Repository interfaces should remain stable even if persistence technology changes.

---

# 151. Misusing Transactions

Repositories should not:

Start transactions

Commit transactions

Rollback transactions

Choose propagation policies

Transaction management belongs entirely within the service layer.

---

# 152. Silent Failure

Repositories should never suppress persistence failures.

Incorrect

try

↓

catch

↓

ignore

Persistence exceptions should propagate naturally so that higher layers can respond appropriately.

---

# 153. Design Goals

Repositories should remain:

✓ Focused

✓ Predictable

✓ Lightweight

✓ Maintainable

✓ Database-Oriented

✓ Easy to Review

✓ Easy to Test

Avoiding repository anti-patterns keeps the persistence layer clean, scalable, and aligned with the overall architecture of SprintForge.

---

````md
---

# 154. God Repository

A repository should own a single aggregate and remain focused on persistence for that aggregate.

A repository becomes a God Repository when it begins accumulating unrelated responsibilities.

Common symptoms include:

• Managing multiple aggregates

• Performing reporting

• Executing analytics queries

• Handling administrative operations

• Supporting numerous unrelated business workflows

Incorrect

WorkspaceRepository

↓

Workspace CRUD

↓

Project Reports

↓

Task Analytics

↓

User Statistics

↓

Notification Queries

↓

Dashboard Data

When a repository begins serving multiple business capabilities, it should be decomposed into smaller, focused repositories, repository extensions, or dedicated read repositories.

Repository growth should follow aggregate boundaries rather than application convenience.

---

# 155. Duplicate Query Logic

Query logic should not be duplicated across repositories.

Examples of duplication include:

• Identical filtering logic

• Repeated join conditions

• Copy-pasted JPQL

• Similar native queries

• Repeated Specifications

Instead, extract reusable components such as:

✓ Specifications

✓ Predicate builders

✓ Repository extensions

✓ Shared projections

Reducing duplication improves maintainability and decreases the likelihood of inconsistent query behavior.

Each query should have a single authoritative implementation whenever practical.

---

# 156. Repository as a Utility Class

Repositories should contain only persistence-related operations.

Avoid adding helper methods unrelated to data access.

Examples of inappropriate repository methods include:

• String formatting

• Date calculations

• Permission evaluation

• Business rule evaluation

• File handling

• JSON processing

• Email generation

• AI prompt construction

Repositories are not general-purpose utility classes.

Any logic that does not directly contribute to retrieving or persisting data belongs elsewhere in the architecture.

Maintaining strict repository responsibilities improves readability and preserves architectural boundaries.

---

# 157. Repository Code Review Checklist

Before approving a repository implementation, developers should verify the following:

✓ Does the repository own exactly one aggregate?

✓ Is the repository responsible only for persistence?

✓ Is all business logic located in services?

✓ Are query names clear and intention-revealing?

✓ Are derived queries preferred where appropriate?

✓ Are complex queries properly documented?

✓ Are projections used instead of full entities when appropriate?

✓ Are large result sets paginated or streamed?

✓ Are filtering and sorting delegated to the database?

✓ Are frequently executed queries index-friendly?

✓ Are N+1 query problems avoided?

✓ Are transactions managed exclusively by the service layer?

✓ Is DTO mapping absent from the repository?

✓ Are persistence exceptions allowed to propagate naturally?

✓ Are repository methods readable, maintainable, and easy to test?

✓ Does the repository comply with SprintForge's architectural standards?

Every repository should be reviewed not only for correctness, but also for maintainability, scalability, and architectural consistency.

---

End of Part 8
```
# SprintForge Engineering Standard
# Repository Layer

# Part 9
# Reference Templates & Implementation Blueprints

---

# 158. Purpose

This chapter provides standard repository templates and implementation blueprints for SprintForge.

The goal is to ensure that every repository follows consistent architectural conventions while remaining easy to understand, maintain, and extend.

These templates represent the preferred repository design for new development.

---

# 159. Standard Repository Template

Every repository should follow the standard declaration pattern.

Example

```java
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

}
```

Repositories should remain lightweight.

Avoid adding unnecessary methods.

Leverage inherited Spring Data functionality whenever possible.

---

# 160. Standard Query Method Template

Derived query methods should follow Spring Data naming conventions.

Examples

```java
Optional<Workspace> findBySlug(String slug);

boolean existsBySlug(String slug);

List<Project> findAllByWorkspaceId(UUID workspaceId);

long countByWorkspaceId(UUID workspaceId);
```

Method names should clearly describe the persistence operation.

---

# 161. Standard Pagination Template

Repository methods returning potentially large datasets should support pagination.

Example

```java
Page<Task> findByWorkspaceId(
    UUID workspaceId,
    Pageable pageable
);
```

Pagination should be preferred over returning unbounded collections.

---

# 162. Standard Projection Template

Use projections when only partial entity data is required.

Example

```java
public interface WorkspaceSummary {

    UUID getId();

    String getName();

    String getSlug();

}
```

Projection queries reduce memory usage and improve query performance.

---

# 163. Standard Specification Template

Dynamic filtering should be implemented using reusable Specifications.

Example

```java
public class TaskSpecifications {

    public static Specification<Task> hasStatus(TaskStatus status) {

        return (root, query, cb) ->
                cb.equal(root.get("status"), status);

    }

}
```

Each Specification should represent one filtering rule.

---

# 164. Standard Custom Query Template

When derived queries become insufficient, prefer readable JPQL.

Example

```java
@Query("""
    SELECT t
    FROM Task t
    WHERE t.workspace.id = :workspaceId
      AND t.status = :status
""")
List<Task> findActiveTasks(
    UUID workspaceId,
    TaskStatus status
);
```

Prefer named parameters over positional parameters.

---

# 165. Standard Entity Graph Template

Use Entity Graphs to prevent unnecessary lazy-loading.

Example

```java
@EntityGraph(
    attributePaths = {
        "owner",
        "members"
    }
)
Optional<Workspace> findWithMembersById(UUID id);
```

Entity Graphs should retrieve only the relationships required by the use case.

---

# 166. Standard Locking Query Template

Locking behavior should be explicit.

Example

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Project> findById(UUID id);
```

Repository methods acquiring locks should clearly document their purpose.

---

# 167. Standard Bulk Update Template

Bulk updates should remain focused and well documented.

Example

```java
@Modifying
@Query("""
    UPDATE Task t
       SET t.status = :status
     WHERE t.sprint.id = :sprintId
""")
int updateSprintTasks(
    UUID sprintId,
    TaskStatus status
);
```

Bulk updates should only be used when they provide measurable performance benefits.

---

# 168. Standard Native Query Template

Native SQL should remain exceptional.

Example

```java
@Query(
    value = "...",
    nativeQuery = true
)
List<ReportRow> generateReport();
```

Every native query should explain why JPQL was insufficient.

---

# 169. Standard Repository Review Blueprint

Before introducing a repository, verify:

✓ Owns one aggregate

✓ Repository interface is lightweight

✓ Query names follow conventions

✓ Uses derived queries where appropriate

✓ Pagination supported

✓ Projections considered

✓ Specifications reusable

✓ Native SQL justified

✓ No business logic

✓ No DTO mapping

✓ No validation

✓ No service dependencies

✓ Transactions owned by services

✓ Performance considerations reviewed

---

# 170. AI Repository Generation Checklist

AI-generated repositories should satisfy the following requirements:

✓ Correct repository naming

✓ Extends appropriate Spring Data interfaces

✓ One aggregate ownership

✓ Clear derived query names

✓ Proper use of Optional

✓ Pagination for large datasets

✓ Specifications for dynamic filtering

✓ Projections where appropriate

✓ No business logic

✓ No validation

✓ No DTO mapping

✓ No service dependencies

✓ Documentation for complex queries

✓ Compliance with SprintForge repository standards

AI-generated repository code should require minimal manual correction before review.

---

# 171. Future Evolution

The repository layer should remain adaptable as SprintForge evolves.

Future enhancements may include:

• Read replicas

• CQRS read repositories

• Outbox persistence

• Multi-tenancy

• Database sharding

• Polyglot persistence

• Advanced search engines

Repository interfaces should evolve without disrupting higher architectural layers.

---

# 172. Final Principles

SprintForge repositories exist to provide a clean, reliable, and efficient abstraction over persistence.

Every repository should embody the following principles:

✓ Persistence-focused

✓ Simple

✓ Predictable

✓ Efficient

✓ Maintainable

✓ Consistent

✓ Testable

✓ Database-aware

Repositories should never become business services, utility classes, or workflow coordinators.

By maintaining strict separation between persistence and business logic, SprintForge ensures a scalable, maintainable, and enterprise-ready architecture.

---

End of Part 9

End of Repository Layer Engineering Standard
