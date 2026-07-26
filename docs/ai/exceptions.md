
# SprintForge Engineering Standard
# Exception Handling

# Part 1 – Exception Philosophy & Architecture

## 1. Purpose

The Exception layer provides a consistent mechanism for representing, propagating, and handling failures throughout SprintForge.

Rather than allowing unexpected failures to leak across application boundaries, exceptions communicate meaningful information between layers and ultimately produce standardized HTTP responses.

---

## 2. Exception Philosophy

Exceptions represent **exceptional situations**, not normal application flow.

Good examples:

- Resource not found
- Permission denied
- Duplicate email
- Invalid state transition
- Validation failure

Bad examples:

- Loop termination
- Boolean replacement
- Expected conditional logic

Business logic should not rely on exceptions for normal control flow.

---

## 3. Goals

SprintForge exception handling should be:

✓ Predictable

✓ Consistent

✓ Layered

✓ Secure

✓ Observable

✓ Easy to debug

✓ Easy to extend

---

## 4. Layer Position

Exceptions originate in different layers but ultimately converge into a standardized HTTP response.

```text id="fcpwql"
Controller
    ↓
Service
    ↓
Repository
```

Exceptions travel upward:

```text id="jqvb2l"
Repository
    ↑
Service
    ↑
Controller
    ↑
GlobalExceptionHandler
    ↑
HTTP Response
```

---

## 5. Exception Flow

Typical request lifecycle:

```text id="rradcc"
HTTP Request

↓

Controller

↓

Service

↓

Repository

↓

Exception

↓

GlobalExceptionHandler

↓

ErrorResponse DTO

↓

HTTP Response
```

---

## 6. Responsibilities

The exception layer is responsible for:

✓ Representing failures

✓ Communicating business errors

✓ Mapping failures to HTTP responses

✓ Standardizing error payloads

✓ Logging failures

✓ Preserving useful diagnostics

---

## 7. Responsibilities That Do NOT Belong

Exceptions should never:

✗ Replace validation

✗ Replace business rules

✗ Contain business workflows

✗ Hide programming errors

✗ Swallow unexpected failures

---

## 8. Fail Fast Principle

Detect failures as early as possible.

Example:

```text id="v4gj9h"
Controller

↓

Validation

↓

Service

↓

Repository
```

Reject invalid input before expensive operations occur.

---

## 9. Checked vs Unchecked Exceptions

SprintForge standardizes on **unchecked exceptions** (`RuntimeException`) for application and business failures.

Reasons:

- Cleaner service signatures
- Better Spring integration
- Reduced boilerplate
- Consistent propagation

Checked exceptions should be reserved for exceptional cases where callers are expected to recover explicitly.

---

## 10. Exception Hierarchy

Every application-specific exception should derive from a common base.

```text id="c8y6f4"
RuntimeException

↓

ApplicationException

↓

BusinessException

↓

Specific Exceptions
```

This provides a single abstraction for application failures.

---

## 11. Separation from HTTP

Service-layer exceptions should remain independent of HTTP concepts.

Avoid embedding HTTP status codes directly inside business logic.

The mapping to HTTP belongs in the global exception handler.

---

## 12. Exception Design Goals

Every exception should be:

- Specific
- Immutable
- Meaningful
- Lightweight
- Easy to identify
- Easy to log

---

## 13. Architecture Principles

Controllers throw nothing intentionally.

Services throw business exceptions.

Repositories may throw persistence exceptions.

The global handler converts them into HTTP responses.

---

## 14. Philosophy Checklist

✓ Runtime exceptions

✓ Layered propagation

✓ Fail fast

✓ No HTTP coupling

✓ Centralized handling

---

# Part 2 – Exception Hierarchy & Organization

## 15. Package Structure

Each module may define feature-specific exceptions, while shared exceptions live in a common package.

```text id="k1z4ie"
common/

exception/

workspace/

exception/

task/

exception/
```

---

## 16. Base Exception

Create a shared base exception.

Example:

```java id="rjytk8"
ApplicationException
```

Every custom exception should extend this class directly or indirectly.

---

## 17. Business Exception

Business rule violations inherit from:

```java id="5n77dc"
BusinessException
```

Examples:

