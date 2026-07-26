
# SprintForge Engineering Standard
# Mapper Layer

# Part 1 – Mapper Philosophy & Architecture

## 1. Purpose

The Mapper layer is responsible for translating objects between different layers of the application while preserving the separation of concerns.

In SprintForge, mappers primarily convert:

```text
Request DTO → Entity
Entity → Response DTO
```

Mappers ensure that neither the API layer nor the persistence layer depends directly on each other.

---

## 2. Mapper Philosophy

A mapper is a translator, not a business component.

Its sole responsibility is object transformation.

A mapper should:

- Transform data
- Copy fields
- Convert types
- Delegate nested mappings

A mapper should **never**:

- Execute business rules
- Access repositories
- Call external APIs
- Perform authorization
- Make business decisions

---

## 3. Why Mappers Exist

Without mappers:

```text
Controller
    ↓
Entity
```

The controller becomes tightly coupled to persistence.

With mappers:

```text
Controller
    ↓
Request DTO
    ↓
Mapper
    ↓
Entity
```

Benefits:

- API independence
- Domain protection
- Easier testing
- Better maintainability
- Smaller services
- Stable contracts

---

## 4. Layer Position

The mapper sits between DTOs and Entities.

```text
Client
    ↓
Controller
    ↓
Request DTO
    ↓
Mapper
    ↓
Entity
    ↓
Service
    ↓
Repository
```

And in reverse:

```text
Repository
    ↓
Entity
    ↓
Mapper
    ↓
Response DTO
    ↓
Controller
    ↓
Client
```

---

## 5. Responsibilities

A mapper is responsible for:

✓ Field mapping

✓ Nested object mapping

✓ Enum conversion

✓ Collection mapping

✓ Primitive conversion

✓ Value Object conversion

✓ Flattening

✓ Expansion

---

## 6. Responsibilities That Do NOT Belong in Mappers

Never:

✗ Query database

✗ Call repository

✗ Inject services for business logic

✗ Validate permissions

✗ Throw business exceptions

✗ Execute workflows

---

## 7. Entity Independence

Entities should never know DTOs exist.

DTOs should never know Entities exist.

Only the mapper understands both.

```text
DTO ← Mapper → Entity
```

---

## 8. One-Way Data Flow

Request Flow

```text
Request DTO
    ↓
Mapper
    ↓
Entity
```

Response Flow

```text
Entity
    ↓
Mapper
    ↓
Response DTO
```

Never expose entities directly to controllers.

---

## 9. Framework Choice

SprintForge standardizes on **MapStruct**.

Reasons:

- Compile-time code generation
- No reflection
- Excellent performance
- Spring integration
- Type safety
- Easy debugging

Reflection-based mapping libraries are discouraged.

---

## 10. Compile-Time vs Runtime Mapping

Compile-time generation catches mapping problems during compilation.

Benefits:

✓ Faster execution

✓ IDE support

✓ Compile-time errors

✓ Easier refactoring

---

## 11. Dependency Injection

All mappers should be Spring-managed.

Example:

```java
@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
}
```

Avoid manually instantiating mapper implementations.

---

## 12. Mapper Lifecycle

Mappers are stateless.

They should:

- Hold no mutable state
- Store no cache
- Maintain no session

They should behave as pure functions.

---

## 13. Mapper Design Goals

Every mapper should be:

- Stateless
- Deterministic
- Testable
- Lightweight
- Reusable
- Easy to read
- Framework-independent (except MapStruct annotations)

---

## 14. Architecture Principles

SprintForge follows:

```text
One Aggregate

↓

One Mapper

↓

One Responsibility
```

Avoid mega-mappers that convert dozens of unrelated objects.

---

## 15. Mapper Philosophy Checklist

Before writing a mapper:

✓ Is it only transforming data?

✓ Is business logic absent?

✓ Is it stateless?

✓ Is it reusable?

✓ Is it compile-time generated?

---

