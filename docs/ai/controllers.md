
# SprintForge Engineering Standard
# Controller Layer

# Part 1 – Controller Philosophy & REST Architecture

## 1. Purpose

The Controller layer is the application's HTTP entry point.

It is responsible for translating HTTP requests into service calls and translating service results into HTTP responses.

Controllers should remain thin, predictable, and free from business logic.

---

## 2. Controller Philosophy

Controllers orchestrate—not execute.

Their responsibilities are limited to:

- Accept requests
- Validate input
- Invoke services
- Return responses

Controllers should never implement business workflows.

---

## 3. Layer Position

Controllers sit at the boundary between external clients and the application.

```text
Client
    ↓
HTTP Request
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Response flow:

```text
Database
    ↓
Repository
    ↓
Service
    ↓
Controller
    ↓
HTTP Response
    ↓
Client
```

---

## 4. Responsibilities

Controllers are responsible for:

✓ Routing requests

✓ Request validation

✓ Parameter extraction

✓ Authentication context access

✓ Calling services

✓ Returning HTTP responses

---

## 5. Responsibilities That Do NOT Belong

Controllers should never:

✗ Query repositories

✗ Execute business logic

✗ Manage transactions

✗ Perform object mapping manually

✗ Handle persistence

✗ Perform authorization decisions beyond endpoint protection

---

## 6. Thin Controller Principle

A controller method should be easy to understand at a glance.

Typical flow:

```text
Validate

↓

Service

↓

Return Response
```

If a controller grows beyond a few lines of orchestration, responsibilities likely belong elsewhere.

---

## 7. REST-First Design

SprintForge follows REST principles.

Resources—not actions—are exposed.

Good

```text
POST /tasks
GET /tasks/{id}
PATCH /tasks/{id}
DELETE /tasks/{id}
```

Avoid

```text
/createTask
/deleteTask
/updateWorkspace
```

---

## 8. Resource-Oriented APIs

Endpoints should represent business resources.

Examples:

- Users
- Workspaces
- Projects
- Tasks
- Sprints
- Labels

Controllers should be organized around resources rather than use cases.

---

## 9. One Controller Per Aggregate

Each aggregate owns its controller.

Examples:

```
WorkspaceController

TaskController

SprintController

UserController
```

Avoid giant controllers responsible for unrelated resources.

---

## 10. Controller Lifecycle

Request

↓

Validation

↓

Service

↓

DTO

↓

HTTP Response

Controllers should remain stateless.

---

## 11. Dependency Injection

Controllers use constructor injection.

Never use field injection.

Example:

```java
@RequiredArgsConstructor
@RestController
public class WorkspaceController {

    private final WorkspaceService workspaceService;

}
```

---

## 12. Design Goals

Every controller should be:

- Thin
- Stateless
- RESTful
- Predictable
- Easy to test
- Consumer-focused

---

## 13. Architecture Principles

Controllers depend only on:

- DTOs
- Services
- Validation
- Spring MVC

They should never depend directly on repositories.

---

## 14. Request Lifecycle

```text
HTTP Request

↓

Controller

↓

Validation

↓

Service

↓

Mapper

↓

Repository

↓

Database
```

---

## 15. Philosophy Checklist

✓ Thin

✓ RESTful

✓ Stateless

✓ No business logic

✓ Service delegation only

---

# Part 2 – Controller Organization & Structure

## 16. Package Structure

Each feature owns its controller.

```text
workspace/

controller/

WorkspaceController
```

Avoid centralized controller packages for unrelated features.

---

## 17. Naming Convention

Always:

```
<Entity>Controller
```

Examples:

- TaskController
- UserController
- WorkspaceController

Avoid:

- TaskApi
- UserEndpoint
- ControllerUtil

---

## 18. URL Structure

Prefer plural resource names.

Good

```text
/api/v1/tasks
/api/v1/workspaces
/api/v1/projects
```

Avoid verbs in URLs.

---

## 19. API Versioning

SprintForge versions APIs using the URL.

Example

```text
/api/v1/tasks
```

Future versions:

```text
/api/v2/tasks
```

Avoid versioning individual DTOs.

---

## 20. Base Request Mapping

Each controller owns one base path.

Example

```java
@RequestMapping("/api/v1/tasks")
```

All endpoints should derive from this base.

---

## 21. Method Organization

Recommended order:

1. Create
2. Get by ID
3. Get all
4. Search
5. Update
6. Patch
7. Delete
8. Specialized operations

Consistent ordering improves readability.

---

## 22. Constructor Injection

Use:

```java
@RequiredArgsConstructor
```

Avoid:

```java
@Autowired
```

on fields.

---

## 23. Visibility

Controller methods should generally be `public`.

Helper methods should remain `private`.

---

## 24. Package Ownership

Each module owns:

```text
controller/

