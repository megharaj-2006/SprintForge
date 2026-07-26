# SprintForge Engineering Standard
# DTO Layer

# Part 1
# DTO Philosophy & API Boundaries

---

# 1. Purpose

Data Transfer Objects (DTOs) define the public contract between SprintForge and its clients.

They represent the data exchanged across application boundaries while protecting the internal domain model from direct exposure.

DTOs provide:

✓ Stable API contracts

✓ Clear separation of concerns

✓ Security through controlled data exposure

✓ Independent evolution of APIs and domain models

The DTO layer acts as the gateway between external consumers and the application's business logic.

---

# 2. DTO Philosophy

DTOs exist solely for transferring data.

They should not contain:

• Business logic

• Persistence logic

• Repository access

• Service dependencies

• Domain workflows

A DTO should describe data, not behavior.

---

# 3. Why DTOs Exist

Directly exposing entities creates tight coupling between the API and the persistence model.

Problems include:

• Internal fields become public

• Database changes break APIs

• Security risks

• Recursive serialization

• Difficult API versioning

DTOs isolate the API from internal implementation details.

---

# 4. API Boundary

The DTO layer forms the boundary between external systems and the internal domain.

Typical request flow:

Client

↓

HTTP Request

↓

Request DTO

↓

Controller

↓

Service

↓

Entity

↓

Repository

↓

Database

Typical response flow:

Database

↓

Repository

↓

Entity

↓

Service

↓

Response DTO

↓

HTTP Response

↓

Client

Entities should never cross this boundary.

---

# 5. DTO Responsibilities

DTOs are responsible for:

✓ Carrying request data

✓ Carrying response data

✓ Supporting validation

✓ Defining API contracts

✓ Hiding internal implementation

They are not responsible for executing business rules.

---

# 6. Separation from Entities

Entities and DTOs represent different concerns.

Entities

↓

Business Model

↓

Persistence

↓

Behavior

DTOs

↓

API Contract

↓

Serialization

↓

Data Transfer

Changes to one should not require changes to the other unless the API contract itself changes.

---

# 7. API Contract Stability

A published DTO becomes part of the public API.

Changes should be made carefully.

Prefer:

✓ Additive changes

✓ Optional fields

✓ Versioned evolution

Avoid:

• Removing existing fields

• Renaming fields

• Changing field semantics without versioning

API stability reduces breaking changes for clients.

---

# 8. DTO Lifecycle

DTOs exist only during data transfer.

Typical lifecycle:

Receive Request

↓

Deserialize

↓

Validate

↓

Map to Entity

↓

Business Processing

↓

Map to Response DTO

↓

Serialize

↓

Return Response

DTOs should never be persisted.

---

# 9. Framework Independence

DTOs should remain independent of persistence technologies.

DTOs should not include:

@Entity

@Table

@Repository

Persistence-specific behavior

They may use serialization and validation annotations where appropriate.

---

# 10. Immutability Philosophy

Whenever practical, DTOs should be immutable.

Immutable DTOs provide:

✓ Thread safety

✓ Predictable behavior

✓ Simpler reasoning

✓ Reduced accidental mutation

Mutable DTOs should only be used when required by framework constraints.

---

# 11. Serialization Responsibility

DTOs define how information is exchanged over the API.

They should be serializable by the chosen serialization framework without exposing unnecessary implementation details.

Serialization concerns belong to DTOs rather than entities.

---

# 12. Consumer-Centric Design

DTOs should be designed around consumer needs rather than database structure.

Example

Dashboard Response

↓

Only Dashboard Data

Not

↓

Entire Workspace Entity

The API should expose exactly what consumers require and nothing more.

---

# 13. Backward Compatibility

API consumers may not upgrade immediately.

Therefore:

Prefer adding new optional fields.

Avoid removing existing fields.

Deprecate before deletion.

Version when necessary.

Backward compatibility should be treated as a core API design principle.

---

# 14. Explicit Data Exposure

Every field included in a DTO should have a deliberate purpose.

Ask:

Does the client actually need this field?

If the answer is no, do not expose it.

Minimize exposed data by default.

---

# 15. DTO Design Goals

SprintForge DTOs should be:

✓ Lightweight

✓ Immutable where practical

✓ Easy to Serialize

✓ Consumer-Focused

✓ Stable

✓ Framework-Friendly

✓ Independent of Domain Logic

Every DTO should provide a clear, minimal, and stable representation of the data required by API consumers.

---
````md id="g4n8xp"
---

# 16. DTO vs Domain Object

SprintForge distinguishes between several types of objects, each with a specific responsibility.