- WorkspaceAlreadyArchivedException
- SprintAlreadyStartedException

---

## 18. Resource Not Found

Missing resources use dedicated exceptions.

Examples:

```java id="jlwmu5"
WorkspaceNotFoundException

TaskNotFoundException

UserNotFoundException
```

Avoid generic "NotFoundException" for unrelated resources.

---

## 19. Conflict Exceptions

Represent uniqueness or state conflicts.

Examples:

- EmailAlreadyExistsException
- DuplicateWorkspaceNameException
- UsernameAlreadyTakenException

---

## 20. Validation Exceptions

Validation failures should normally originate from Bean Validation.

Custom validation exceptions should be used only for business validations that cannot be expressed declaratively.

---

## 21. Authorization Exceptions

Permission failures should use dedicated exceptions when business rules require additional checks beyond Spring Security.

Examples:

- WorkspaceAccessDeniedException
- TaskModificationForbiddenException

---

## 22. Illegal State Exceptions

Invalid business state transitions deserve dedicated exceptions.

Example:

```text id="p0kkhf"
Completed Sprint

↓

Start Sprint

↓

SprintAlreadyCompletedException
```

---

## 23. External Service Exceptions

Failures from external integrations should be wrapped.

Examples:

- EmailDeliveryException
- StorageServiceException
- PaymentGatewayException

Avoid exposing vendor-specific exceptions to the rest of the application.

---

## 24. Naming Convention

Always use:

```text id="r5lfdn"
<Resource><Problem>Exception
```

Examples:

- UserAlreadyExistsException
- WorkspaceNotFoundException
- InvalidInviteTokenException

---

## 25. Organization Checklist

✓ Base exception

✓ Business hierarchy

✓ Resource-specific names

✓ Feature ownership

✓ No generic catch-all exceptions

---

# Part 3 – Exception Propagation

## 26. Throw at the Correct Layer

Controllers rarely throw exceptions.

Services throw business exceptions.

Repositories throw persistence exceptions.

---

## 27. Do Not Swallow Exceptions

Bad:

```java id="9uw7mr"
catch (...) {
}
```

Every exception should either:

- Be handled appropriately
- Be propagated

---

## 28. Wrap External Exceptions

Convert infrastructure exceptions into application-specific exceptions.

Example:

```text id="o2pn7o"
IOException

↓

StorageServiceException
```

This prevents infrastructure details from leaking.

---

## 29. Preserve Cause

Always retain the original exception as the cause.

This preserves stack traces for debugging.

---

## 30. Avoid Generic RuntimeException

Bad:

```java id="dbjlwm"
throw new RuntimeException(...)
```

Prefer:

```java id="jlwmq4"
throw new WorkspaceNotFoundException(...)
```

Specific exceptions improve readability and error handling.

---

## 31. Service Boundaries

Services should throw meaningful business exceptions rather than low-level persistence exceptions.

---

## 32. Repository Exceptions

Repositories may propagate Spring Data exceptions.

Services decide whether to translate them.

---

## 33. Nested Exceptions

Avoid excessive nesting.

One meaningful wrapper is usually sufficient.

---

## 34. Propagation Checklist

✓ Preserve cause

✓ Throw meaningful exceptions

✓ Wrap infrastructure

✓ No swallowed exceptions

---

# Part 4 – Global Exception Handling

## 35. Purpose

SprintForge uses centralized exception handling.

Controllers remain free from repetitive try-catch blocks.

---

## 36. Global Handler

Use:

```java id="jlwmrf"
@RestControllerAdvice
```

for application-wide exception handling.

---

## 37. Exception Mapping

Map exceptions to appropriate HTTP responses.

Example:

```text id="6w1k1q"
WorkspaceNotFoundException

↓

404
```

---

## 38. Business Exceptions

Business exceptions should produce predictable client-facing messages.

Avoid exposing internal implementation details.

---

## 39. Validation Errors

Validation failures should return:

400 Bad Request

with detailed field-level information.

---

## 40. Unexpected Exceptions

Unhandled exceptions should become:

500 Internal Server Error

while logging complete diagnostic information internally.

---

## 41. ErrorResponse DTO

Every error should use a standardized response model.