service/

repository/

mapper/

dto/
```

Maintain feature encapsulation.

---

## 25. Organization Checklist

✓ One controller per aggregate

✓ Resource-oriented

✓ Constructor injection

✓ Versioned URLs

✓ Consistent method ordering

---

# Part 3 – Endpoint Design & HTTP Standards

## 26. GET

Retrieve resources.

Should never modify server state.

Example:

```
GET /tasks/{id}
```

---

## 27. POST

Create new resources.

Example:

```
POST /tasks
```

Returns:

201 Created

---

## 28. PUT

Replace an existing resource.

Entire representation is supplied.

Use only when replacement semantics are intended.

---

## 29. PATCH

Partial update.

Preferred for most update operations.

Example:

```
PATCH /tasks/{id}
```

---

## 30. DELETE

Delete a resource.

Return:

204 No Content

when successful.

---

## 31. Search Endpoints

Prefer query parameters.

Example

```text
GET /tasks?status=OPEN&priority=HIGH
```

Avoid RPC-style endpoints.

---

## 32. Pagination

Standard parameters:

```
?page=

&size=

&sort=
```

Every paginated endpoint should follow the same convention.

---

## 33. Sorting

Use:

```
sort=name,asc
```

Avoid custom sorting formats.

---

## 34. Filtering

Use query parameters.

Example

```
GET /tasks

?assignee=12

&status=TODO
```

---

## 35. Bulk Operations

Batch operations deserve dedicated endpoints.

Example

```
POST /tasks/bulk-update
```

Avoid overloading existing endpoints.

---

## 36. File Uploads

Use dedicated endpoints.

Example

```
POST /tasks/{id}/attachments
```

---

## 37. Downloads

Separate file downloads.

```
GET /attachments/{id}
```

---

## 38. HTTP Semantics

Always respect HTTP meanings.

Never return:

200

when:

404

is appropriate.

---

## 39. Endpoint Checklist

✓ Correct HTTP verb

✓ RESTful URI

✓ Resource-focused

✓ Predictable

---

# Part 4 – Request Handling & Validation

## 40. Request DTOs

Controllers accept Request DTOs—not entities.

Good

```java
TaskCreateRequest
```

Bad

```java
Task
```

---

## 41. Response DTOs

Controllers return Response DTOs—not entities.

---

## 42. Validation

Use:

```java
@Valid
```

on request bodies.

Validation should execute before service invocation.

---

## 43. Path Variables

Use:

```java
@PathVariable
```

for resource identifiers.

---

## 44. Query Parameters

Use:

```java
@RequestParam
```

for:

- filtering
- sorting
- pagination

---

## 45. Request Headers

Use:

```java
@RequestHeader
```

only when necessary.

Examples:

- Idempotency keys
- Correlation IDs
- API keys

---

## 46. Authentication Principal

Use Spring Security support.

Example:

```java
@AuthenticationPrincipal
```

Avoid manually parsing JWTs inside controllers.

---

## 47. Multipart Requests

Use:

```java
MultipartFile
```

for uploads.

Validation still applies.

---

## 48. Validation Flow

```text
HTTP Request

↓

DTO

↓

Validation

↓

Service
```

---

## 49. Request Checklist

✓ DTOs

✓ @Valid

✓ Path variables

✓ Query params

✓ No entities

---

# Part 5 – Response Design & HTTP Status Codes

## 50. Purpose

Responses should be predictable and follow HTTP semantics.

---

## 51. ResponseEntity

Prefer:

```java
ResponseEntity<T>
```

when status or headers vary.

Return DTOs directly only for simple cases with default `200 OK`.

---

## 52. Success Codes

Use:

- 200 OK
- 201 Created
- 202 Accepted (rare)
- 204 No Content

Choose the most appropriate status.

---

## 53. Client Errors

Use:

- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 409 Conflict
- 422 Unprocessable Entity (when applicable)

---

## 54. Server Errors

Unexpected failures return:

500 Internal Server Error

through the global exception handler.

Controllers should not catch and translate unexpected exceptions.

---

## 55. Created Resources

Creation endpoints should return:

201 Created

and include the created resource (or a `Location` header when appropriate).

---

## 56. Empty Responses

Delete operations should return:

204 No Content

Avoid returning placeholder objects.

---

## 57. Error Handling

Controllers delegate exception handling to:

```text
GlobalExceptionHandler
```

Avoid local try-catch blocks for business exceptions.

---

## 58. Consistent Response Structure

Every endpoint should return predictable DTOs.

Avoid mixing:

```text
Map<String,Object>

JSONObject

Custom JSON