| Object Type | Primary Responsibility |
|-------------|------------------------|
| DTO | Transfer data across application boundaries |
| Entity | Model persistent business concepts and behavior |
| Value Object | Represent immutable business values without identity |
| Domain Event | Represent something significant that has already happened within the domain |

Each type serves a different purpose.

Examples

User Registration Request

↓

Request DTO

User

↓

Entity

EmailAddress

↓

Value Object

UserRegisteredEvent

↓

Domain Event

These object types should not be used interchangeably.

Keeping their responsibilities separate improves maintainability and reduces coupling.

---

# 17. Contract-First API Design

SprintForge follows a Contract-First approach for API development.

Before implementing controllers or services, developers should first define:

✓ Request DTOs

✓ Response DTOs

✓ Validation rules

✓ Error responses

✓ API documentation

Recommended workflow:

Business Requirement

↓

Design DTO Contract

↓

Review API

↓

Implement Controller

↓

Implement Service

↓

Implement Mapping

↓

Implement Tests

Designing the contract first promotes consistency and encourages consumer-focused APIs rather than exposing internal implementation details.

---

# 18. API Evolution Strategy

API contracts evolve over time.

SprintForge favors gradual, backward-compatible evolution.

Preferred changes include:

✓ Adding optional fields

✓ Introducing new DTOs

✓ Adding new endpoints

✓ Deprecating outdated fields before removal

Avoid:

• Removing existing fields without versioning

• Renaming public fields

• Changing field semantics

• Changing data types unexpectedly

When breaking changes become necessary:

Current API

↓

Deprecate

↓

Introduce New Version

↓

Migrate Consumers

↓

Remove Old Version

API evolution should be predictable and well documented.

---

# 19. Documentation Standards

Every public DTO should be documented.

Documentation should describe:

Purpose

Field meanings

Validation constraints

Allowed values

Optional vs required fields

Special business notes

Examples

WorkspaceCreateRequest

Purpose

Creates a new workspace.

Name

Required, 3–100 characters.

Description

Optional.

Visibility

PUBLIC or PRIVATE.

Good documentation improves:

✓ API usability

✓ Consumer onboarding

✓ SDK generation

✓ OpenAPI accuracy

Documentation should remain synchronized with the implementation.

---

# 20. DTO Philosophy Checklist

Before introducing a new DTO, developers should verify the following:

✓ Does the DTO represent an API contract?

✓ Does it expose only the required data?

✓ Is it independent of entities?

✓ Is it free of business logic?

✓ Is it independent of persistence concerns?

✓ Are unnecessary fields omitted?

✓ Is the DTO consumer-focused rather than database-focused?

✓ Is the DTO appropriately immutable?

✓ Is it documented?

✓ Is it prepared for future evolution?

✓ Does it comply with SprintForge API standards?

Every DTO should exist for a clear communication purpose and remain a lightweight, stable representation of data exchanged between the application and its clients.

---

End of Part 1

# SprintForge Engineering Standard
# DTO Layer

# Part 2
# DTO Types & Classification

---

# 21. Purpose

Not all DTOs serve the same purpose.

SprintForge classifies DTOs based on their responsibility rather than the feature they belong to.

Each DTO should have exactly one responsibility.

Different API operations should generally use different DTOs.

---

# 22. DTO Classification Philosophy

DTOs should be designed around API operations.

Avoid creating one DTO that attempts to satisfy every endpoint.

Instead:

Create Request

↓

CreateRequest DTO

Update Request

↓

UpdateRequest DTO

Search Request

↓

SearchRequest DTO

Detail Response

↓

DetailResponse DTO

Summary Response

↓

SummaryResponse DTO

Purpose-specific DTOs improve readability and reduce unnecessary data transfer.

---

# 23. Request DTOs

Request DTOs carry data from clients into the application.

Examples:

WorkspaceCreateRequest

TaskUpdateRequest

SprintStartRequest

LoginRequest

Responsibilities:

✓ Accept client input

✓ Support validation

✓ Represent request payloads

Request DTOs should never contain response-only fields.

---

# 24. Response DTOs

Response DTOs carry data from the application to clients.

Examples:

WorkspaceResponse

TaskResponse

ProjectResponse

UserProfileResponse

Responsibilities:

✓ Return processed data

✓ Hide internal implementation

✓ Define API responses

Response DTOs should expose only the information required by the client.

---

# 25. Create Request DTOs

Create DTOs are used exclusively when creating new resources.

Typical fields include:

Required business data

Optional initialization values

Configuration options

Create DTOs should never contain:

Database identifiers

Audit fields

Version fields

Server-managed values

The server is responsible for generating these values.

---

