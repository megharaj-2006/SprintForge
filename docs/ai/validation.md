
# SprintForge Engineering Standard
# Validation

# Part 1 – Validation Philosophy & Architecture

## 1. Purpose

Validation ensures that only correct, consistent, and meaningful data enters the SprintForge application.

Its goals are to:

- Prevent invalid data from reaching business logic
- Protect database integrity
- Improve API usability
- Enforce domain rules
- Reduce runtime errors

Validation is the application's first line of defense against incorrect input.

---

## 2. Validation Philosophy

SprintForge follows a **layered validation strategy**.

Each layer validates only the concerns it owns.

```text id="9mq74t"
Client

↓

Controller

↓

DTO Validation

↓

Service Validation

↓

Domain Rules

↓

Database Constraints
```

Validation responsibilities should never overlap unnecessarily.

---

## 3. Goals

Validation should be:

✓ Declarative

✓ Predictable

✓ Consistent

✓ Reusable

✓ Testable

✓ Secure

✓ Easy to understand

---

## 4. Validation Layers

SprintForge validates data in five stages.

### Layer 1

Client-side validation

Purpose:

Improve user experience.

Never trusted.

---

### Layer 2

DTO Validation

Purpose:

Validate request structure.

---

### Layer 3

Business Validation

Purpose:

Enforce business rules.

---

### Layer 4

Domain Validation

Purpose:

Protect aggregate consistency.

---

### Layer 5

Database Constraints

Purpose:

Guarantee persistence integrity.

---

## 5. Validation Flow

```text id="4nsqib"
HTTP Request

↓

DTO

↓

Bean Validation

↓

Controller

↓

Service

↓

Business Validation

↓

Repository

↓

Database Constraints
```

Each layer builds upon the previous one.

---

## 6. Validation Responsibilities

Validation is responsible for:

✓ Required fields

✓ Formats

✓ Length limits

✓ Numeric ranges

✓ Collections

✓ Business invariants

✓ Cross-field rules

✓ Aggregate consistency

---

## 7. Responsibilities That Do NOT Belong

Validation should never:

✗ Execute workflows

✗ Modify state

✗ Perform persistence

✗ Replace authorization

✗ Hide programming errors

---

## 8. Fail Fast

SprintForge follows fail-fast validation.

Reject invalid requests immediately.

Example:

```text id="jlwmv1"
Invalid Request

↓

400 Bad Request

↓

Stop Processing
```

Never continue processing invalid data.

---

## 9. Declarative First

Prefer declarative validation over imperative validation.

Good:

```java id="jlwmv2"
@NotBlank

@Email

@Size
```

Avoid repetitive `if` statements where annotations express the rule clearly.

---

## 10. Validation Ownership

| Concern | Layer |
|----------|-------|
| Email format | DTO |
| Required field | DTO |
| Username uniqueness | Service |
| Workspace archived | Service |
| Database uniqueness | Database |
| Aggregate invariant | Domain |

Each rule belongs to exactly one owner.

---

## 11. Separation of Concerns

Validation does not replace business logic.

Example:

Checking that a field is present is validation.

Determining whether a user may archive a workspace is business logic.

---

## 12. Design Goals

Every validation rule should be:

- Specific
- Reusable
- Easy to test
- Clearly named
- Independent where possible

---

## 13. Validation Strategy

SprintForge uses:

- Bean Validation (Jakarta Validation)
- Custom Validators
- Service-layer validation
- Database constraints

Each serves a distinct purpose.

---

## 14. Philosophy Checklist

✓ Fail fast

✓ Declarative first

✓ Layered validation

✓ Reusable rules

✓ No duplicated responsibility

---

# Part 2 – DTO Validation (Bean Validation)

## 15. Purpose

DTO validation ensures incoming requests are structurally correct before reaching business logic.

DTOs define API contracts.

---

## 16. Bean Validation

SprintForge standardizes on Jakarta Bean Validation.

Common annotations include:

- `@NotNull`
- `@NotBlank`
- `@NotEmpty`
- `@Size`
- `@Email`
- `@Pattern`
- `@Min`
- `@Max`
- `@Positive`
- `@Past`
- `@Future`

Prefer standard annotations before creating custom validators.

---

## 17. Required Fields

Use:

```java id="jlwmv3"
@NotNull
```

for required object references.

Use:

