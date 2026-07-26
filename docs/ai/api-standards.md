# SprintForge Engineering Standard
# API Standards

# Part 1 – API Philosophy & Design Principles

## 1. Purpose

SprintForge APIs provide the primary communication interface between clients and the backend.

A well-designed API should be:

- Predictable
- Consistent
- Discoverable
- Stable
- Easy to consume
- Easy to evolve

API design is part of the product—not merely an implementation detail.

---

## 2. API Philosophy

SprintForge follows RESTful principles while prioritizing developer experience.

Every endpoint should answer three questions clearly:

- What resource is being accessed?
- What operation is being performed?
- What response should the client expect?

Consistency is more valuable than cleverness.

---

## 3. Goals

SprintForge APIs should be:

✓ RESTful

✓ Predictable

✓ Stateless

✓ Versionable

✓ Secure

✓ Self-documenting

✓ Backward compatible where practical

---

## 4. REST Architecture

SprintForge follows a resource-oriented architecture.

```text id="api001"
Client

↓

HTTP Request

↓

Resource

↓

Representation

↓

HTTP Response
```

Resources—not actions—are the primary abstraction.

---

## 5. Resource-Oriented Design

Resources represent domain entities.

Examples:

```text id="api002"
users

workspaces

projects

sprints

tasks

comments

notifications
```

Avoid designing APIs around verbs.

---

## 6. Statelessness

Every request should contain all information necessary for processing.

Servers should not rely on HTTP session state.

Authentication is handled using JWT.

---

## 7. Uniform Interface

Clients should interact with resources consistently.

Example:

```text id="api003"
GET

POST

PUT

PATCH

DELETE
```

Every resource should follow the same conventions.

---

## 8. Predictability

If clients understand one SprintForge endpoint, they should understand all others.

Naming, status codes, pagination, filtering, and error responses should remain consistent.

---

## 9. Simplicity

Avoid unnecessary nesting.

Good:

```text id="api004"
/workspaces/{id}/tasks
```

Bad:

```text id="api005"
/companies/{id}/organizations/{id}/teams/{id}/workspaces/{id}/tasks
```

Deep resource hierarchies reduce usability.

---

## 10. Consistency

Every module should follow identical API conventions.

Developers should never have to guess:

- URL format
- Response structure
- Status codes
- Pagination format

---

## 11. API Evolution

APIs should evolve without unnecessarily breaking existing clients.

Backward compatibility should be considered before introducing breaking changes.

---

## 12. Design Principles

SprintForge APIs follow:

- Resource orientation
- Statelessness
- Consistency
- Discoverability
- Stability

---

## 13. Philosophy Checklist

✓ RESTful

✓ Resource-oriented

✓ Stateless

✓ Consistent

✓ Predictable

---

# Part 2 – URI Design & Resource Naming

## 14. Purpose

URIs identify resources.

They should clearly describe **what** is being accessed—not **how** the server implements it.

---

## 15. Naming Convention

Use:

- lowercase
- plural nouns
- hyphen-separated words

Examples:

```text id="api006"
/users

/workspaces

/task-comments
```

Avoid camelCase and snake_case in URLs.

---

## 16. Resource Names

Good:

```text id="api007"
/tasks

/projects

/sprints
```

Bad:

```text id="api008"
/getTasks

/createSprint

/deleteWorkspace
```

Operations belong to HTTP methods—not URI names.

---

## 17. Resource Identifiers

Use path variables.

Example:

```text id="api009"
/tasks/{taskId}
```

Avoid query parameters for identifying a single resource.

---

## 18. Nested Resources

Nested resources should represent ownership.

Example:

```text id="api010"
/workspaces/{workspaceId}/projects

/projects/{projectId}/tasks
```

Avoid excessive nesting beyond two or three levels.

---

## 19. Collections

Collections represent multiple resources.

Example:

```text id="api011"
GET /tasks
```

Individual resources:

```text id="api012"
GET /tasks/{taskId}
```

---

## 20. Query Parameters