# 26. Update Request DTOs

Update DTOs modify existing resources.

They contain only fields that clients are allowed to update.

Examples

TaskUpdateRequest

WorkspaceUpdateRequest

UserProfileUpdateRequest

Update DTOs should exclude immutable information such as:

id

createdAt

createdBy

---

# 27. Patch DTOs

Patch DTOs support partial updates.

Unlike Update DTOs, every field is optional.

Example

TaskPatchRequest

↓

Only Priority Updated

↓

Everything Else Unchanged

Patch DTOs should clearly distinguish between:

Field omitted

Field explicitly set to null (if supported)

Patch operations should preserve unspecified data.

---

# 28. Summary Response DTOs

Summary DTOs provide lightweight representations for collections and listings.

Examples

WorkspaceSummaryResponse

ProjectSummaryResponse

TaskSummaryResponse

Typical use cases:

Dashboard

Search Results

Lists

Tables

Summary DTOs should avoid expensive nested data.

---

# 29. Detail Response DTOs

Detail DTOs provide comprehensive information for a single resource.

Example

TaskDetailResponse

↓

Task

↓

Comments

↓

Labels

↓

Attachments

↓

Assignee

↓

Metadata

Detail DTOs should include additional information only when required by the client.

---

# 30. Search DTOs

Search DTOs encapsulate search criteria.

Typical fields include:

Keyword

Filters

Sort Order

Page Number

Page Size

Search DTOs should remain independent of persistence implementation.

---

# 31. Filter DTOs

Filter DTOs represent reusable filtering criteria.

Examples

TaskFilter

WorkspaceFilter

SprintFilter

Filters may include:

Status

Priority

Assignee

Labels

Date Range

Filter DTOs improve consistency across multiple search endpoints.

---

# 32. Pagination DTOs

Pagination DTOs represent paging parameters.

Typical fields:

Page

Size

Sort

Direction

Pagination DTOs should remain reusable across the entire application.

---

# 33. Authentication DTOs

Authentication endpoints use specialized DTOs.

Examples

LoginRequest

RegisterRequest

TokenRefreshRequest

AuthenticationResponse

Authentication DTOs often have stricter security requirements than ordinary business DTOs.

---

# 34. Error DTOs

Error DTOs define standardized API error responses.

Typical fields include:

Timestamp

Status

Error Code

Message

Details

Path

Correlation ID

Consistent error responses simplify client integration.

---

# 35. Nested DTOs

Nested DTOs may be included when they improve API usability.

Example

TaskResponse

↓

AssigneeSummary

↓

WorkspaceSummary

Avoid deeply nested object graphs.

Only include nested DTOs when clients genuinely require the related information.

---

# 36. DTO Reuse Strategy

DTO reuse should be intentional.

Reuse DTOs when:

✓ Same purpose

✓ Same consumers

✓ Same validation

Create new DTOs when responsibilities diverge.

Avoid forcing multiple endpoints to share an unsuitable DTO.

---

# 37. Internal vs Public DTOs

SprintForge distinguishes between:

Internal DTOs

Used between application layers.

Public DTOs

Exposed through REST APIs.

Public DTOs require greater stability because they form part of the API contract.

Internal DTOs may evolve more freely.

---

# 38. DTO Naming Standards

DTO names should clearly communicate their purpose.

Examples

WorkspaceCreateRequest

WorkspaceUpdateRequest

WorkspaceSummaryResponse

WorkspaceDetailResponse

TaskPatchRequest

SearchRequest

Avoid generic names such as:

WorkspaceDTO

TaskData

UserObject

Names should describe both the business concept and the DTO's role.

---

# 39. DTO Design Goals

SprintForge DTOs should be:

✓ Purpose-Specific

✓ Lightweight

✓ Consumer-Focused

✓ Easy to Validate

✓ Easy to Map

✓ Stable

✓ Explicit

Every DTO should represent a single API responsibility while remaining easy to understand and evolve.

---
````md id="p4x8rm"
---

# 40. Bulk Operation DTOs

Some business operations modify multiple resources in a single request.

Such operations should use dedicated Bulk Operation DTOs rather than reusing single-resource DTOs.

Examples

BulkTaskUpdateRequest

BulkAssignRequest

BulkDeleteRequest

BulkArchiveRequest

Typical structure:

Target Resource IDs

↓

Operation Parameters

↓

Execution Options

Example

BulkAssignRequest

• taskIds

• assigneeId

• notifyAssignees

Bulk DTOs should:

✓ Clearly identify target resources

✓ Validate batch size

✓ Report partial failures where appropriate

Bulk operations should remain explicit and predictable.

---