Typical fields:

- timestamp
- status
- errorCode
- message
- path
- correlationId

---

## 42. Consistent Responses

Every endpoint should produce the same error structure regardless of the originating controller.

---

## 43. Logging Integration

Global handlers should log exceptions using the application's logging framework.

Logging level should depend on exception severity.

---

## 44. Handler Checklist

✓ Centralized

✓ Standard responses

✓ Proper status mapping

✓ Consistent logging

---

# Part 5 – Error Response Design

## 45. Purpose

Clients should receive structured, machine-readable error information.

---

## 46. Standard Error DTO

Example:

```java id="jlwmst"
public record ErrorResponse(

    Instant timestamp,

    int status,

    String error,

    String code,

    String message,

    String path,

    String correlationId
) {}
```

---

## 47. Field Meanings

Recommended fields:

- **timestamp** – when the error occurred
- **status** – HTTP status code
- **error** – HTTP status text
- **code** – application-specific error code
- **message** – user-facing description
- **path** – request URI
- **correlationId** – request trace identifier

---

## 48. Error Codes

Application error codes should remain stable.

Examples:

```text id="jlwmu1"
USR_001

TASK_004

AUTH_002

WORKSPACE_007
```

Codes are more reliable for client integrations than free-form messages.

---

## 49. User Messages

Messages should be:

✓ Clear

✓ Actionable

✓ Non-technical

Avoid exposing stack traces or SQL details.

---

## 50. Internal Diagnostics

Detailed debugging information belongs in logs—not in API responses.

Clients should receive only the information necessary to understand and react to the error.

---

## 51. Validation Errors

Field validation failures should include structured field-level details.

Example:

```text id="jlwmu2"
field

↓

message
```

Allow clients to display validation feedback accurately.

---

## 52. Correlation IDs

Every error response should include a correlation ID when request tracing is enabled.

This simplifies debugging across logs and distributed systems.

---

## 53. Localization

Error messages may be localized in the future.

Application error codes should remain language-independent.

---

## 54. Midpoint Summary

At this stage, SprintForge exception handling provides:

- A clear exception hierarchy
- Consistent propagation rules
- Centralized global handling
- Standardized error responses
- Stable application error codes
- Secure client-facing messages

---
Perfect. This completes the `exceptions.md` handbook.

---

# SprintForge Engineering Standard
# Exception Handling

# Part 6 – Logging & Observability

## 55. Purpose

Exception handling is not complete without proper observability.

Every meaningful failure should leave enough diagnostic information to allow developers and operators to understand **what happened, where it happened, and why it happened**.

---

## 56. Logging Philosophy

Exceptions should be logged exactly once.

Preferred flow:

```text
Exception

↓

GlobalExceptionHandler

↓

Structured Log

↓

Monitoring System
```

Avoid logging the same exception at multiple layers unless additional context is being added.

---

## 57. Log Levels

Choose log levels based on severity.

Recommended:

| Situation | Level |
|-----------|-------|
| Validation failure | INFO / WARN |
| Business exception | WARN |
| Unauthorized access | WARN |
| External service failure | ERROR |
| Unexpected exception | ERROR |
| Programming bug | ERROR |

Do not log expected business failures as ERROR unless they indicate a system problem.

---

## 58. Structured Logging

Logs should be structured rather than free-form.

Include:

- Timestamp
- Exception type
- Error code
- Correlation ID
- User ID (when available)
- Request path
- HTTP method

Structured logs simplify searching and monitoring.

---

## 59. Correlation IDs

Every request should ideally receive a unique correlation ID.

Example flow:

```text
Incoming Request

↓

Correlation ID Generated

↓

Logs

↓

Error Response

↓

Monitoring
```

This allows tracing a request across the entire application.

---

## 60. External Monitoring

Exceptions should integrate with monitoring tools.

Examples:

- Sentry
- Datadog
- Grafana
- Elastic Stack
- OpenTelemetry

The exception layer should support observability without being tightly coupled to a specific vendor.

---

## 61. Avoid Excessive Logging

Do not log:

- Every validation error as ERROR
- The same exception multiple times
- Large request payloads unnecessarily
- Sensitive data

Logs should remain useful rather than noisy.