# Part 2 – Mapper Organization & Structure

## 16. Package Structure

Each feature owns its mapper.

```text
workspace/

    mapper/

        WorkspaceMapper

task/

    mapper/

        TaskMapper
```

Avoid centralized mapper packages.

---

## 17. Naming Convention

Always:

```
<EntityName>Mapper
```

Examples:

- UserMapper
- WorkspaceMapper
- TaskMapper
- SprintMapper

Avoid:

- MapperUtil
- EntityConverter
- ObjectTransformer

---

## 18. One Mapper Per Aggregate

Each aggregate root owns one primary mapper.

Example:

```text
Workspace

↓

WorkspaceMapper
```

Child entities may have dedicated mappers if complexity justifies it.

---

## 19. Interface-Based Design

Mappers should be interfaces.

MapStruct generates implementations.

Good:

```java
public interface WorkspaceMapper
```

Avoid manually writing implementation classes unless using decorators.

---

## 20. Shared Configuration

Common configuration belongs in:

```java
@MapperConfig
```

Shared settings include:

- componentModel
- injection strategy
- null handling
- unmapped target policy

---

## 21. Injection Strategy

Prefer constructor injection.

MapStruct:

```java
injectionStrategy = InjectionStrategy.CONSTRUCTOR
```

Avoid field injection.

---

## 22. Mapper Dependencies

Nested mappers should be declared using:

```java
uses = {
    UserMapper.class,
    LabelMapper.class
}
```

Avoid manually invoking other mapper implementations.

---

## 23. Visibility

Public mappers only when needed.

Keep helper methods private or default.

Expose only the mapping API.

---

## 24. Package Ownership

Each module owns its mapper.

Example:

```text
workspace/

mapper/

dto/

entity/
```

Cross-module mapping should be minimized.

---

## 25. Organization Checklist

✓ One mapper per aggregate

✓ Feature-local

✓ Interface

✓ Shared config

✓ Constructor injection

✓ Proper naming

---

# Part 3 – Mapping Strategies

## 26. Request → Entity Mapping

Create requests become new entities.

```text
CreateRequest

↓

Mapper

↓

Entity
```

Only client-controlled fields are mapped.

---

## 27. Entity → Response Mapping

Entities become response DTOs.

Server-managed values may now be included.

Example:

- id
- createdAt
- owner
- version (if exposed)

---

## 28. Update Mapping

Updates modify an existing entity.

Example:

```java
void updateEntity(
    UpdateRequest dto,
    @MappingTarget Workspace entity
);
```

The existing entity remains managed by JPA.

---

## 29. Patch Mapping

Patch updates only supplied values.

Use:

```java
NullValuePropertyMappingStrategy.IGNORE
```

Missing values should never overwrite existing ones.

---

## 30. Nested Mapping

Delegate nested objects.

Example:

```text
Task

↓

Assignee

↓

UserMapper
```

Never duplicate nested conversion logic.

---

## 31. Collection Mapping

Collections should map automatically.

```text
List<Entity>

↓

List<ResponseDTO>
```

Reuse mapping methods.

Avoid manual loops whenever MapStruct can generate them.

---

## 32. Enum Mapping

Enums should map explicitly.

Example:

```text
TaskPriority

↓

PriorityResponse
```

Avoid ordinal-based conversions.

---

## 33. Value Object Mapping

Value Objects may require custom conversions.

Example:

```text
EmailAddress

↓

String
```

And back again.

---

## 34. Primitive Conversion

Examples:

- UUID ↔ String
- Instant ↔ ISO String
- BigDecimal ↔ Double (only when justified)

Conversions should be centralized.

---

## 35. Null Handling

Decide consistent behavior.

Preferred:

Null input

↓

Null output

Avoid unexpected NullPointerExceptions.

---

## 36. Flattening

Sometimes nested entities become flat DTOs.

Example:

```text
Task

↓

Workspace

↓

workspaceName
```