# 41. Import & Export DTOs

Import and export operations often require specialized DTOs.

Examples

TaskImportRow

WorkspaceExportRecord

ProjectMigrationDTO

CSVTaskRecord

ExcelSprintRecord

Import/Export DTOs may contain fields that differ from standard REST DTOs.

Example

Import File

↓

CSV Row DTO

↓

Validation

↓

Entity Mapping

↓

Persistence

Import DTOs should prioritize:

✓ Data validation

✓ Error reporting

✓ Compatibility with external formats

Export DTOs should prioritize:

✓ Readability

✓ Consumer compatibility

✓ Stable data representation

Import/Export DTOs should never replace normal REST DTOs.

---

# 42. Event & Integration DTOs

SprintForge distinguishes between REST DTOs and Integration DTOs.

REST DTOs

↓

Client ↔ Server Communication

Integration DTOs

↓

System ↔ System Communication

Examples

TaskCreatedEvent

WorkspaceArchivedEvent

NotificationPayload

WebhookRequest

KafkaMessage

RabbitMQEvent

Integration DTOs may include:

Event Metadata

Correlation IDs

Event Version

Timestamp

Source System

Unlike REST DTOs, integration DTOs often emphasize:

✓ Compatibility

✓ Versioning

✓ Idempotency

✓ Loose Coupling

REST DTOs and Integration DTOs should evolve independently.

---

# 43. DTO Selection Guide

Choose the appropriate DTO based on the API operation.

| Use Case | Recommended DTO |
|----------|-----------------|
| Create a new resource | CreateRequest |
| Update an existing resource | UpdateRequest |
| Partially update a resource | PatchRequest |
| Return lightweight list data | SummaryResponse |
| Return complete resource details | DetailResponse |
| Search resources | SearchRequest |
| Apply reusable filters | FilterDTO |
| Authentication | Authentication DTO |
| Pagination | Pagination DTO |
| Batch operations | Bulk Operation DTO |
| Import data | Import DTO |
| Export data | Export DTO |
| System integration | Event/Integration DTO |

Developers should select the DTO type based on the operation being performed rather than attempting to reuse an existing DTO for unrelated purposes.

Purpose-specific DTOs lead to clearer APIs and simpler maintenance.

---

# 44. DTO Classification Checklist

Before introducing a new DTO, developers should verify the following:

### Purpose

✓ Does the DTO have a single responsibility?

✓ Is a new DTO actually required?

✓ Is the DTO consumer-focused?

---

### Classification

✓ Is the correct DTO type selected?

✓ Is it appropriate for the API operation?

✓ Is it clearly distinguishable from similar DTOs?

---

### Data Exposure

✓ Does it expose only the required fields?

✓ Are server-managed fields excluded where appropriate?

✓ Are sensitive fields omitted?

---

### Maintainability

✓ Is the DTO lightweight?

✓ Is it reusable where appropriate?

✓ Is unnecessary reuse avoided?

---

### Naming

✓ Does the name follow SprintForge conventions?

✓ Does the name communicate both the business concept and DTO type?

---

### Validation

✓ Are validation requirements appropriate?

✓ Are required and optional fields clearly defined?

✓ Is the DTO ready for mapping?

Every DTO should have a well-defined purpose, clear ownership, and a stable contract that aligns with SprintForge's API design standards.

---

End of Part 2


---

# SprintForge Engineering Standard
# DTO Layer

# Part 3 – DTO Structure & Organization

## 45. Purpose

A well-structured DTO layer improves readability, discoverability, maintainability, and API consistency.

SprintForge organizes DTOs by **feature first**, then by **purpose**, mirroring the module structure of the application.

---

## 46. Package Structure

Each feature owns its own DTO package.

```
workspace/
    dto/
        request/
        response/
        search/
        mapper/

task/
    dto/
        request/
        response/
        search/
```

Avoid a global `dto/` package containing every DTO in the application.

---

## 47. Request vs Response Packages

Separate incoming and outgoing models.

```
request/
    CreateWorkspaceRequest
    UpdateWorkspaceRequest
    PatchWorkspaceRequest

response/
    WorkspaceSummaryResponse
    WorkspaceDetailResponse
```

This separation immediately communicates intent.

---

## 48. Naming Conventions

Always include both:

- Business object
- DTO role

Examples:

```
UserCreateRequest
UserUpdateRequest
UserProfileResponse
WorkspaceSummaryResponse
TaskSearchRequest
```

Avoid:

```
UserDTO
DataDTO
TaskObject
Model
```

---

## 49. Java Records vs Classes

Prefer Java Records for immutable DTOs.

Good:

```
public record WorkspaceResponse(...)
```

Use classes only when:

- Mutable properties are required
- Framework limitations exist
- Builders are necessary

Records reduce boilerplate and communicate immutability.

---

## 50. Field Ordering

Maintain consistent ordering.

Recommended:

```
Identifiers

↓

Business Fields

↓

Relationships

↓

Metadata

↓

Audit Information
```

Consistent ordering improves readability.

---

## 51. Nullable Fields

Every nullable field should be intentional.

Document:

- Required
- Optional
- Nullable

Avoid ambiguous nullability.

---

## 52. Collections

Never return null collections.

Good

```
[]
```

Bad

```
null
```

Clients should never need null checks before iteration.

---

## 53. Nested DTO Design

Limit nesting.

Good

```
Task

↓

AssigneeSummary
```

Avoid

```
Task

↓

Workspace

↓

Project

↓

Organization

↓

Owner

↓

Profile
```

Deep object graphs increase payload size and complexity.

---

## 54. Specialized DTOs

Different endpoints often deserve different DTOs.

Example:

Dashboard

↓

DashboardResponse

Analytics

↓

AnalyticsResponse

Statistics

↓

StatisticsResponse

Do not reuse Detail DTOs everywhere.

---

## 55. Pagination Models

Prefer reusable pagination wrappers.

Example:

```
PageResponse<T>
```

Containing:

- content
- page
- size
- totalElements
- totalPages
- hasNext

Every paginated endpoint should share the same structure.

---

## 56. Generic Wrappers

Use wrappers sparingly.

Good

```
ApiResponse<T>
```

Bad

```
ApiData<ApiObject<ApiResult<T>>>
```

Avoid wrapper nesting.

---

## 57. Serialization Standards

Use consistent JSON naming.

Prefer

camelCase

Avoid inconsistent casing across DTOs.

---

## 58. Date & Time Representation

Use ISO-8601.

Examples

```
2026-07-26T14:35:21Z
```

Avoid locale-specific formats.

Never expose database timestamp formats.

---

## 59. Enum Representation

Expose enums as meaningful strings.

Good

```
"IN_PROGRESS"
```

Avoid numeric enum values.

---

## 60. DTO Documentation

Every public DTO should explain:

- Purpose
- Consumer
- Validation
- Optional fields

Swagger/OpenAPI annotations are encouraged.

---

## 61. Versioning Strategy

Avoid creating V2 DTOs until necessary.

Prefer

```
WorkspaceResponse
```

instead of

```
WorkspaceResponseV2
```

Version APIs—not every DTO.

---

## 62. Structure Checklist

Before introducing a DTO:

✓ Proper package

✓ Proper name

✓ Immutable

✓ Consumer focused

✓ Minimal fields

✓ Consistent ordering

✓ Well documented

---

# Part 4 – Validation Standards

## 63. Purpose

Validation prevents invalid data from entering the domain.

DTO validation represents the application's first line of defense.

---

## 64. Validation Philosophy

Validate:

- Required fields
- Length
- Format
- Range
- Patterns

Business rules belong in Services or Entities.

---

## 65. Bean Validation

Prefer Jakarta Validation.

Examples:

```
@NotNull
@NotBlank
@Size
@Email
@Pattern
@Positive
@Min
@Max
```

Avoid manual validation whenever standard annotations suffice.

---

## 66. Required Fields

Use:

```
@NotNull
```

for objects

```
@NotBlank
```

for strings

Choose the correct constraint.

---

## 67. String Validation

Validate:

- minimum length
- maximum length
- regex
- whitespace

Trim values before processing.

---

## 68. Numeric Validation

Validate:

- positive
- range
- precision

Reject impossible values early.

---

## 69. Date Validation

Examples:

```
@Past
@Future
@FutureOrPresent
```

Ensure date logic aligns with business requirements.

---

## 70. Collection Validation

Validate:

- maximum size
- minimum size
- nested objects

Example

```
@Size(max=20)
```

---

## 71. Nested Validation

Use

```
@Valid
```

for nested DTOs.

Without it, nested validation won't execute.

---

## 72. Custom Validation

Create custom validators when standard annotations are insufficient.

Examples

- Password strength
- Unique username
- Business-specific formats

---

## 73. Cross-Field Validation

Some rules involve multiple fields.

Example

```
startDate < endDate
```

These require custom validators.

---

## 74. Validation Messages

Messages should be:

Clear

Specific

Actionable

Avoid

```
Invalid input
```

Prefer

```
Workspace name must contain between 3 and 100 characters.
```

---

## 75. Business Validation

Do NOT check:

- database existence
- ownership
- permissions