```java id="jlwmv4"
@NotBlank
```

for required text.

---

## 18. String Validation

Examples:

```java id="jlwmv5"
@NotBlank

@Size(max = 100)

@Pattern(...)
```

Avoid manual length checking inside controllers.

---

## 19. Numeric Validation

Examples:

```java id="jlwmv6"
@Positive

@Min(1)

@Max(100)
```

Express numeric constraints declaratively.

---

## 20. Enum Validation

Prefer enum types over raw strings.

If strings are unavoidable, validate against allowed values using a custom constraint rather than arbitrary comparisons.

---

## 21. Collection Validation

Example:

```java id="jlwmv7"
@NotEmpty

@Size(max = 20)
```

Validate collection size as part of the API contract.

---

## 22. Nested Validation

Use:

```java id="jlwmv8"
@Valid
```

to validate nested DTOs recursively.

Without `@Valid`, nested objects are not automatically validated.

---

## 23. Validation Messages

Messages should be:

✓ Clear

✓ User-friendly

✓ Actionable

Bad:

```text id="jlwmv9"
Constraint violation
```

Good:

```text id="jlwmv10"
Workspace name must not be blank.
```

---

## 24. Controller Integration

Validation executes automatically.

Example:

```java id="jlwmv11"
public ResponseEntity<?> create(

    @Valid

    @RequestBody

    WorkspaceCreateRequest request
)
```

Controllers should not manually invoke validators.

---

## 25. DTO Validation Checklist

✓ Bean Validation

✓ Standard annotations

✓ `@Valid`

✓ Clear messages

✓ No manual validation

---

# Part 3 – Business Validation

## 26. Purpose

Business validation enforces rules that depend on application state.

These rules cannot be expressed with annotations alone.

---

## 27. Service Ownership

Business validation belongs in the service layer.

Examples:

- Username already exists
- Sprint already started
- Workspace archived
- User exceeds project limit

Controllers should not perform these checks.

---

## 28. Database Queries

Business validation may require repository access.

Example:

```text id="jlwmv12"
Email

↓

Repository

↓

Already Exists?
```

Such validation belongs in services, not DTOs.

---

## 29. State Validation

Example:

```text id="jlwmv13"
Task

↓

Completed

↓

Move to TODO?

↓

Reject
```

These rules depend on current state and belong to the domain/service layer.

---

## 30. Authorization vs Validation

Example:

Validation:

"Workspace exists."

Authorization:

"User may edit workspace."

Do not mix the two concerns.

---

## 31. Aggregate Validation

Some rules involve multiple entities.

Example:

```text id="jlwmv14"
Sprint

↓

Contains Closed Tasks

↓

Cannot Finish
```

These belong to aggregate business logic.

---

## 32. Cross-Repository Validation

Example:

```text id="jlwmv15"
Invite

↓

User

↓

Workspace

↓

Membership
```

Validation spanning repositories belongs in services.

---

## 33. Throwing Exceptions

Business validation failures should throw meaningful application exceptions.

Example:

```java id="jlwmv16"
throw new WorkspaceArchivedException(...);
```

---

## 34. Business Validation Checklist

✓ Service-owned

✓ Repository access allowed

✓ State-aware

✓ Aggregate-aware

✓ Throws business exceptions

---

# Part 4 – Custom Validation

## 35. Purpose

When Bean Validation cannot express a rule, create reusable custom validators.

Avoid scattering identical validation logic throughout the codebase.

---

## 36. When to Create One

Good candidates:

- Strong password policy
- Unique username format
- Valid workspace slug
- Color hex code
- Business identifier formats

Avoid custom validators for one-off checks.

---

## 37. Annotation Design

A custom validator should include:

- Annotation
- Validator implementation
- Clear message
- Documentation

It should integrate seamlessly with Bean Validation.

---

## 38. Cross-Field Validation

Some rules require comparing multiple fields.

Example:

```text id="jlwmv17"
startDate

<

endDate
```

This belongs in a class-level validator.

---

## 39. Conditional Validation

Example:

```text id="jlwmv18"
Reminder Enabled

↓

Reminder Date Required
```

Conditional rules should remain reusable and testable.

---

## 40. Reuse

One validator should support multiple DTOs whenever practical.

Avoid duplicating identical validation logic.

---

## 41. Performance

Custom validators should remain lightweight.