Flattening improves API usability.

---

## 37. Expansion

Conversely:

```text
workspaceId

↓

WorkspaceSummary
```

Only when clients require richer responses.

---

## 38. Partial Mapping

Map only required fields.

Do not map entire entities unnecessarily.

---

## 39. Strategy Checklist

✓ Nested delegation

✓ Consistent null handling

✓ Patch support

✓ Flatten where useful

✓ Explicit enum conversion

---

# Part 4 – Advanced Mapping

## 40. Cycle Avoidance

Entities may contain circular references.

Example:

```text
Workspace

↓

Tasks

↓

Workspace
```

Response DTOs should break cycles.

---

## 41. Multiple Source Mapping

Sometimes multiple objects create one DTO.

Example:

```text
Task

+

User

↓

TaskResponse
```

MapStruct supports multiple parameters.

---

## 42. Context Objects

Use `@Context` for shared mapping state.

Examples:

- Locale
- Timezone
- Currency
- Cycle avoidance context

Avoid abusing context for business services.

---

## 43. Qualified Mapping

When multiple conversions exist:

Use:

```java
@Named
```

or

```java
@Qualifier
```

to select the appropriate mapping method.

---

## 44. BeforeMapping

Initialize data before mapping.

Example:

- Normalize values
- Prepare target

Avoid business logic.

---

## 45. AfterMapping

Use for adjustments after mapping.

Examples:

- Derived display fields
- Calculated labels
- Formatting

Do not modify business state.

---

## 46. Decorators

Use decorators only when generated mapping is insufficient.

Typical uses:

- External formatting
- Localization
- Special conversion logic

Business rules still belong elsewhere.

---

## 47. Immutable Mapping

MapStruct works well with Java Records.

Prefer immutable response DTOs.

---

## 48. MappingTarget

Update existing entities using:

```java
@MappingTarget
```

Never replace managed JPA entities unnecessarily.

---

## 49. Advanced Mapping Checklist

✓ No cycles

✓ Proper context

✓ Qualified methods

✓ Safe updates

✓ Immutable DTO support

---

# Part 5 – MapStruct Standards

## 50. Standard Configuration

All mappers should inherit a shared configuration.

Example:

```java
@Mapper(config = GlobalMapperConfig.class)
```

Avoid repeating identical settings.

---

## 51. Mapping Annotations

Use:

- `@Mapping`
- `@Mappings` (when needed)
- `@BeanMapping`

Only when automatic mapping is insufficient.

---

## 52. Ignored Fields

Explicitly ignore server-managed fields.

Example:

```java
@Mapping(target = "id", ignore = true)
@Mapping(target = "createdAt", ignore = true)
```

Never trust client input for these values.

---

## 53. Inherited Configuration

Reuse mapping definitions.

Use:

```java
@InheritConfiguration
```

to avoid duplication.

---

## 54. Inverse Configuration

Reverse mappings using:

```java
@InheritInverseConfiguration
```

when appropriate.

---

## 55. Null Strategies

Standardize:

- `NullValuePropertyMappingStrategy`
- `NullValueCheckStrategy`

Apply globally unless a mapper requires different behavior.

---

## 56. Unmapped Fields

Set:

```java
unmappedTargetPolicy = ReportingPolicy.ERROR
```

Compilation should fail when required mappings are missing.

---

## 57. Expression Mapping

Use expressions sparingly.

Prefer dedicated mapping methods over complex inline expressions.

---

## 58. Default Methods

Small helper conversions may be implemented as default methods inside mapper interfaces.

Keep them pure and reusable.

---

## 59. Testing Expectations

Every mapper should have unit tests verifying:

- Standard mapping
- Null handling
- Collection mapping
- Nested mapping
- Update mapping
- Patch behavior

---

## 60. MapStruct Checklist

✓ Shared configuration

✓ ReportingPolicy.ERROR

✓ Explicit ignores

✓ Constructor injection