inside DTO validation.

Those belong in Services.

---

## 76. Fail Fast

Reject invalid requests before reaching business logic.

Controller

↓

Validation

↓

Service

↓

Repository

---

## 77. Validation Checklist

✓ Bean Validation

✓ Nested validation

✓ Clear messages

✓ No business logic

✓ No persistence checks

---

# Part 5 – Mapping Strategy

## 78. Purpose

Mapping converts DTOs into Entities and vice versa.

DTOs and Entities should never be manually mixed throughout the application.

---

## 79. Mapper Philosophy

Controllers should never map.

Repositories should never map.

Services should not contain mapping code.

Dedicated mappers perform conversion.

---

## 80. Mapping Direction

Request DTO

↓

Entity

↓

Business Logic

↓

Response DTO

Only mappers understand both sides.

---

## 81. One Mapper Per Aggregate

Example

```
WorkspaceMapper
TaskMapper
SprintMapper
```

Avoid giant mapping utilities.

---

## 82. Manual vs Generated Mapping

Prefer MapStruct.

Reasons:

✓ Fast

✓ Compile-time safety

✓ Readable

✓ Type-safe

Avoid reflection-based mapping libraries.

---

## 83. Mapping Responsibilities

Mappers:

✓ Copy data

✓ Convert enums

✓ Convert nested DTOs

✓ Format values

Mappers should not:

✗ Query repositories

✗ Execute business logic

✗ Validate permissions

---

## 84. Nested Mapping

Delegate nested mappings.

Example

TaskMapper

↓

UserMapper

↓

WorkspaceMapper

Avoid duplicated conversion logic.

---

## 85. Null Handling

Define consistent behavior.

Null input

↓

Null output

or

↓

Exception

Choose one standard and document it.

---

## 86. Collection Mapping

Map collections using reusable methods.

Never duplicate list conversion logic.

---

## 87. Partial Updates

Patch operations require selective mapping.

Only provided fields should overwrite existing values.

---

## 88. Mapping Checklist

✓ Dedicated mapper

✓ No business logic

✓ Reusable

✓ Nested delegation

✓ Consistent null handling

---

# Part 6 – Response Design & API Contracts

## 89. Purpose

Response DTOs define exactly what clients receive.

Consistency improves developer experience.

---

## 90. Consistent Responses

All endpoints should follow consistent structures.

Avoid every controller inventing unique responses.

---

## 91. Success Responses

Responses should contain:

Requested data

Relevant metadata

Nothing more

Avoid exposing unnecessary internals.

---

## 92. Pagination Responses

Standard format:

- content
- page
- size
- totalPages
- totalElements

Every paginated endpoint should match.

---

## 93. Empty Responses

For successful operations without payload:

Use

```
204 No Content
```

Avoid returning meaningless objects.

---

## 94. Error Responses

Standardize:

- code
- message
- timestamp
- path
- details

Every error should follow the same schema.

---

## 95. Field Consistency

Never rename equivalent fields.

Example

Always use

```
createdAt
```

Not

```
created
creationDate
dateCreated
```

---

## 96. Response Size

Only expose what clients require.

Avoid returning complete entities when summaries suffice.

---

## 97. Stable Contracts

Changing response DTOs is an API-breaking change.

Prefer additive evolution.

---

## 98. Hypermedia

SprintForge currently follows REST without HATEOAS.

Avoid embedding navigation links unless future requirements demand them.

---

## 99. Serialization Performance

Avoid:

- circular references
- huge object graphs
- excessive nesting

Design responses for efficiency.

---

## 100. Response Checklist

✓ Stable

✓ Consistent

✓ Minimal

✓ Consumer focused

✓ Proper HTTP semantics

---

End of part 6

---

# SprintForge Engineering Standard
# DTO Layer

# Part 7 – Security & Data Exposure

## 101. Purpose

DTOs act as the application's security boundary.

Every field exposed through a DTO is information intentionally shared with API consumers.

Never expose internal domain objects directly.

---

## 102. Principle of Least Exposure

Expose only the information required by the client.

Bad

```java
UserResponse
{
    id,
    username,
    email,
    passwordHash,
    roles,
    createdAt,
    updatedAt
}
```

Good

```java
UserSummaryResponse
{
    id,
    username,
    avatarUrl
}
```

Every additional field increases security risk.

---

## 103. Never Expose Sensitive Fields

Sensitive fields should never appear in response DTOs.

Examples:

- password
- passwordHash
- refreshToken
- accessTokenSecret
- OTP
- verificationCode
- JWT signing information
- internal database IDs
- internal audit logs
- deleted flags
- version fields (unless intentionally exposed)