Avoid:

- Heavy database queries
- Network calls
- Complex computations

Infrastructure-dependent validation belongs in services.

---

## 42. Testing

Every custom validator should include unit tests covering:

- Valid input
- Invalid input
- Edge cases
- Null handling

---

## 43. Documentation

Every custom annotation should clearly describe:

- Purpose
- Constraints
- Expected usage

---

## 44. Custom Validation Checklist

✓ Reusable

✓ Lightweight

✓ Tested

✓ Documented

✓ Bean Validation compatible

---

# Part 5 – Validation Groups & Advanced Validation

## 45. Purpose

Some validation rules differ depending on the operation being performed.

Validation Groups allow different constraints to apply in different contexts.

---

## 46. Create vs Update

Example:

Create:

```text id="jlwmv19"
Password

Required
```

Update:

```text id="jlwmv20"
Password

Optional
```

Validation groups prevent duplicate DTOs for simple variations.

---

## 47. Partial Updates

PATCH operations should validate only supplied fields.

Combine validation groups with partial update semantics where appropriate.

---

## 48. Group Interfaces

Define marker interfaces to represent validation contexts.

Example:

```java id="jlwmv21"
CreateGroup

UpdateGroup
```

These interfaces contain no methods and exist solely for grouping constraints.

---

## 49. Controller Usage

Apply the appropriate validation group at the controller boundary.

This ensures the correct set of constraints is enforced for each endpoint.

---

## 50. When to Use Groups

Use validation groups sparingly.

Prefer separate request DTOs when create and update operations differ significantly.

Groups are best suited for minor variations in validation rules.

---

## 51. Composition

Validation annotations can be composed into reusable meta-annotations.

Example:

A `@ValidWorkspaceName` annotation could combine:

- `@NotBlank`
- `@Size`
- `@Pattern`

This reduces duplication and improves consistency.

---

## 52. Validation Sequence

Complex validation can be performed in stages using validation group sequences.

Earlier groups should complete successfully before later groups execute.

This helps avoid cascading validation noise.

---

## 53. Midpoint Summary

At this stage, SprintForge validation provides:

- Layered validation architecture
- Bean Validation at the API boundary
- Service-layer business validation
- Reusable custom validators
- Validation groups for advanced scenarios
- Fail-fast request processing

---
Perfect. This completes the `validation.md` handbook.

---

# SprintForge Engineering Standard
# Validation

# Part 6 – Database & Domain Validation

## 54. Purpose

Validation does not end at the API boundary.

Even after request validation and business validation succeed, the application must protect its domain model and persistence layer from invalid state.

---

## 55. Domain Invariants

A **domain invariant** is a rule that must always remain true throughout the lifetime of an aggregate.

Examples:

- A completed task cannot become "In Progress."
- A sprint cannot end before it starts.
- A workspace owner cannot be removed without assigning another owner.

Domain invariants belong to the domain model or service layer—not the controller.

---

## 56. Entity Integrity

Entities should never exist in an invalid state.

Example:

```text id="9jd2wv"
Task

↓

Title = ""

↓

Invalid Entity
```

The application should prevent such objects from being persisted.

---

## 57. Database Constraints

The database provides the final layer of validation.

Typical constraints include:

- PRIMARY KEY
- FOREIGN KEY
- UNIQUE
- NOT NULL
- CHECK (when supported)

Database constraints complement—not replace—application validation.

---

## 58. Uniqueness

Uniqueness should be validated at two levels:

1. Service layer (better user experience)
2. Database constraint (guaranteed integrity)

Example:

```text id="jlwmv22"
Email Exists?

↓

Reject

↓

UNIQUE Constraint
```

Never rely solely on application checks because race conditions can still occur.

---

## 59. Referential Integrity

Relationships should always remain valid.

Example:

```text id="jlwmv23"
Task

↓

Workspace Exists?

↓

Persist
```

Foreign key constraints ensure orphan records cannot be created.

---

## 60. Transaction Boundaries

Some validation depends on multiple updates occurring together.

Example:

```text id="jlwmv24"
Archive Workspace

↓

Archive Tasks

↓

Commit
```

Transactional consistency protects domain validity.

---

## 61. Optimistic Locking

Concurrent updates should be validated using optimistic locking.

Example:

```text id="jlwmv25"
Version

↓

Mismatch

↓

Reject Update
```