✓ Tested mappings

✓ Minimal custom expressions

---

End of part 5

---

# SprintForge Engineering Standard
# Mapper Layer

# Part 6 – Performance & Best Practices

## 61. Purpose

The Mapper layer should remain lightweight and efficient. Since mappers are invoked frequently throughout the application, poor mapping practices can impact performance, memory usage, and maintainability.

---

## 62. Compile-Time Code Generation

SprintForge uses **MapStruct** because it generates mapping code during compilation.

Advantages:

- No reflection
- Minimal memory overhead
- Near hand-written performance
- IDE-friendly
- Compile-time validation

Generated code should be preferred over runtime mapping libraries.

---

## 63. Avoid Reflection-Based Mappers

Reflection-based frameworks (e.g., ModelMapper, Dozer) introduce:

- Runtime overhead
- Hidden mapping logic
- Harder debugging
- Reduced type safety

SprintForge standardizes on compile-time mapping for predictability and performance.

---

## 64. Lazy Loading Awareness

Mapping entities with lazy-loaded relationships can unintentionally trigger additional database queries.

Example:

```text id="x9o5ab"
Workspace
    ↓
Tasks (LAZY)
    ↓
Comments (LAZY)
```

When mapping, ensure required relationships are fetched intentionally to avoid unexpected database access.

---

## 65. Prevent N+1 Problems

Mapping itself should never cause the N+1 query problem.

Instead:

- Fetch required associations using repositories
- Use `JOIN FETCH`, `@EntityGraph`, or projections where appropriate
- Map only already-loaded data

Performance issues should be solved before the mapper is invoked.

---

## 66. Map Only Required Fields

Do not map entire entities when only a subset is needed.

Bad:

```text id="7hz3ru"
Entity
↓
100 fields
↓
DTO
```

Good:

```text id="qk2hfd"
Entity
↓
8 required fields
↓
Summary DTO
```

Smaller DTOs reduce serialization cost and improve API performance.

---

## 67. Efficient Collection Mapping

MapStruct efficiently handles collections.

Prefer:

```text id="lqv9du"
List<Entity>

↓

List<ResponseDTO>
```

Avoid writing manual loops unless specialized behavior is required.

---

## 68. Avoid Duplicate Mapping

Never duplicate identical mapping logic across multiple mappers.

Extract shared conversion methods into reusable mappers or utility methods.

---

## 69. Mapper Purity

Mapping methods should behave like mathematical functions.

Input

↓

Mapper

↓

Output

The same input should always produce the same output.

Avoid hidden side effects.

---

## 70. Performance Checklist

✓ Compile-time mapping

✓ No reflection

✓ No repository access

✓ No N+1 queries

✓ Small DTOs

✓ Efficient collections

✓ Pure functions

---

# Part 7 – Security & Data Exposure

## 71. Purpose

Mappers play a critical role in preventing accidental data leaks.

Every mapped field represents an explicit decision to expose information.

---

## 72. Explicit Field Mapping

Do not rely blindly on automatic mapping for sensitive objects.

Explicitly ignore fields that should remain internal.

Example:

```java id="e6bn9a"
@Mapping(target = "passwordHash", ignore = true)
@Mapping(target = "refreshToken", ignore = true)
```

---

## 73. Protect Sensitive Data

Never expose:

- passwordHash
- refreshToken
- secretKey
- OTP
- verificationCode
- internal audit data
- security metadata

These fields should never appear in response DTOs.

---

## 74. Ignore Server-Managed Fields

When mapping request DTOs to entities, ignore:

- id
- createdAt
- updatedAt
- createdBy
- updatedBy
- version
- deleted

These values must always be controlled by the server.

---

## 75. Prevent Over-Posting

Clients should not be able to update restricted fields.

Example:

```text id="g1xl3w"
Request DTO

↓

Mapper

↓

Entity
```

Only explicitly permitted fields should be copied.

---

## 76. Role-Specific Mapping

