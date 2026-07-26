# SprintForge Engineering Standard
# Testing

# Part 1 – Testing Philosophy & Architecture

## 1. Purpose

Testing verifies that SprintForge behaves correctly under expected and unexpected conditions.

A comprehensive testing strategy helps:

- Prevent regressions
- Validate business logic
- Ensure API reliability
- Support safe refactoring
- Increase developer confidence

Testing is an integral part of development, not an afterthought.

---

## 2. Testing Philosophy

SprintForge follows a **testing pyramid**.

```text
            End-to-End
         Integration Tests
          Unit Tests
```

Most tests should be unit tests.

Integration tests should validate component interaction.

End-to-end tests should remain focused on critical user flows.

---

## 3. Goals

Testing should be:

✓ Fast

✓ Repeatable

✓ Deterministic

✓ Isolated

✓ Maintainable

✓ Readable

✓ Reliable

---

## 4. Testing Layers

SprintForge validates correctness at multiple levels.

```text
Client

↓

Controller Tests

↓

Service Tests

↓

Repository Tests

↓

Database
```

Every architectural layer has corresponding tests.

---

## 5. Responsibilities

Testing is responsible for:

✓ Verifying correctness

✓ Detecting regressions

✓ Documenting behavior

✓ Supporting refactoring

✓ Preventing production defects

---

## 6. Responsibilities That Do NOT Belong

Tests should never:

✗ Replace documentation

✗ Depend on production data

✗ Depend on execution order

✗ Share mutable state

✗ Test framework internals

---

## 7. Test Categories

SprintForge uses:

- Unit Tests
- Integration Tests
- Repository Tests
- Controller Tests
- Security Tests
- Validation Tests

Each category has a distinct purpose.

---

## 8. Test Independence

Every test should execute independently.

Bad:

```text
Test A

↓

Test B
```

Good:

```text
Test A

Test B

Test C
```

No test should rely on another test having executed first.

---

## 9. Deterministic Tests

Tests should always produce the same result given the same inputs.

Avoid dependencies on:

- Current time (unless controlled)
- Network availability
- Random values
- External services

---

## 10. Fast Feedback

Developers should be able to execute unit tests frequently.

Long-running tests should be reserved for integration or end-to-end pipelines.

---

## 11. Test Naming

Use descriptive names.

Examples:

```text
createWorkspace_shouldPersistWorkspace()

archiveSprint_shouldThrowWhenAlreadyCompleted()

login_shouldReturnJwtForValidCredentials()
```

Names should describe expected behavior.

---

## 12. Design Goals

Every test should be:

- Clear
- Small
- Focused
- Independent
- Repeatable

---

## 13. Testing Strategy

SprintForge emphasizes:

- Behavior over implementation
- Public API testing
- Realistic scenarios
- Minimal mocking where appropriate

---

## 14. Philosophy Checklist

✓ Testing pyramid

✓ Independent tests

✓ Fast execution

✓ Deterministic

✓ Readable

---

# Part 2 – Unit Testing

## 15. Purpose

Unit tests verify a single class or component in isolation.

Dependencies should be replaced with test doubles where appropriate.

---

## 16. Scope

Typical unit test targets:

- Services
- Validators
- Mappers
- Utility classes
- Business logic

Avoid unit testing simple getters/setters.

---

## 17. Framework

SprintForge standardizes on:

- JUnit 5
- Mockito
- AssertJ (recommended)

Avoid mixing multiple assertion libraries without justification.

---

## 18. Test Structure

Preferred structure:

```text
Arrange

↓

Act

↓

Assert
```

Also known as the AAA pattern.

---

## 19. Arrange

Prepare:

- Inputs
- Mock behavior
- Test objects

Only create what is necessary.

---

## 20. Act

Invoke exactly one unit of behavior.

Avoid testing multiple unrelated operations in a single test.

---

## 21. Assert

Verify:

- Return values
- State changes
- Interactions (when appropriate)
- Exceptions

Assertions should be precise and meaningful.

---

## 22. Mocking

Mock external dependencies only.

Typical examples:

- Repositories
- Email services
- Storage services
- External APIs

Avoid mocking the class under test.

---

## 23. Verifications

Verify interactions only when behavior depends on them.

Do not overuse interaction verification for simple return-value tests.

---

## 24. Exception Testing

Verify both:

- Exception type
- Exception message (where meaningful)

Example:

```text
assertThrows()

↓

BusinessException
```

---

## 25. Unit Testing Checklist

✓ AAA structure

✓ JUnit 5

✓ Mockito

✓ Small scope

✓ Clear assertions

---

# Part 3 – Service Testing

## 26. Purpose