---

## 62. Metrics

Exception handling should contribute to application metrics.

Examples:

- Number of 404 responses
- Authentication failures
- Validation failures
- Database failures
- External service failures

Metrics help identify trends before they become incidents.

---

## 63. Alerting

Critical exceptions should trigger alerts.

Examples:

- Database unavailable
- Storage service unavailable
- Authentication service failure
- Unexpected spikes in 500 responses

Alerting should focus on operational issues rather than expected business exceptions.

---

## 64. Observability Checklist

✓ Structured logging

✓ Correlation IDs

✓ Appropriate log levels

✓ Metrics

✓ Monitoring integration

✓ No duplicate logging

---

# Part 7 – Security & Sensitive Information

## 65. Purpose

Error handling must protect sensitive information while remaining useful to clients.

---

## 66. Never Leak Internal Details

Clients should never receive:

- Stack traces
- SQL queries
- Database schema
- File system paths
- Server implementation details

Only meaningful, consumer-friendly information should be returned.

---

## 67. Generic Server Errors

Unexpected failures should produce generic responses.

Example:

```text
500 Internal Server Error

An unexpected error occurred.
```

Detailed diagnostics belong in logs.

---

## 68. Sensitive Exception Messages

Avoid exposing exception messages originating from infrastructure libraries.

Bad:

```text
ORA-00942: table or view does not exist
```

Good:

```text
Database operation failed.
```

---

## 69. Authentication Failures

Do not reveal whether:

- Username exists
- Email exists
- Password was correct

Prefer generic authentication failure messages.

---

## 70. Authorization Failures

Return:

403 Forbidden

without revealing protected resources or internal permission models.

---

## 71. Validation Messages

Validation responses should help users correct input without exposing implementation details.

Good:

```text
Email must be a valid email address.
```

Bad:

```text
Regex validation failed.
```

---

## 72. Audit Logging

Security-related exceptions should be logged for auditing.

Examples:

- Repeated failed logins
- Unauthorized access attempts
- Permission violations

Audit logs should be protected from unauthorized modification.

---

## 73. Security Checklist

✓ No stack traces

✓ Generic server errors

✓ Secure authentication messages

✓ Audit logging

✓ No infrastructure leakage

---

# Part 8 – Exception Anti-Patterns & Code Smells

## 74. Purpose

Poor exception design makes systems difficult to debug and maintain.

Avoid the following anti-patterns.

---

## 75. Catching Exception

Bad:

```java
catch (Exception e)
```

Catch the most specific exception possible.

---

## 76. Swallowing Exceptions

Bad:

```java
catch (...) {

}
```

Ignoring exceptions hides failures and complicates debugging.

---

## 77. Throwing Generic RuntimeException

Avoid:

```java
throw new RuntimeException(...)
```

Use meaningful application-specific exceptions instead.

---

## 78. Using Exceptions for Control Flow

Bad:

```text
Try

↓

Exception

↓

Continue Loop
```

Exceptions should not replace normal conditional logic.

---

## 79. Logging and Rethrowing

Bad:

```text
Log

↓

Throw

↓

Log Again

↓

Throw Again
```

Log once at the appropriate layer.

---

## 80. Losing the Cause

Always preserve the original exception.

Bad:

```java
throw new StorageException("Upload failed");
```

Good:

```java
throw new StorageException(
    "Upload failed",
    cause
);
```

---

## 81. Huge Exception Hierarchies

Avoid excessive inheritance.

Example:

```text
BusinessException

↓

TaskException

↓

TaskUpdateException

↓

TaskStatusException

↓

TaskAlreadyClosedException
```

Keep the hierarchy understandable.

---

## 82. HTTP Logic Inside Services

Avoid:

```java
throw new ResponseStatusException(...)
```

inside services.

Services should remain HTTP-independent.

---

## 83. Entity Leakage

Do not embed entities inside exceptions.

Exceptions should carry only the information required for handling and logging.

---

## 84. Anti-Pattern Checklist

Avoid:

✗ Generic RuntimeException

✗ Catch Exception

✗ Swallowed exceptions

✗ HTTP coupling

✗ Lost causes

✗ Double logging

✗ Control-flow exceptions

---