Some responses vary depending on user permissions.

Examples:

- AdminResponse
- UserResponse
- PublicResponse

Separate DTOs are preferred over conditional mapping logic.

---

## 77. Data Minimization

Expose only what the client requires.

Example:

Dashboard

↓

WorkspaceSummaryResponse

instead of

WorkspaceDetailResponse

Smaller responses improve both security and performance.

---

## 78. Internal vs External Models

Internal entities often contain implementation details.

The mapper acts as a filter, ensuring only public information crosses the API boundary.

---

## 79. Security Checklist

✓ Sensitive fields ignored

✓ Server-managed fields protected

✓ Explicit mappings

✓ No entity exposure

✓ Minimal data transfer

---

# Part 8 – Mapper Anti-Patterns & Code Smells

## 80. Purpose

Poor mapper design leads to maintenance problems, hidden bugs, and architectural violations.

This section highlights common anti-patterns.

---

## 81. Business Logic in Mappers

Bad:

```java id="18xg3v"
if (user.isPremium()) {
    applyDiscount();
}
```

Business decisions belong in Services or Domain Models—not mappers.

---

## 82. Repository Calls

Never inject repositories into mappers.

Bad:

```java id="r1vwrv"
UserRepository
```

inside

```java id="m1vf9w"
UserMapper
```

Fetching data is not a mapper responsibility.

---

## 83. Service Injection

Avoid injecting services into mappers.

This introduces hidden dependencies and mixes responsibilities.

---

## 84. Giant Mappers

Avoid mappers responsible for unrelated aggregates.

Bad:

```text id="ccr42m"
ApplicationMapper
```

Good:

```text id="mw9b0r"
UserMapper
TaskMapper
WorkspaceMapper
```

---

## 85. Circular Mapping

Avoid:

```text id="7jlwmj"
Workspace

↓

Task

↓

Workspace

↓

Task
```

Break cycles using summary DTOs or specialized mappings.

---

## 86. Manual Mapping Everywhere

Repeated field-by-field mapping inside controllers and services is error-prone.

Centralize mapping in dedicated MapStruct interfaces.

---

## 87. Entity Leakage

Never return entities directly from controllers.

Always map:

```text id="qthtgg"
Entity

↓

Response DTO
```

---

## 88. Duplicate Conversion Logic

The same conversion should not exist in multiple mappers.

Extract reusable methods.

---

## 89. Ignoring Compiler Warnings

Do not suppress unmapped field warnings without justification.

Prefer:

```java id="vlrjlwm"
ReportingPolicy.ERROR
```

to ensure mapping completeness.

---

## 90. Anti-Pattern Checklist

Avoid:

✗ Business logic

✗ Repository access

✗ Service injection

✗ Circular mappings

✗ Entity exposure

✗ Giant mappers

✗ Duplicate logic

✗ Reflection-based mapping

---

# Part 9 – Reference Templates & Implementation Blueprints

## 91. Standard Mapper

```java id="8mdzjv"
@Mapper(
    config = GlobalMapperConfig.class
)
public interface WorkspaceMapper {

    Workspace toEntity(WorkspaceCreateRequest request);

    WorkspaceResponse toResponse(Workspace entity);
}
```

---

## 92. Update Mapper

```java id="woe07e"
void updateEntity(
    WorkspaceUpdateRequest request,
    @MappingTarget Workspace entity
);
```

---

## 93. Patch Mapper

```java id="vudlv7"
@BeanMapping(
    nullValuePropertyMappingStrategy =
        NullValuePropertyMappingStrategy.IGNORE
)
```

Only non-null values overwrite existing data.

---

## 94. Collection Mapping

```java id="4h2jya"
List<TaskResponse> toResponses(
    List<Task> tasks
);
```

MapStruct automatically generates collection mappings.

---

## 95. Nested Mapping

```java id="hdm5fr"
@Mapper(
    uses = {
        UserMapper.class,
        LabelMapper.class
    }
)
```