This prevents accidental overwrites.

---

## 62. Constraint Violations

Persistence exceptions should be translated into meaningful business exceptions.

Example:

```text id="jlwmv26"
ConstraintViolationException

↓

DuplicateEmailException
```

Clients should not receive raw database errors.

---

## 63. Domain Validation Checklist

✓ Domain invariants

✓ Database constraints

✓ Referential integrity

✓ Optimistic locking

✓ Exception translation

---

# Part 7 – Security & Validation Best Practices

## 64. Purpose

Validation is a security mechanism as much as it is a correctness mechanism.

Every input from an external client should be considered untrusted until validated.

---

## 65. Never Trust Client Input

Even if the frontend validates input, the backend must repeat validation.

Example:

```text id="jlwmv27"
Frontend

↓

Modified Request

↓

Backend Validation
```

Client-side validation improves usability—not security.

---

## 66. Input Sanitization

Validation verifies correctness.

Sanitization removes or normalizes unwanted content.

Examples:

- Trimming whitespace
- Normalizing Unicode
- Escaping output (where appropriate)

Avoid mixing sanitization with validation when they serve different purposes.

---

## 67. SQL Injection Protection

Never construct SQL using user input.

Always rely on:

- JPA
- Hibernate
- Parameterized queries

Validation is not a substitute for safe query construction.

---

## 68. XSS Considerations

Validation cannot fully prevent Cross-Site Scripting (XSS).

Combine validation with:

- Output encoding
- Safe HTML rendering
- Content Security Policy (CSP)

---

## 69. File Upload Validation

Uploaded files should be validated for:

- Size
- Content type
- File extension
- Virus scanning (where applicable)

Never trust the client-provided MIME type alone.

---

## 70. URL Validation

External URLs should be validated before storage or processing.

Consider:

- Scheme (`https`)
- Allowed domains (if applicable)
- Maximum length

---

## 71. Rate Limiting

Validation should not become a denial-of-service vector.

Combine validation with:

- Authentication
- Rate limiting
- Request size limits

---

## 72. Logging Validation Failures

Validation failures should be logged appropriately.

Do not log:

- Passwords
- Tokens
- Sensitive personal information

---

## 73. Security Checklist

✓ Backend validation

✓ Safe queries

✓ File validation

✓ URL validation

✓ Sensitive data protected

---

# Part 8 – Validation Anti-Patterns

## 74. Purpose

Poor validation design creates duplicated logic, inconsistent behavior, and security vulnerabilities.

Avoid the following anti-patterns.

---

## 75. Validation in Controllers

Bad:

```java id="jlwmv28"
if (request.getName() == null) {
    ...
}
```

Use Bean Validation instead.

---

## 76. Duplicate Rules

Avoid implementing the same rule in multiple layers.

Example:

```text id="jlwmv29"
Controller

↓

Service

↓

Repository
```

The same validation should have a single owner.

---

## 77. Database-Only Validation

Do not rely solely on database constraints.

Example:

```text id="jlwmv30"
Save

↓

Constraint Error

↓

500
```

Prefer catching invalid input before persistence.

---

## 78. Business Logic in Validators

Validators should not execute workflows.

Bad:

```text id="jlwmv31"
Validate

↓

Create Workspace
```

Validation checks state—it does not change it.

---

## 79. Heavy Validators

Avoid validators that perform:

- Large database queries
- Network requests
- Expensive computations

Such checks belong in services.

---

## 80. Silent Validation

Never ignore validation failures.

Every failed validation should produce a clear, actionable error response.

---

## 81. Generic Messages

Avoid:

```text id="jlwmv32"
Invalid Input
```

Prefer:

```text id="jlwmv33"
Workspace name must not exceed 100 characters.
```

Specific messages improve developer and user experience.

---

## 82. Over-Validation

Do not validate information that is:

- Generated by the server
- Already guaranteed by the application
- Impossible to violate

Keep validation focused on meaningful constraints.

---

## 83. Anti-Pattern Checklist

Avoid:

✗ Manual controller validation

✗ Duplicate rules

✗ Heavy validators

✗ Database-only validation

✗ Generic error messages

✗ Silent failures

---

# Part 9 – Reference Templates & Implementation Blueprints

## 84. Request DTO