---

## 104. Internal vs External Models

Internal Entity

↓

Service

↓

Response DTO

↓

Client

The client should never understand your internal persistence model.

---

## 105. Role-Based DTOs

Different users may require different DTOs.

Example

Admin

↓

UserAdminResponse

Normal User

↓

UserProfileResponse

Guest

↓

PublicUserResponse

Avoid exposing admin-only information to standard users.

---

## 106. Field-Level Authorization

Some fields require authorization.

Example

Project Budget

↓

Visible to Managers

↓

Hidden from Contributors

DTO selection may depend on the authenticated user's permissions.

---

## 107. Prevent Over-Posting

Clients should never control fields they are not allowed to modify.

Bad

```java
UserUpdateRequest
{
    username,
    role,
    createdAt,
    passwordHash
}
```

Good

```java
UserUpdateRequest
{
    displayName,
    bio,
    avatar
}
```

Accept only fields that the client is permitted to update.

---

## 108. Prevent Under-Validation

Never assume the client sends trustworthy data.

Validate every incoming field, even if the frontend already performs validation.

Trust boundaries always begin at the server.

---

## 109. Data Minimization

Prefer:

Summary DTO

instead of

Complete Entity

Only transmit data that is actively used by the client.

---

## 110. File & Binary Data

Avoid embedding large binary content directly inside DTOs.

Instead return:

- fileId
- downloadUrl
- mimeType
- size

The file itself should be retrieved separately.

---

## 111. IDs and Identifiers

Expose only identifiers intended for public use.

Avoid exposing:

- database sequence numbers (if considered sensitive)
- internal implementation identifiers
- infrastructure-specific references

Prefer stable business identifiers or UUIDs where appropriate.

---

## 112. DTO Security Checklist

✓ No sensitive fields

✓ Least privilege

✓ Minimal exposure

✓ Proper validation

✓ Role-aware responses

✓ Secure update models

---

# Part 8 – DTO Anti-Patterns & Code Smells

## 113. Purpose

Poor DTO design leads to brittle APIs, security issues, and maintenance problems.

This section highlights common mistakes to avoid.

---

## 114. God DTO

One DTO serving dozens of endpoints.

Example

```java
UserDTO
```

Used for:

- Create
- Update
- Search
- Response
- Export
- Import

Create specialized DTOs instead.

---

## 115. Entity Exposure

Returning JPA entities directly.

Problems:

- Lazy loading issues
- Infinite recursion
- Security leaks
- API coupling

Always map to Response DTOs.

---

## 116. Reusing Request as Response

Bad

```java
WorkspaceDTO
```

Used for both request and response.

Requests and responses evolve differently.

Keep them separate.

---

## 117. Massive DTOs

DTOs with 80–100 fields are a design smell.

Split them into:

- Summary
- Detail
- Statistics
- Settings
- Analytics

---

## 118. Business Logic Inside DTOs

Avoid methods like:

```java
calculatePriority()

approveTask()

closeSprint()
```

DTOs transfer data.

They do not perform business operations.

---

## 119. Persistence Annotations

Never annotate DTOs with:

- @Entity
- @Table
- @Column
- @OneToMany

Persistence belongs to entities.

---

## 120. Bidirectional DTO Graphs

Avoid

Task

↓

Workspace

↓

Tasks

↓

Workspace

↓

Tasks

Circular DTO graphs cause serialization problems.

---

## 121. Inconsistent Naming

Avoid

```
TaskDTO
TaskData
TaskInfo
TaskObject
```

Use consistent naming:

```
TaskCreateRequest
TaskSummaryResponse
TaskDetailResponse
```

---

## 122. Manual Mapping Everywhere

Repeated mapping code inside controllers is a maintenance nightmare.

Centralize mapping in dedicated mapper classes.

---

## 123. Over-Nesting

Deep JSON structures increase payload size.

Keep nesting shallow.

---

## 124. API Leakage

Never expose:

- ORM implementation
- Database schema
- Internal enums
- Internal state

DTOs define business contracts—not database contracts.

---

## 125. Anti-Pattern Checklist

Avoid:

✗ God DTOs

✗ Entity exposure

✗ Business logic

✗ Persistence annotations

✗ Circular references

✗ Manual mapping

✗ Massive payloads

✗ Security leaks

---

# Part 9 – Reference Templates & Implementation Blueprints

## 126. Standard Create Request

```java
public record WorkspaceCreateRequest(

    @NotBlank
    @Size(min = 3, max = 100)
    String name,

    @Size(max = 500)
    String description
) {}
```

---

## 127. Standard Update Request