Service tests verify business logic independently of the web and persistence layers.

This is where most application logic should be tested.

---

## 27. Dependencies

Typical service dependencies:

```text
Service

↓

Repository (Mock)

↓

Mapper (Real or Mock)

↓

Validator
```

Repositories are usually mocked.

Simple MapStruct mappers may be used as real implementations.

---

## 28. Business Rules

Every important business rule deserves dedicated tests.

Examples:

- Cannot archive completed sprint
- Duplicate workspace rejected
- Task assignment validated

---

## 29. Happy Path

Every service method should include at least one successful scenario.

---

## 30. Failure Scenarios

Test expected failures.

Examples:

- Resource not found
- Invalid state
- Duplicate entity
- Permission denied

---

## 31. Edge Cases

Include:

- Empty collections
- Boundary values
- Null handling (where applicable)
- Maximum limits

---

## 32. Repository Verification

Verify repository interactions only when relevant.

Example:

```text
save()

↓

Called Once
```

Avoid excessive interaction assertions.

---

## 33. Transaction Behavior

Service tests verify behavior.

Transactional infrastructure itself generally belongs in integration tests.

---

## 34. Service Testing Checklist

✓ Business rules

✓ Happy path

✓ Failure path

✓ Edge cases

✓ Repository mocked

---

# Part 4 – Repository & Database Testing

## 35. Purpose

Repository tests verify persistence behavior against a real database.

Mocking repositories cannot validate SQL generation, entity mappings, or constraints.

---

## 36. Scope

Repository tests verify:

- CRUD operations
- Custom queries
- Relationships
- Constraints
- Entity mappings

---

## 37. Framework

Use:

```text
@DataJpaTest
```

for focused persistence testing.

Keep repository tests isolated from the web layer.

---

## 38. Database Choice

Prefer:

- Testcontainers
- PostgreSQL

Avoid H2 if production behavior differs significantly from PostgreSQL.

Testing against the same database engine reduces surprises.

---

## 39. Entity Relationships

Verify:

- One-to-One
- One-to-Many
- Many-to-Many

Relationships should persist and load correctly.

---

## 40. Constraints

Test:

- Unique constraints
- Foreign keys
- Not-null constraints
- Optimistic locking

These behaviors cannot be fully validated through mocks.

---

## 41. Query Methods

Every custom query should have tests.

Examples:

```text
findByEmail()

findActiveTasks()

findByWorkspace()
```

---

## 42. Transactions

Repository tests should verify rollback behavior where appropriate.

Each test should leave the database clean.

---

## 43. Repository Checklist

✓ Real database

✓ Entity mappings

✓ Constraints

✓ Queries

✓ Transactions

---

# Part 5 – Controller & API Testing

## 44. Purpose

Controller tests verify the HTTP layer.

They ensure requests are correctly mapped to services and responses follow API contracts.

---

## 45. Scope

Controller tests verify:

- Routing
- Request validation
- Status codes
- Response DTOs
- Error handling

Business logic should remain in service tests.

---

## 46. Framework

Use:

```text
@WebMvcTest
```

for isolated controller testing.

Mock service dependencies.

---

## 47. MockMvc

Standardize on:

```text
MockMvc
```

for testing REST endpoints.

Verify:

- HTTP method
- Status
- Headers
- JSON body

---

## 48. Validation

Controller tests should verify Bean Validation.

Example:

```text
Invalid DTO

↓

400 Bad Request
```

---

## 49. Error Responses

Verify standardized error responses.

Examples:

- 400
- 401
- 403
- 404
- 409

Ensure the response structure matches the application's `ErrorResponse` contract.

---

## 50. JSON Assertions

Verify:

- Required fields
- Missing fields
- Nested objects
- Collections
- Pagination

Avoid asserting irrelevant implementation details.

---

## 51. Security

Protected endpoints should verify:

- Authentication required
- Authorization enforced
- Public endpoints accessible

---

## 52. API Contracts

Controllers should consistently return:

- Correct HTTP status
- Correct DTO
- Correct headers
- Predictable JSON

API tests act as executable documentation.

---

## 53. Midpoint Summary

At this stage, SprintForge testing includes:

- Testing philosophy and architecture
- Unit testing standards
- Service-layer testing
- Repository testing with real databases
- Controller testing with MockMvc
- API contract verification

---

Perfect. This completes the `testing.md` handbook.

---

# SprintForge Engineering Standard
# Testing

# Part 6 – Integration & Security Testing

## 54. Purpose

Integration tests verify that multiple application components work correctly together.

Unlike unit tests, integration tests execute real Spring components and validate complete application behavior.

---

## 55. Scope

Typical integration test scope:

```text
Controller

↓

Service

↓

Repository

↓

Database
```

These tests verify component interaction rather than isolated business logic.

---

## 56. Framework

Use:

```text
@SpringBootTest
```

for full application integration tests.

Only use this when interaction between multiple Spring components is required.

---

## 57. Real Infrastructure

Whenever practical, integration tests should use:

- PostgreSQL (Testcontainers)
- Spring Security
- JPA
- Transaction management

Avoid replacing critical infrastructure with mocks.

---

## 58. HTTP Testing

For full-stack API testing use:

- MockMvc
- WebTestClient (if using WebFlux)

Verify:

- Authentication
- Authorization
- Validation
- Persistence
- Response contracts

---

## 59. Transaction Isolation

Each integration test should execute independently.

Rollback changes after each test unless persistence between tests is intentionally required.

---

## 60. Service Integration

Verify complete workflows.

Example:

```text
Create Workspace

↓

Persist

↓

Retrieve

↓

Update

↓

Delete
```

Testing complete business workflows increases confidence.

---

## 61. Security Testing

Security deserves dedicated tests.

Verify:

- Login success
- Login failure
- Expired JWT
- Invalid JWT
- Missing JWT
- Role restrictions
- Ownership checks

---

## 62. Authorization Matrix

Each protected endpoint should be tested for:

| User | Expected |
|------|----------|
| Anonymous | 401 |
| Authenticated | Allowed/Denied |
| Wrong Role | 403 |
| Owner | Allowed |
| Non-owner | Denied |

---

## 63. Integration Checklist

✓ Full Spring Context

✓ Real Database

✓ Security Enabled

✓ Complete Workflow

✓ Independent Tests

---

# Part 7 – Test Data Management & Testcontainers

## 64. Purpose

Reliable tests require predictable data.

Every test should create only the data it needs.

---

## 65. Test Data Principles

Test data should be:

- Small
- Explicit
- Readable
- Independent
- Disposable

Avoid massive shared datasets.

---

## 66. Test Builders

Prefer builder patterns for creating test objects.

Example:

```text
WorkspaceBuilder

↓

build()
```

Builders reduce duplication and improve readability.

---

## 67. Factory Methods

Reusable factory methods simplify common test scenarios.

Example:

```text
TestUsers.admin()

TestUsers.normalUser()
```

Factories should produce valid default objects.

---

## 68. Avoid Shared Mutable Data

Bad:

```text
Global Test User

↓

Modified

↓

Breaks Other Tests
```

Each test should own its data.

---

## 69. Testcontainers

SprintForge standardizes on **Testcontainers** for integration tests.

Benefits:

- Production-like database
- Consistent environment
- Isolation
- No local database dependency

---

## 70. PostgreSQL Container

Repository and integration tests should execute against PostgreSQL rather than an in-memory substitute whenever production behavior matters.

This improves confidence in SQL, constraints, and migrations.

---

## 71. Database Initialization

Initialize test databases using:

- Flyway migrations
- Seed scripts (when necessary)
- Test builders

Keep initialization deterministic.

---

## 72. Cleaning Up

Each test should leave the environment clean.

Avoid relying on manual cleanup.

Use:

- Transaction rollback
- Fresh containers
- Dedicated cleanup utilities

---

## 73. Test Data Checklist

✓ Builders

✓ Factories

✓ Testcontainers

✓ Independent data

✓ Automatic cleanup

---

# Part 8 – Testing Anti-Patterns

## 74. Purpose

Poor testing practices reduce confidence and increase maintenance costs.

Avoid the following anti-patterns.

---

## 75. Testing Implementation Instead of Behavior

Bad:

```text
Verify Private Method
```

Good:

```text
Verify Public Behavior
```

Tests should validate observable outcomes rather than internal implementation.

---

## 76. Over-Mocking

Avoid mocking every dependency.

Mock only external collaborators that are outside the scope of the test.

Excessive mocking creates fragile tests.

---

## 77. Huge Test Methods

Avoid tests that verify many unrelated scenarios.

Bad:

```text
One Test

↓

10 Behaviors
```

Each test should focus on a single behavior.

---

## 78. Magic Values

Avoid unexplained literals.

Bad:

```java
assertEquals(17, result);
```

Prefer descriptive constants or builders.

---

## 79. Sleeping in Tests

Never rely on:

```java
Thread.sleep(...)
```

Use proper synchronization or await mechanisms when testing asynchronous code.

---

## 80. Testing Private Methods

Private methods are implementation details.

Test through the public API instead.

---

## 81. Ignoring Edge Cases

Every important feature should include:

- Boundary values
- Empty input
- Invalid input
- Null handling (where applicable)