Use query parameters for:

- filtering
- sorting
- pagination
- searching

Example:

```text id="api013"
/tasks?status=TODO&page=0&size=20
```

---

## 21. Reserved Characters

Avoid unnecessary special characters.

Prefer:

```text id="api014"
/task-comments
```

instead of:

```text id="api015"
/task_comments
```

---

## 22. API Prefix

Recommended base path:

```text id="api016"
/api/v1
```

Example:

```text id="api017"
/api/v1/tasks
```

This simplifies future versioning.

---

## 23. URI Checklist

✓ Plural nouns

✓ Lowercase

✓ Resource-oriented

✓ Hyphen-separated

✓ Minimal nesting

---

# Part 3 – HTTP Methods & Semantics

## 24. Purpose

HTTP methods communicate the intended operation.

Clients should infer behavior from the HTTP method alone.

---

## 25. GET

Retrieve resources.

Characteristics:

- Safe
- Idempotent
- No side effects

Example:

```text id="api018"
GET /tasks
```

---

## 26. POST

Create resources.

Example:

```text id="api019"
POST /tasks
```

POST may also initiate operations that do not naturally fit CRUD, but resource creation remains its primary use.

---

## 27. PUT

Replace an existing resource completely.

Every field is expected to be supplied.

Example:

```text id="api020"
PUT /users/{id}
```

---

## 28. PATCH

Modify part of a resource.

Example:

```text id="api021"
PATCH /tasks/{id}
```

Preferred for partial updates.

---

## 29. DELETE

Remove a resource.

Example:

```text id="api022"
DELETE /tasks/{id}
```

Deletion may be soft or hard depending on business requirements, but the API contract should remain consistent.

---

## 30. OPTIONS & HEAD

Support automatically through the framework where appropriate.

Business APIs rarely require custom implementations.

---

## 31. Idempotency

Safe methods:

```text id="api023"
GET

PUT

DELETE
```

Repeated requests should produce the same final state.

POST is generally not idempotent.

---

## 32. Method Selection

Choose the HTTP method based on semantics—not convenience.

Avoid using POST for updates when PATCH or PUT better communicates intent.

---

## 33. Method Checklist

✓ GET for retrieval

✓ POST for creation

✓ PUT for replacement

✓ PATCH for updates

✓ DELETE for deletion

---

# Part 4 – Request & Response Standards

## 34. Purpose

Clients should receive predictable request and response structures.

Consistency improves usability and simplifies frontend development.

---

## 35. Request Body

Use JSON for request payloads.

Example:

```json
{
  "title": "Implement JWT Authentication",
  "priority": "HIGH"
}
```

Use DTOs to define request contracts.

---

## 36. Response Body

Successful responses should return DTOs rather than entities.

Example:

```json
{
  "id": 12,
  "title": "Implement JWT Authentication",
  "status": "IN_PROGRESS"
}
```

---

## 37. Resource Creation

Successful creation should return:

- HTTP 201 Created
- Newly created resource (or appropriate representation)
- `Location` header when applicable

---

## 38. Empty Responses

Operations without response content should return:

```text id="api024"
204 No Content
```

Avoid returning empty JSON objects unnecessarily.

---

## 39. Collections

Collections should return arrays together with pagination metadata where applicable.

Example:

```json
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 154,
  "totalPages": 8
}
```

---

## 40. Standard Response Rules

Responses should:

- Use JSON
- Include only necessary fields
- Avoid exposing internal implementation details
- Remain stable across releases

---

## 41. DTO Ownership

Request DTOs and response DTOs should remain independent.

Avoid reusing the same DTO for unrelated operations if their contracts differ.

---

## 42. Response Checklist

✓ JSON

✓ DTOs

✓ Appropriate status code

✓ Stable structure

✓ Minimal payload

---

# Part 5 – HTTP Status Codes

## 43. Purpose

Status codes communicate the outcome of an operation independently of the response body.

Clients should rely on HTTP semantics before interpreting JSON.

---