DTO
```

Standardize on DTOs.

---

## 59. Response Checklist

✓ Proper status code

✓ Response DTO

✓ Consistent structure

✓ Global exception handling

✓ REST semantics

---

## 60. Midpoint Summary

By this stage, SprintForge controllers are standardized around:

- Thin controllers
- Feature-based organization
- RESTful resource design
- Proper HTTP semantics
- DTO-based communication
- Validation-first request processing
- Consistent response handling

---

Perfect. This completes the `controllers.md` handbook.

---

# SprintForge Engineering Standard
# Controller Layer

# Part 6 – OpenAPI & API Documentation

## 61. Purpose

API documentation is a first-class deliverable, not an afterthought.

Every public endpoint should be discoverable, understandable, and testable through generated OpenAPI documentation.

---

## 62. OpenAPI Standard

SprintForge standardizes on **OpenAPI 3.x** with **SpringDoc**.

Benefits:

- Interactive API documentation
- Client SDK generation
- Consistent endpoint documentation
- Easier frontend integration

Documentation should always be generated from code.

---

## 63. Controller Documentation

Every controller should include a clear description of the resource it manages.

Example:

```java
@Tag(
    name = "Tasks",
    description = "Operations related to task management"
)
```

---

## 64. Endpoint Documentation

Every endpoint should include:

- Purpose
- Expected input
- Response type
- Status codes
- Security requirements

Avoid undocumented public endpoints.

---

## 65. Request Documentation

Request DTO fields should document:

- Purpose
- Required/optional
- Validation constraints
- Example values

Documentation should remain synchronized with validation rules.

---

## 66. Response Documentation

Every response should clearly indicate:

- Response DTO
- Success status
- Error responses
- Pagination (if applicable)

---

## 67. Error Documentation

Document common errors.

Example:

- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 409 Conflict

Clients should know how to handle failures.

---

## 68. Examples

Whenever practical, provide request and response examples.

Examples significantly improve API usability.

---

## 69. Deprecated Endpoints

Use:

```java
@Deprecated
```

and corresponding OpenAPI annotations when endpoints are scheduled for removal.

Avoid silently breaking clients.

---

## 70. Documentation Checklist

✓ Tagged controller

✓ Documented endpoints

✓ Request examples

✓ Response examples

✓ Error responses

✓ Security documented

---

# Part 7 – Security & Authorization

## 71. Purpose

Controllers enforce the application's HTTP security boundary.

Authentication and authorization begin before business logic executes.

---

## 72. Authentication

Controllers should assume authentication has already been performed by Spring Security.

Avoid manually validating JWTs.

---

## 73. Authorization

Protect endpoints using method-level security.

Examples:

```java
@PreAuthorize(...)
```

```java
@RolesAllowed(...)
```

Business permission evaluation belongs in the service layer when domain-specific decisions are required.

---

## 74. Current User

Retrieve authenticated users using:

```java
@AuthenticationPrincipal
```

Avoid extracting user information directly from tokens.

---

## 75. Public Endpoints

Examples:

- Login
- Register
- Refresh Token
- Health Check

These should remain explicitly documented as public.

---

## 76. Protected Endpoints

Most business endpoints require authentication.

Controllers should clearly distinguish between:

Public

↓

Authenticated

↓

Role-specific

---

## 77. Input Trust

Never trust client input.

Validate every request regardless of frontend validation.

---

## 78. Sensitive Data

Controllers should never expose:

- Password hashes
- Secrets
- Internal IDs (when inappropriate)
- Tokens
- Security metadata

Response DTOs should already enforce this.

---

## 79. Security Checklist

✓ Authentication

✓ Authorization

✓ Validation

✓ DTOs

✓ No sensitive fields

---

# Part 8 – Controller Anti-Patterns & Code Smells

## 80. Purpose

Controllers should remain thin and predictable.

The following anti-patterns reduce maintainability and violate architectural boundaries.

---

## 81. Business Logic

Bad:

```java
if (task.canBeClosed()) {
    ...
}
```

Controllers should delegate decisions to services.

---

## 82. Repository Injection

Never inject repositories into controllers.

Bad:

```java
TaskRepository
```

Good:

```java
TaskService
```

---

## 83. Manual Mapping

Avoid:

```java
TaskResponse dto = new TaskResponse(...);
```

inside controllers.

Use dedicated mappers.

---

## 84. Transaction Management

Controllers should never use:

```java
@Transactional
```

Transactions belong in the service layer.

---

## 85. Exception Handling

Avoid:

```java
try {

} catch (...)
```

for business exceptions.

Use a global exception handler.

---

## 86. Giant Controllers

Avoid controllers with dozens of unrelated endpoints.

Split by aggregate.

---

## 87. Returning Entities

Never return JPA entities.

Always return Response DTOs.

---

## 88. Utility Controllers

Avoid generic controllers like:

```text
ApiController

UtilityController