```java
public record WorkspaceUpdateRequest(

    @NotBlank
    @Size(min = 3, max = 100)
    String name,

    @Size(max = 500)
    String description
) {}
```

---

## 128. Standard Summary Response

```java
public record WorkspaceSummaryResponse(

    UUID id,

    String name,

    WorkspaceVisibility visibility
) {}
```

---

## 129. Standard Detail Response

```java
public record WorkspaceDetailResponse(

    UUID id,

    String name,

    String description,

    WorkspaceVisibility visibility,

    Instant createdAt,

    Instant updatedAt
) {}
```

---

## 130. Standard Search Request

```java
public record WorkspaceSearchRequest(

    String keyword,

    WorkspaceVisibility visibility,

    int page,

    int size
) {}
```

---

## 131. Standard Error Response

```java
public record ErrorResponse(

    Instant timestamp,

    int status,

    String error,

    String message,

    String path
) {}
```

---

## 132. Standard Page Response

```java
public record PageResponse<T>(

    List<T> content,

    int page,

    int size,

    long totalElements,

    int totalPages,

    boolean hasNext
) {}
```

---

## 133. Standard API Response

Only when a wrapper is truly needed.

```java
public record ApiResponse<T>(

    T data,

    String message
) {}
```

Avoid wrapping every endpoint unnecessarily.

---

## 134. Mapper Blueprint

```
Request DTO

↓

Mapper

↓

Entity

↓

Business Logic

↓

Mapper

↓

Response DTO
```

Controllers should never perform mapping.

---

## 135. DTO Blueprint Checklist

Every DTO should satisfy:

✓ Proper name

✓ Proper package

✓ Immutable

✓ Validation

✓ Mapper support

✓ Documentation

✓ Consumer-focused

---

# Part 10 – DTO Governance & Final Principles

## 136. Purpose

DTO standards ensure consistency across SprintForge as the project grows.

Every contributor should follow the same conventions.

---

## 137. Ownership

Each feature owns its own DTOs.

Example

```
task/

dto/

request/

response/
```

Avoid shared DTO packages across unrelated modules.

---

## 138. Code Review Requirements

Every DTO review should verify:

- Correct naming
- Proper validation
- No business logic
- Proper mapping
- No entity exposure
- Correct package placement

---

## 139. Evolution Strategy

DTOs evolve gradually.

Preferred:

✓ Add fields

✓ Deprecate old fields

✓ Introduce new DTOs when responsibilities change

Avoid breaking existing clients.

---

## 140. Documentation Requirements

Every public DTO should be documented using OpenAPI/Swagger annotations where appropriate.

Include:

- purpose
- field descriptions
- examples
- validation constraints

---

## 141. Testing Expectations

DTO-related tests should verify:

- Validation
- Serialization
- Deserialization
- Mapper correctness
- API contract stability

---

## 142. Framework Independence

DTOs should remain lightweight.

Avoid framework-specific behavior beyond serialization and validation annotations.

This keeps the API portable and easier to evolve.

---

## 143. AI-Assisted Development

When using AI tools to generate DTOs, verify that they:

- follow SprintForge naming conventions
- use immutable records where appropriate
- include only necessary fields
- avoid exposing entities
- include appropriate validation
- remain consumer-focused

AI-generated code should always undergo human review.

---

## 144. Final DTO Principles

Every SprintForge DTO should be:

✓ Purpose-specific

✓ Immutable where practical

✓ Easy to validate

✓ Easy to map

✓ Consumer-focused

✓ Minimal

✓ Secure

✓ Well documented

✓ Stable

✓ Independent of persistence

---

## 145. DTO Compliance Checklist

Before merging a new DTO:

### Design

✓ Single responsibility

✓ Correct DTO type

✓ Clear naming

✓ Appropriate package

### Security

✓ No sensitive fields

✓ Least privilege

✓ Proper update restrictions

### Validation

✓ Bean Validation applied

✓ Custom validation where needed

✓ Nested validation supported

### Mapping

✓ Dedicated mapper exists

✓ No business logic

✓ No repository access

### API

✓ Stable contract

✓ Proper documentation

✓ Serialization verified

✓ Consumer-focused design

---

## 146. Closing Statement

The DTO layer is the public face of SprintForge.

Entities may evolve.

Repositories may change.

Services may be refactored.

Database schemas may be redesigned.

However, well-designed DTOs provide a stable, secure, and consumer-friendly contract that allows the system to evolve internally without breaking clients.

By following these standards, SprintForge maintains clear boundaries between its domain model and external interfaces, resulting in APIs that are secure, maintainable, and resilient over time.

---

End of part 10