## 44. Success Codes

Common success responses:

| Status | Meaning |
|---------|---------|
| 200 | Success |
| 201 | Created |
| 202 | Accepted |
| 204 | No Content |

Choose the most appropriate status rather than always returning `200`.

---

## 45. Client Errors

Typical client errors:

| Status | Meaning |
|---------|---------|
| 400 | Invalid request |
| 401 | Authentication required |
| 403 | Permission denied |
| 404 | Resource not found |
| 409 | Conflict |
| 422 | Semantic validation failure (optional, if adopted consistently) |

---

## 46. Server Errors

Typical server responses:

| Status | Meaning |
|---------|---------|
| 500 | Internal server error |
| 502 | Bad gateway |
| 503 | Service unavailable |
| 504 | Gateway timeout |

Avoid leaking internal implementation details in server error responses.

---

## 47. Consistency

The same situation should always produce the same status code across every module.

Example:

```text id="api025"
Resource Not Found

↓

404
```

Never return `200` with an embedded error object.

---

## 48. Exception Mapping

The global exception handler should consistently map exceptions to HTTP responses.

Example:

```text id="api026"
EntityNotFoundException

↓

404
```

Centralized exception mapping ensures predictable behavior.

---

## 49. Error Contract

Every error response should follow the standardized `ErrorResponse` format defined in `exceptions.md`.

Avoid endpoint-specific error structures.

---

## 50. Status Code Checklist

✓ Correct semantics

✓ Consistent mapping

✓ Centralized exception handling

✓ No "200 for errors"

✓ Stable error contract

---

## 51. Midpoint Summary

At this stage, SprintForge API standards define:

- REST philosophy and architecture
- URI and resource naming conventions
- HTTP method semantics
- Request and response design
- Consistent HTTP status code usage

---
Perfect. This completes the `api-standards.md` handbook.

---

# SprintForge Engineering Standard
# API Standards

# Part 6 – Pagination, Filtering & Sorting

## 52. Purpose

Most API resources grow over time.

Pagination, filtering, and sorting ensure APIs remain performant, predictable, and easy to consume regardless of dataset size.

---

## 53. Pagination

Collection endpoints should support pagination by default.

Recommended query parameters:

```text id="api027"
page

size
```

Example:

```text id="api028"
GET /tasks?page=0&size=20
```

Avoid returning entire datasets in a single response.

---

## 54. Default Page Size

Provide a sensible default page size.

Example:

```text id="api029"
20
```

Also define a maximum allowed page size to prevent excessively large responses.

---

## 55. Pagination Response

Paginated responses should include metadata.

Example:

```json id="api030"
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 240,
  "totalPages": 12,
  "first": true,
  "last": false
}
```

Clients should not need to calculate pagination details themselves.

---

## 56. Sorting

Sorting should be performed using query parameters.

Example:

```text id="api031"
GET /tasks?sort=createdAt,desc
```

Multiple sort fields may be supported if needed.

---

## 57. Filtering

Filtering should also use query parameters.

Examples:

```text id="api032"
GET /tasks?status=TODO

GET /tasks?priority=HIGH

GET /tasks?assignee=42
```

Filters should narrow result sets rather than alter resource behavior.

---

## 58. Searching

Search endpoints should remain resource-oriented.

Example:

```text id="api033"
GET /tasks?search=authentication
```

Avoid creating separate endpoints such as:

```text id="api034"
/searchTasks
```

---

## 59. Combining Parameters

Clients should be able to combine:

- Pagination
- Sorting
- Filtering

Example:

```text id="api035"
GET /tasks

?page=0

&size=20

&sort=priority,desc

&status=TODO
```

Each parameter should remain independent.

---

## 60. Performance

Filtering and sorting should be backed by efficient database queries and appropriate indexes.

Avoid loading large result sets into memory for in-memory filtering.

---

## 61. Pagination Checklist

✓ Page

✓ Size

✓ Sorting

✓ Filtering

✓ Metadata

---