ApplicationController
```

Organize around business resources.

---

## 89. Anti-Pattern Checklist

Avoid:

✗ Business logic

✗ Repository access

✗ Manual mapping

✗ Transactions

✗ Entity exposure

✗ Giant controllers

✗ Utility controllers

---

# Part 9 – Reference Templates & Implementation Blueprints

## 90. Standard Controller

```java
@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

}
```

---

## 91. Create Endpoint

```java
@PostMapping
public ResponseEntity<WorkspaceResponse> create(
        @Valid @RequestBody WorkspaceCreateRequest request) {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(workspaceService.create(request));
}
```

---

## 92. Get Endpoint

```java
@GetMapping("/{id}")
public ResponseEntity<WorkspaceResponse> get(
        @PathVariable UUID id) {

    return ResponseEntity.ok(
            workspaceService.get(id));
}
```

---

## 93. Update Endpoint

```java
@PatchMapping("/{id}")
public ResponseEntity<WorkspaceResponse> update(
        @PathVariable UUID id,
        @Valid @RequestBody WorkspaceUpdateRequest request) {

    return ResponseEntity.ok(
            workspaceService.update(id, request));
}
```

---

## 94. Delete Endpoint

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(
        @PathVariable UUID id) {

    workspaceService.delete(id);

    return ResponseEntity.noContent().build();
}
```

---

## 95. Search Endpoint

```java
@GetMapping
public PageResponse<TaskSummaryResponse> search(
        TaskSearchRequest request) {

    return taskService.search(request);
}
```

---

## 96. File Upload

```java
@PostMapping("/{id}/attachments")
public AttachmentResponse upload(
        @PathVariable UUID id,
        MultipartFile file) {

    return attachmentService.upload(id, file);
}
```

---

## 97. Blueprint Checklist

✓ RESTful URL

✓ DTO input

✓ DTO output

✓ Validation

✓ Proper status codes

✓ Thin controller

---

# Part 10 – Governance & Final Principles

## 98. Purpose

Controller standards ensure a consistent API across every SprintForge module.

Every controller should present a predictable experience to API consumers.

---

## 99. Ownership

Each feature owns its own controller.

Example:

```text
workspace/

controller/

WorkspaceController
```

Avoid shared controller implementations across unrelated modules.

---

## 100. Code Review Requirements

Every controller review should verify:

- Correct URL design
- Proper HTTP method
- Thin controller
- DTO usage
- Validation
- Security annotations
- Response consistency

---

## 101. Documentation

Every public endpoint should be documented using OpenAPI annotations.

Changes to endpoints must include documentation updates.

---

## 102. Testing Expectations

Controller tests should verify:

- Request validation
- Status codes
- Response bodies
- Error handling
- Authentication
- Authorization
- Pagination
- File uploads (where applicable)

Prefer focused web-layer tests using `@WebMvcTest` and integration tests where full application behavior is required.

---

## 103. Evolution Strategy

API evolution should prioritize backward compatibility.

Preferred changes:

✓ Add optional fields

✓ Introduce new endpoints

✓ Deprecate before removal

Avoid unnecessary breaking changes.

---

## 104. AI-Assisted Development

AI tools can generate controller boilerplate, but every generated controller must be reviewed to ensure it:

- Uses the correct REST semantics
- Delegates business logic to services
- Uses DTOs rather than entities
- Applies validation correctly
- Returns appropriate HTTP status codes
- Follows SprintForge package and naming conventions

---

## 105. Final Controller Principles

Every SprintForge controller should be:

✓ Thin

✓ RESTful

✓ Stateless

✓ Secure

✓ Well documented

✓ Easy to test

✓ DTO-based

✓ Validation-first

✓ Service-oriented

✓ Consumer-focused

---

## 106. Controller Compliance Checklist

Before merging a controller:

### Architecture

✓ One controller per aggregate

✓ Constructor injection

✓ Feature-local package

### API Design

✓ RESTful URLs

✓ Correct HTTP verbs

✓ Versioned endpoints

✓ Consistent response structure

### Requests

✓ DTO input

✓ `@Valid`

✓ Proper parameter binding

### Responses

✓ Response DTOs

✓ Correct status codes

✓ Global exception handling

### Security

✓ Authentication enforced

✓ Authorization applied

✓ No sensitive data exposed

### Quality

✓ No business logic

✓ No repository access

✓ No manual mapping

✓ Tests included

✓ OpenAPI documentation complete

---

## 107. Closing Statement

The Controller layer is SprintForge's public interface.

It defines how external clients interact with the application while shielding the domain model from HTTP-specific concerns.

By keeping controllers thin, resource-oriented, and focused on orchestration, SprintForge maintains a clean separation between transport, business logic, and persistence.

Following these standards ensures APIs remain consistent, secure, maintainable, and easy to evolve as the platform grows.

---