```java id="jlwmv34"
public record WorkspaceCreateRequest(

    @NotBlank
    @Size(max = 100)
    String name,

    @Size(max = 500)
    String description

) {}
```

---

## 85. Nested Validation

```java id="jlwmv35"
public record TaskRequest(

    @Valid

    UserRequest assignee

) {}
```

---

## 86. Custom Validator

```java id="jlwmv36"
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(...)
public @interface ValidWorkspaceSlug {

}
```

---

## 87. Validator Implementation

```java id="jlwmv37"
public class WorkspaceSlugValidator
        implements ConstraintValidator<...> {

}
```

---

## 88. Validation Group

```java id="jlwmv38"
public interface CreateGroup {

}
```

---

## 89. Controller Example

```java id="jlwmv39"
@PostMapping
public ResponseEntity<?> create(

    @Validated(CreateGroup.class)

    @RequestBody

    WorkspaceCreateRequest request

) {

}
```

---

## 90. Validation Flow Blueprint

```text id="jlwmv40"
HTTP Request

↓

DTO

↓

Bean Validation

↓

Service Validation

↓

Repository

↓

Database
```

---

## 91. Blueprint Checklist

✓ Bean Validation

✓ Custom validators

✓ Business validation

✓ Database constraints

✓ Unit tests

---

# Part 10 – Governance & Final Principles

## 92. Purpose

Validation standards ensure consistent enforcement of data quality across every SprintForge module.

Every feature should validate input in the same predictable manner.

---

## 93. Ownership

Validation ownership should remain clear.

| Rule | Owner |
|------|-------|
| Required fields | DTO |
| Format | DTO |
| Cross-field rules | Custom Validator |
| Business state | Service |
| Aggregate invariants | Domain |
| Persistence integrity | Database |

A rule should have one primary owner.

---

## 94. Code Review Requirements

Every validation review should verify:

- Appropriate validation layer
- Standard annotations where possible
- Reusable custom validators
- Meaningful error messages
- No duplicated rules
- Tests included

---

## 95. Documentation

Validation constraints form part of the API contract.

Public APIs should document:

- Required fields
- Length limits
- Accepted formats
- Allowed ranges
- Validation error responses

OpenAPI documentation should reflect validation annotations.

---

## 96. Testing Expectations

Validation tests should verify:

- Valid input
- Invalid input
- Boundary values
- Null handling
- Cross-field rules
- Custom validators
- Business validation
- Database constraint translation

Testing should cover both success and failure scenarios.

---

## 97. Evolution Strategy

As SprintForge evolves:

- Reuse existing validators where possible
- Introduce new constraints intentionally
- Remove obsolete validation rules
- Keep validation messages consistent

Avoid accumulating conflicting or redundant rules.

---

## 98. AI-Assisted Development

AI tools can generate DTOs, validators, and annotations, but generated validation must be reviewed to ensure it:

- Uses standard Bean Validation annotations where applicable
- Places rules in the correct architectural layer
- Produces clear error messages
- Avoids duplicate validation
- Keeps validators lightweight
- Aligns with SprintForge naming conventions

---

## 99. Final Validation Principles

Every SprintForge validation rule should be:

✓ Declarative when possible

✓ Layer-appropriate

✓ Reusable

✓ Lightweight

✓ Testable

✓ Secure

✓ Consistent

✓ Well documented

✓ Easy to understand

✓ Focused on a single concern

---

## 100. Validation Compliance Checklist

Before merging validation changes:

### Architecture

✓ Rule belongs to the correct layer

✓ No duplicated responsibility

✓ Business validation separated from DTO validation

### DTO Validation

✓ Standard annotations used

✓ `@Valid` applied where required

✓ Clear validation messages

### Business Validation

✓ Service-layer ownership

✓ Meaningful exceptions

✓ Repository access only where necessary

### Security

✓ Backend validation

✓ File validation (if applicable)

✓ Sensitive data protected

### Quality

✓ Unit tests included

✓ Documentation updated

✓ OpenAPI reflects constraints

---

## 101. Closing Statement

Validation is the foundation of SprintForge's data integrity.

By enforcing rules at the appropriate architectural layer—from request DTOs through business services to database constraints—the application maintains correctness without duplicating responsibilities.

A disciplined validation strategy improves API usability, strengthens security, protects the domain model, and ensures that every layer of the application can rely on receiving well-formed, meaningful data.

---