Delegate nested conversions rather than duplicating logic.

---

## 96. Enum Mapping

```java id="b0yfp6"
Priority

↓

PriorityResponse
```

Prefer explicit mapping over ordinal conversions.

---

## 97. Shared Mapper Configuration

```java id="mceh6u"
@MapperConfig(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
```

All mappers should inherit from this configuration.

---

## 98. Testing Blueprint

Every mapper should be tested for:

- Request → Entity
- Entity → Response
- Update mapping
- Patch mapping
- Null handling
- Collection mapping
- Nested mapping

---

## 99. Blueprint Checklist

✓ Shared config

✓ Constructor injection

✓ Explicit ignores

✓ Tested

✓ Reusable

✓ Stateless

---

# Part 10 – Governance & Final Principles

## 100. Purpose

Mapper standards ensure consistency across all SprintForge modules.

Every mapper should follow the same architectural conventions regardless of feature.

---

## 101. Ownership

Each feature owns its mapper.

Example:

```text id="jlwmks"
workspace/

mapper/

WorkspaceMapper
```

Avoid shared "utility" mappers spanning unrelated modules.

---

## 102. Code Review Requirements

Every mapper review should verify:

- Correct naming
- Proper package placement
- No business logic
- No repository access
- Shared configuration
- Explicit ignores
- Test coverage

---

## 103. Documentation

Public mapping methods should clearly communicate their intent through descriptive method names and, where beneficial, Javadoc.

Complex custom mappings should include comments explaining why they exist.

---

## 104. Testing Expectations

Mapper tests should verify:

- Standard mappings
- Update mappings
- Patch behavior
- Nested objects
- Collections
- Null handling
- Custom conversions
- Ignored fields

Generated code should never be assumed correct without verification.

---

## 105. Evolution Strategy

As entities and DTOs evolve:

- Add new mappings intentionally
- Remove obsolete mappings
- Keep configuration synchronized
- Review unmapped field warnings

Avoid accumulating dead mapping methods.

---

## 106. AI-Assisted Development

AI tools can generate mapper interfaces quickly, but generated code must be reviewed to ensure it:

- Uses `GlobalMapperConfig`
- Ignores server-managed fields
- Avoids business logic
- Delegates nested mappings
- Handles updates and patches correctly
- Follows SprintForge naming conventions

AI should accelerate development, not replace architectural review.

---

## 107. Final Mapper Principles

Every SprintForge mapper should be:

✓ Stateless

✓ Deterministic

✓ Compile-time generated

✓ Pure

✓ Easy to test

✓ Easy to maintain

✓ Independent of business logic

✓ Focused on a single aggregate

✓ Secure

✓ Reusable

---

## 108. Mapper Compliance Checklist

Before merging a new mapper:

### Architecture

✓ One mapper per aggregate

✓ Feature-local package

✓ Interface-based design

✓ Shared configuration

### Mapping

✓ Request → Entity

✓ Entity → Response

✓ Update mapping

✓ Patch mapping (if applicable)

✓ Collection mapping

### Security

✓ Sensitive fields ignored

✓ Server-managed fields protected

✓ No entity leakage

### Quality

✓ No business logic

✓ No repository access

✓ No service injection

✓ Unit tests included

✓ Compiler warnings resolved

---

## 109. Closing Statement

The Mapper layer is the bridge between SprintForge's public API and its internal domain model.

DTOs define what the outside world sees.

Entities define how the business operates.

Mappers ensure these two worlds remain independent.

By centralizing all object transformation in dedicated, stateless, compile-time generated mappers, SprintForge achieves:

- Stable API contracts
- Clean architecture
- Strong separation of concerns
- Improved maintainability
- Better performance
- Safer evolution of both the domain model and the API

A well-designed Mapper layer allows the application to grow without coupling persistence models to external consumers, making it a foundational component of a scalable Spring Boot architecture.

---