# Part 7 – Versioning, Compatibility & Deprecation

## 62. Purpose

APIs evolve over time.

Versioning enables SprintForge to introduce improvements while minimizing disruption to existing clients.

---

## 63. Versioning Strategy

SprintForge standardizes on URI versioning.

Example:

```text id="api036"
/api/v1/tasks
```

Future breaking changes should use:

```text id="api037"
/api/v2/tasks
```

---

## 64. Backward Compatibility

Whenever practical:

- Add new fields rather than changing existing ones
- Preserve existing behavior
- Avoid removing response fields abruptly

Backward compatibility reduces client migration effort.

---

## 65. Breaking Changes

Examples include:

- Removing fields
- Renaming properties
- Changing endpoint semantics
- Changing authentication requirements
- Altering status code behavior

Breaking changes should only occur in a new major API version.

---

## 66. Non-Breaking Changes

Generally safe changes include:

- Adding optional response fields
- Adding optional request fields
- Improving documentation
- Optimizing performance
- Adding new endpoints

These should not require a version change.

---

## 67. Deprecation

Deprecated endpoints should:

- Remain functional during the deprecation period
- Be documented clearly
- Provide migration guidance
- Have a planned removal date

Deprecation should be communicated well before removal.

---

## 68. Sunset Strategy

Recommended lifecycle:

```text id="api038"
Supported

↓

Deprecated

↓

Removed
```

Clients should receive sufficient notice before removal.

---

## 69. OpenAPI Versioning

Swagger/OpenAPI documentation should clearly indicate:

- Current API version
- Deprecated endpoints
- Replacement endpoints

Documentation should evolve alongside the API.

---

## 70. Compatibility Checklist

✓ URI versioning

✓ Backward compatibility

✓ Planned deprecation

✓ Migration guidance

✓ Updated documentation

---

# Part 8 – API Anti-Patterns

## 71. Purpose

Inconsistent API design creates confusion, increases maintenance costs, and complicates client development.

Avoid the following anti-patterns.

---

## 72. Verb-Based URIs

Bad:

```text id="api039"
/createTask

/deleteTask

/getTasks
```

Good:

```text id="api040"
/tasks
```

Use HTTP methods to express actions.

---

## 73. Deep Nesting

Avoid deeply nested resource paths.

Bad:

```text id="api041"
/organizations/{id}/teams/{id}/projects/{id}/sprints/{id}/tasks
```

Keep resource hierarchies shallow whenever possible.

---

## 74. Returning Entities

Never expose JPA entities directly.

Problems include:

- Internal field exposure
- Lazy-loading issues
- Tight coupling
- Serialization problems

Always return DTOs.

---

## 75. Inconsistent Status Codes

Bad:

```text id="api042"
404

↓

Sometimes 400

↓

Sometimes 200
```

The same situation should always return the same status code.

---

## 76. Multiple Response Formats

Avoid:

```text id="api043"
Endpoint A

↓

Format A

Endpoint B

↓

Format B
```

Response structures should remain consistent across the application.

---

## 77. Ignoring HTTP Semantics

Do not use:

```text id="api044"
POST

↓

Retrieve Data
```

Choose the method that accurately represents the operation.

---

## 78. Leaking Internal Details

Avoid exposing:

- Database IDs that should remain internal
- Stack traces
- SQL errors
- Framework exceptions

The API contract should remain independent of internal implementation.

---

## 79. Anti-Pattern Checklist

Avoid:

✗ Verb-based URLs

✗ Deep nesting

✗ Entity exposure

✗ Inconsistent status codes

✗ Multiple response formats

✗ Misused HTTP methods

✗ Internal implementation leakage

---

# Part 9 – Reference Templates & Implementation Blueprints

## 80. Collection Endpoint

```text id="api045"
GET /api/v1/tasks
```

---

## 81. Resource Endpoint

```text id="api046"
GET /api/v1/tasks/{taskId}
```

---

## 82. Nested Resource

```text id="api047"
GET /api/v1/workspaces/{workspaceId}/projects
```