# Part 9 – Reference Templates & Implementation Blueprints

## 85. Base Exception

```java
public abstract class ApplicationException
        extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(
            String message,
            Throwable cause) {
        super(message, cause);
    }

}
```

---

## 86. Business Exception

```java
public class BusinessException
        extends ApplicationException {

    public BusinessException(String message) {
        super(message);
    }

}
```

---

## 87. Resource Not Found

```java
public class WorkspaceNotFoundException
        extends BusinessException {

    public WorkspaceNotFoundException(UUID id) {
        super("Workspace not found: " + id);
    }

}
```

---

## 88. Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
        WorkspaceNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handle(...) {
        ...
    }

}
```

---

## 89. Error Response DTO

```java
public record ErrorResponse(

    Instant timestamp,

    int status,

    String error,

    String code,

    String message,

    String path,

    String correlationId

) {}
```

---

## 90. Validation Error DTO

```java
public record ValidationError(

    String field,

    String message

) {}
```

---

## 91. Exception Mapping Blueprint

```text
Exception

↓

GlobalExceptionHandler

↓

ErrorResponse

↓

HTTP Response
```

---

## 92. Logging Blueprint

```text
Exception

↓

Structured Log

↓

Monitoring

↓

Alert (if critical)
```

---

## 93. Blueprint Checklist

✓ Base exception

✓ Specific subclasses

✓ Global handler

✓ Error DTO

✓ Logging

✓ Tests

---

# Part 10 – Governance & Final Principles

## 94. Purpose

A consistent exception strategy ensures every SprintForge module behaves predictably when failures occur.

---

## 95. Ownership

Each feature owns its business-specific exceptions.

Example:

```text
workspace/

exception/

WorkspaceNotFoundException
```

Shared infrastructure exceptions belong in a common package.

---

## 96. Code Review Requirements

Every exception review should verify:

- Appropriate exception type
- Clear naming
- Preserved cause
- Proper HTTP mapping
- Secure error messages
- Logging strategy

---

## 97. Documentation

Public APIs should document expected error responses.

Include:

- HTTP status
- Error codes
- Response schema
- Common failure scenarios

---

## 98. Testing Expectations

Exception tests should verify:

- Correct exception thrown
- Global handler mapping
- HTTP status
- Error response body
- Validation errors
- Logging behavior (where practical)

---

## 99. Evolution Strategy

As SprintForge grows:

- Introduce new exceptions intentionally
- Reuse existing abstractions where appropriate
- Deprecate obsolete exceptions gradually
- Keep error codes stable for API consumers

Avoid breaking client integrations through unnecessary changes.

---

## 100. AI-Assisted Development

AI can generate exception classes and handlers, but every generated artifact should be reviewed to ensure it:

- Extends the correct base class
- Preserves the original cause
- Uses meaningful names
- Produces secure client messages
- Maps correctly in the global handler
- Follows SprintForge error code conventions

---

## 101. Final Exception Principles

Every SprintForge exception should be:

✓ Specific

✓ Layer-appropriate

✓ Lightweight

✓ Secure

✓ Easy to log

✓ Easy to test

✓ Independent of HTTP

✓ Consistently handled

✓ Observable

✓ Well documented

---

## 102. Exception Compliance Checklist

Before merging a new exception:

### Design

✓ Extends the correct base exception

✓ Clearly named

✓ Meaningful message

✓ Preserves cause (when wrapping)

### Handling

✓ Mapped in `GlobalExceptionHandler`

✓ Correct HTTP status

✓ Standard `ErrorResponse`

### Security

✓ No sensitive information exposed

✓ Generic server messages where appropriate

✓ Audit logging considered

### Quality

✓ Unit tests included

✓ Documentation updated

✓ Stable error code assigned (if applicable)

---

## 103. Closing Statement

Exception handling is the backbone of SprintForge's failure management strategy.

A well-designed exception architecture ensures that failures are:

- Detected early
- Propagated consistently
- Logged appropriately
- Presented securely
- Understood easily by both developers and API consumers

By separating business exceptions from infrastructure failures, centralizing HTTP translation, and standardizing error responses, SprintForge provides a robust and maintainable approach to error handling that scales with the application.

---