Edge cases often reveal hidden defects.

---

## 82. Flaky Tests

Avoid tests that fail intermittently.

Common causes:

- Timing issues
- Shared state
- External dependencies
- Random values

Flaky tests undermine confidence in the test suite.

---

## 83. Anti-Pattern Checklist

Avoid:

✗ Over-mocking

✗ Large tests

✗ Sleep statements

✗ Shared state

✗ Flaky tests

✗ Testing private methods

✗ Magic values

---

# Part 9 – Reference Templates & Implementation Blueprints

## 84. Unit Test Structure

```text
Arrange

↓

Act

↓

Assert
```

---

## 85. Service Test Blueprint

```text
Mock Repository

↓

Call Service

↓

Assert Result

↓

Verify Interaction
```

---

## 86. Repository Test Blueprint

```text
Persist Entity

↓

Query Database

↓

Assert Result
```

---

## 87. Controller Test Blueprint

```text
MockMvc

↓

HTTP Request

↓

JSON Response

↓

Assertions
```

---

## 88. Integration Test Blueprint

```text
@SpringBootTest

↓

Controller

↓

Service

↓

Repository

↓

Database
```

---

## 89. Security Test Blueprint

```text
Anonymous

↓

401

Authenticated

↓

200

Wrong Role

↓

403
```

---

## 90. Test Data Blueprint

```text
Builder

↓

Factory

↓

Valid Object
```

---

## 91. Testing Checklist

✓ Unit Tests

✓ Integration Tests

✓ Repository Tests

✓ Controller Tests

✓ Security Tests

✓ Testcontainers

---

# Part 10 – Governance & Final Principles

## 92. Purpose

Testing standards ensure every SprintForge module maintains a consistent level of quality.

Testing is a shared engineering responsibility rather than an optional activity.

---

## 93. Ownership

Each feature owns its own tests.

Example:

```text
workspace/

controller/
service/
repository/

test/
```

Tests should evolve alongside production code.

---

## 94. Code Review Requirements

Every pull request should verify:

- New functionality has tests
- Existing tests remain valid
- Test names are descriptive
- No flaky behavior
- Appropriate test level chosen
- No unnecessary mocks

---

## 95. Documentation

Tests serve as executable documentation.

Complex business rules should be reflected through clear test cases.

When behavior changes, update both the implementation and the associated tests.

---

## 96. Testing Expectations

Minimum expectations:

### Unit Tests

- Business logic
- Validators
- Utilities
- Mappers

### Integration Tests

- Critical workflows
- Persistence
- Transactions

### Controller Tests

- HTTP status
- Validation
- Error responses
- JSON structure

### Security Tests

- Authentication
- Authorization
- Role restrictions

---

## 97. CI/CD Integration

Every pull request should execute the automated test suite.

Recommended pipeline order:

```text
Compile

↓

Static Analysis

↓

Unit Tests

↓

Integration Tests

↓

Package

↓

Deploy
```

Production deployments should not proceed if required tests fail.

---

## 98. AI-Assisted Development

AI tools can generate test classes, fixtures, and assertions, but generated tests must be reviewed to ensure they:

- Verify behavior rather than implementation
- Use meaningful test data
- Follow the AAA pattern
- Cover success and failure scenarios
- Remain deterministic
- Avoid brittle assertions

AI should improve testing productivity, not reduce test quality.

---

## 99. Final Testing Principles

Every SprintForge test should be:

✓ Independent

✓ Fast

✓ Deterministic

✓ Readable

✓ Maintainable

✓ Focused

✓ Repeatable

✓ Reliable

✓ Behavior-driven

✓ Easy to debug

---

## 100. Testing Compliance Checklist

Before merging new functionality:

### Unit Tests

✓ Business logic covered

✓ Edge cases included

✓ Exceptions verified

### Integration Tests

✓ Critical workflows covered

✓ Real infrastructure where appropriate

✓ Transactions verified

### Controller Tests

✓ Status codes verified

✓ Response contracts verified

✓ Validation covered

### Security Tests

✓ Authentication tested

✓ Authorization tested

✓ Protected endpoints verified

### Quality

✓ No flaky tests

✓ No shared mutable state

✓ Testcontainers used where appropriate

✓ CI pipeline passes

---

## 101. Closing Statement

Testing is SprintForge's primary mechanism for preserving correctness as the codebase evolves.

A balanced testing strategy—combining focused unit tests, realistic integration tests, API contract verification, repository testing, and security validation—provides confidence that changes do not introduce regressions.

By emphasizing deterministic, maintainable, behavior-focused tests, SprintForge enables rapid development while maintaining the reliability expected of an enterprise-grade backend.

---