---

## 83. Pagination Example

```text id="api048"
GET /api/v1/tasks

?page=0

&size=20

&sort=priority,desc
```

---

## 84. Resource Creation Flow

```text id="api049"
POST

↓

201 Created

↓

Location Header

↓

Response DTO
```

---

## 85. Error Flow

```text id="api050"
Exception

↓

Global Handler

↓

ErrorResponse

↓

HTTP Status
```

---

## 86. API Lifecycle

```text id="api051"
Design

↓

Implement

↓

Document

↓

Test

↓

Release

↓

Deprecate

↓

Remove
```

---

## 87. API Checklist

✓ RESTful

✓ Versioned

✓ DTOs

✓ Consistent status codes

✓ Predictable responses

---

# Part 10 – Governance & Final Principles

## 88. Purpose

API standards ensure every SprintForge endpoint presents a consistent experience to clients, regardless of which module implements it.

A stable API reduces integration costs and improves long-term maintainability.

---

## 89. Ownership

API responsibilities should remain clearly separated.

| Concern | Owner |
|---------|-------|
| URI Design | Controller Layer |
| Request Validation | Validation Layer |
| Business Logic | Service Layer |
| Error Mapping | Global Exception Handler |
| Security | Spring Security |
| Documentation | OpenAPI |

---

## 90. Code Review Requirements

Every API-related review should verify:

- URI naming conventions
- Correct HTTP method
- Appropriate status codes
- DTO usage
- Validation present
- Security applied
- Pagination where applicable
- OpenAPI documentation updated

---

## 91. Documentation

Every public endpoint should document:

- Purpose
- Request DTO
- Response DTO
- Status codes
- Authentication requirements
- Query parameters
- Example requests and responses

OpenAPI should be treated as the authoritative API reference.

---

## 92. Testing Expectations

Every API should be tested for:

- Successful requests
- Validation failures
- Authentication failures
- Authorization failures
- Error responses
- Pagination
- Filtering
- Sorting

API contract tests help ensure changes do not unintentionally break clients.

---

## 93. Evolution Strategy

As SprintForge evolves:

- Preserve backward compatibility where practical
- Deprecate before removing
- Version breaking changes
- Review API consistency during feature development

The API should evolve deliberately rather than organically.

---

## 94. AI-Assisted Development

AI tools can generate controllers, DTOs, and OpenAPI documentation, but generated APIs should be reviewed to ensure they:

- Follow REST principles
- Use correct HTTP methods
- Return DTOs instead of entities
- Apply consistent status codes
- Support pagination where appropriate
- Align with SprintForge naming conventions

AI should accelerate implementation while preserving a consistent API contract.

---

## 95. Final API Principles

Every SprintForge API should be:

✓ RESTful

✓ Stateless

✓ Predictable

✓ Versioned

✓ Secure

✓ Well documented

✓ Backward compatible where practical

✓ Consistent

✓ Easy to consume

✓ Easy to evolve

---

## 96. API Compliance Checklist

Before merging API-related changes:

### Resource Design

✓ Resource-oriented URIs

✓ Correct HTTP method

✓ Minimal nesting

### Requests & Responses

✓ DTOs only

✓ JSON format

✓ Correct status codes

✓ Standard error responses

### Collections

✓ Pagination supported

✓ Filtering supported

✓ Sorting supported

### Compatibility

✓ Version reviewed

✓ Backward compatibility considered

✓ OpenAPI updated

### Quality

✓ Controller tests updated

✓ Integration tests updated

✓ Documentation updated

---

## 97. Closing Statement

SprintForge APIs represent the public contract between the backend and its consumers.

By following consistent resource-oriented design, standard HTTP semantics, predictable response structures, comprehensive documentation, and disciplined versioning, SprintForge provides APIs that are intuitive to use, resilient to change, and maintainable over the lifetime of the application.

A well-designed API reduces client complexity, improves developer experience, and enables the platform to evolve confidently without sacrificing reliability.

---
