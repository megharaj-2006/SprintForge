# SprintForge — Complete Backend API Inventory

This document provides the definitive, complete catalog of every backend API endpoint discovered in SprintForge.
Total Discovered Endpoints: **642** across **110 active controllers**.

---

## Controller: `AIExecutionController`

- **Package:** `org.SprintForge.modules.ai.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/ai/controller/AIExecutionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/ai/controller/AIExecutionController.java)
- **Tag / Domain:** AIExecutionController (N/A)
- **Endpoints Count:** 8

### `API-002` — POST `/ai/analyze`

- **Method Name:** `analyze`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-004` — POST `/ai/classify`

- **Method Name:** `classify`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-005` — POST `/ai/extract`

- **Method Name:** `extract`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-006` — POST `/ai/generate`

- **Method Name:** `generate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-009` — POST `/ai/predict`

- **Method Name:** `predict`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-013` — POST `/ai/rewrite`

- **Method Name:** `rewrite`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-015` — POST `/ai/summarize`

- **Method Name:** `summarize`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-016` — POST `/ai/translate`

- **Method Name:** `translate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AIExecutionDTORequest`
- **Response Model Fields (`ResponseEntity<AIResponse>`):**
  - `content`: `String`
  - `usage`: `TokenUsage`
  - `latencyMs`: `long`
  - `estimatedCost`: `double`
  - `finishReason`: `String`
  - `providerKey`: `String`
  - `modelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

## Controller: `AIPlatformController`

- **Package:** `org.SprintForge.modules.ai.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/ai/controller/AIPlatformController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/ai/controller/AIPlatformController.java)
- **Tag / Domain:** AIPlatformController (N/A)
- **Endpoints Count:** 7

### `API-003` — GET `/ai/capabilities`

- **Method Name:** `getCapabilities`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AICapability>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AICapability>>`):**
  - `capabilityKey`: `String`
  - `displayName`: `String`
  - `description`: `String`
  - `defaultProviderKey`: `String`
  - `defaultModelKey`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-007` — GET `/ai/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Boolean>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-008` — GET `/ai/models`

- **Method Name:** `getModels`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AIModel>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AIModel>>`):**
  - `provider`: `AIProvider`
  - `modelKey`: `String`
  - `displayName`: `String`
  - `capabilities`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-010` — GET `/ai/prompts`

- **Method Name:** `getPrompts`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<PromptTemplate>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<PromptTemplate>>`):**
  - `templateKey`: `String`
  - `name`: `String`
  - `description`: `String`
  - `systemPrompt`: `String`
  - `userPrompt`: `String`
  - `outputSchema`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-011` — POST `/ai/prompts/render`

- **Method Name:** `renderPrompt`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<PromptResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `PromptRenderRequest`
  - Fields:
    - `templateKey`: `String`
    - `variables`: `Map<String, Object>`
- **Response Model Fields (`ResponseEntity<PromptResponse>`):**
  - `templateKey`: `String`
  - `name`: `String`
  - `compiledSystemPrompt`: `String`
  - `compiledUserPrompt`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-012` — GET `/ai/providers`

- **Method Name:** `getProviders`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AIProvider>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AIProvider>>`):**
  - `providerKey`: `String`
  - `displayName`: `String`
  - `baseUrl`: `String`
  - `defaultModel`: `String`
  - `configuration`: `String`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

### `API-014` — GET `/ai/statistics`

- **Method Name:** `getStatistics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AIStatisticsResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AIStatisticsResponse>`):**
  - `totalExecutions`: `long`
  - `totalTokens`: `long`
  - `totalEstimatedCost`: `double`
  - `averageLatencyMs`: `double`
  - `providerHealth`: `Map<String, Boolean>`
- **Frontend Destination:** SprintForge AI Assistant Drawer / AI Copilot Bar (/ai/studio)
- **UI Trigger / Interaction:** User prompts AI for task breakdown, sprint summary, code review, or workflow automation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AISkillsTest, PromptEngineTest, PromptOrchestratorTest, ProviderSDKTest)

---

## Controller: `ActivityFeedController`

- **Package:** `org.SprintForge.platform.communication.activity`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/activity/ActivityFeedController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/activity/ActivityFeedController.java)
- **Tag / Domain:** ActivityFeedController (N/A)
- **Endpoints Count:** 8

### `API-001` — GET `/activity`

- **Method Name:** `getGlobalActivity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `actorId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `activityType`: `ActivityType`
  - `summary`: `String`
  - `metadata`: `String`
  - `visibility`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-020` — GET `/api/v1/activity`

- **Method Name:** `getGlobalActivity`
- **Summary / Purpose:** Get global activity timeline
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ActivityFeed>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ActivityFeed>>`):**
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `activityType`: `String`
  - `summary`: `String`
  - `metadata`: `String`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-145` — GET `/api/v1/projects/{id}/activity`

- **Method Name:** `getProjectActivity`
- **Summary / Purpose:** Get project activity feed
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ActivityFeed>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ActivityFeed>>`):**
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `activityType`: `String`
  - `summary`: `String`
  - `metadata`: `String`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-294` — GET `/api/v1/tasks/{id}/activity`

- **Method Name:** `getTaskActivity`
- **Summary / Purpose:** Get task activity timeline
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ActivityFeed>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ActivityFeed>>`):**
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `activityType`: `String`
  - `summary`: `String`
  - `metadata`: `String`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-391` — GET `/api/v1/users/{id}/activity`

- **Method Name:** `getUserActivity`
- **Summary / Purpose:** Get user activity feed
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ActivityFeed>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ActivityFeed>>`):**
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `activityType`: `String`
  - `summary`: `String`
  - `metadata`: `String`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-590` — GET `/projects/{id}/activity`

- **Method Name:** `getProjectActivity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `actorId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `activityType`: `ActivityType`
  - `summary`: `String`
  - `metadata`: `String`
  - `visibility`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-607` — GET `/tasks/{id}/activity`

- **Method Name:** `getTaskActivity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `actorId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `activityType`: `ActivityType`
  - `summary`: `String`
  - `metadata`: `String`
  - `visibility`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-608` — GET `/users/{id}/activity`

- **Method Name:** `getUserActivity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<ActivityFeedResponse>>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `actorId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `activityType`: `ActivityType`
  - `summary`: `String`
  - `metadata`: `String`
  - `visibility`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

## Controller: `AdvancedSearchController`

- **Package:** `org.SprintForge.platform.search.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/search/controller/AdvancedSearchController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/search/controller/AdvancedSearchController.java)
- **Tag / Domain:** AdvancedSearchController (N/A)
- **Endpoints Count:** 4

### `API-596` — POST `/search/advanced`

- **Method Name:** `advancedSearch`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SearchResultResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AdvancedSearchRequest`
  - Fields:
    - `query`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `matchMode`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `sortBy`: `String`
    - `query`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `matchMode`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `sortBy`: `String`
- **Response Model Fields (`ResponseEntity<List<SearchResultResponse>>`):**
  - `id`: `Long`
  - `entityType`: `String`
  - `entityId`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `title`: `String`
  - `contentSnippet`: `String`
  - `highlightedTitle`: `String`
  - `relevanceScore`: `double`
  - `lastIndexedAt`: `Instant`
  - `id`: `Long`
  - `entityType`: `String`
  - *...and 8 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-597` — POST `/search/filter`

- **Method Name:** `filterSearch`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SearchResultResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AdvancedSearchRequest`
  - Fields:
    - `query`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `matchMode`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `sortBy`: `String`
    - `query`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `matchMode`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `sortBy`: `String`
- **Response Model Fields (`ResponseEntity<List<SearchResultResponse>>`):**
  - `id`: `Long`
  - `entityType`: `String`
  - `entityId`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `title`: `String`
  - `contentSnippet`: `String`
  - `highlightedTitle`: `String`
  - `relevanceScore`: `double`
  - `lastIndexedAt`: `Instant`
  - `id`: `Long`
  - `entityType`: `String`
  - *...and 8 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-604` — POST `/search/save`

- **Method Name:** `saveSearch`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedSearchResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `SaveSearchRequest`
  - Fields:
    - `name`: `String`
    - `queryText`: `String`
    - `workspaceId`: `String`
    - `userId`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `isShared`: `boolean`
    - `name`: `String`
    - `queryText`: `String`
    - `workspaceId`: `String`
    - `userId`: `String`
    - `filters`: `List<SearchFilterRule>`
    - `isShared`: `boolean`
- **Response Model Fields (`ResponseEntity<SavedSearchResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `queryText`: `String`
  - `filters`: `String`
  - `isShared`: `boolean`
  - `createdAt`: `Instant`
  - `id`: `Long`
  - `name`: `String`
  - `queryText`: `String`
  - `filters`: `String`
  - `isShared`: `boolean`
  - `createdAt`: `Instant`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-605` — GET `/search/saved`

- **Method Name:** `getSavedSearches`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SavedSearchResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SavedSearchResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `queryText`: `String`
  - `filters`: `String`
  - `isShared`: `boolean`
  - `createdAt`: `Instant`
  - `id`: `Long`
  - `name`: `String`
  - `queryText`: `String`
  - `filters`: `String`
  - `isShared`: `boolean`
  - `createdAt`: `Instant`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

## Controller: `AnalyticsController`

- **Package:** `org.SprintForge.platform.analytics.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/analytics/controller/AnalyticsController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/analytics/controller/AnalyticsController.java)
- **Tag / Domain:** AnalyticsController (N/A)
- **Endpoints Count:** 6

### `API-017` — GET `/analytics/snapshots`

- **Method Name:** `getSnapshots`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AnalyticsSnapshotResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AnalyticsSnapshotResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `snapshotType`: `String`
  - `metrics`: `String`
  - `capturedAt`: `Instant`
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `snapshotType`: `String`
  - `metrics`: `String`
  - `capturedAt`: `Instant`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-018` — POST `/analytics/snapshots`

- **Method Name:** `createSnapshot`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AnalyticsSnapshotResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AnalyticsSnapshotResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `snapshotType`: `String`
  - `metrics`: `String`
  - `capturedAt`: `Instant`
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `snapshotType`: `String`
  - `metrics`: `String`
  - `capturedAt`: `Instant`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-019` — GET `/analytics/trends`

- **Method Name:** `getTrend`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AnalyticsTrendResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AnalyticsTrendResponse>`):**
  - `metricName`: `String`
  - `currentValue`: `double`
  - `previousValue`: `double`
  - `percentageChange`: `double`
  - `trendDirection`: `String`
  - `details`: `Map<String, Object>`
  - `metricName`: `String`
  - `currentValue`: `double`
  - `previousValue`: `double`
  - `percentageChange`: `double`
  - `trendDirection`: `String`
  - `details`: `Map<String, Object>`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-155` — GET `/api/v1/projects/{projectId}/analytics`

- **Method Name:** `getProjectAnalytics`
- **Summary / Purpose:** Get overall project analytics (velocity, throughput, cycle time)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectAnalyticsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectAnalyticsResponse>`):**
  - `projectId`: `Long`
  - `velocity`: `double`
  - `throughputTasksPerWeek`: `double`
  - `cycleTimeDays`: `double`
  - `leadTimeDays`: `double`
  - `sprintSuccessRate`: `double`
  - `estimateAccuracyPercentage`: `double`
  - `blockedTasksPercentage`: `double`
  - `overduePercentage`: `double`
  - `productivityStatus`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-156` — GET `/api/v1/projects/{projectId}/analytics/quality`

- **Method Name:** `getQualityAnalytics`
- **Summary / Purpose:** Get project quality, defect, and blocked task analytics
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<QualityAnalyticsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<QualityAnalyticsResponse>`):**
  - `projectId`: `Long`
  - `blockedTasksCount`: `long`
  - `reopenedTasksCount`: `long`
  - `overdueTasksCount`: `long`
  - `riskTrendStatus`: `String`
  - `defectDensity`: `double`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-157` — GET `/api/v1/projects/{projectId}/analytics/team`

- **Method Name:** `getTeamAnalytics`
- **Summary / Purpose:** Get team productivity and workload balance analytics
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TeamAnalyticsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TeamAnalyticsResponse>`):**
  - `projectId`: `Long`
  - `totalMembers`: `long`
  - `activeContributorsCount`: `long`
  - `averageTasksAssignedPerMember`: `double`
  - `workloadBalanceScore`: `double`
  - `teamHealth`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ApprovalController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.approval.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/approval/controller/ApprovalController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/approval/controller/ApprovalController.java)
- **Tag / Domain:** Approval Controller (REST endpoints for enterprise approval workflows and governance requests)
- **Endpoints Count:** 7

### `API-026` — POST `/api/v1/approvals`

- **Method Name:** `requestApproval`
- **Summary / Purpose:** Request an approval for a release, decision, budget, or requirement
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApprovalResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `RequestApprovalRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `entityType`: `String` [@NotBlank(message = "Entity type is required")]
    - `entityId`: `Long` [@NotNull(message = "Entity ID is required")]
    - `title`: `String` [@NotBlank(message = "Approval title is required"), @Size(min = 2, max = 150, message = "Approval title must be between 2 and 150 characters")]
    - `comments`: `String`
- **Response Model Fields (`ResponseEntity<ApprovalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-027` — GET `/api/v1/approvals/pending`

- **Method Name:** `getPendingApprovals`
- **Summary / Purpose:** Get all pending approvals
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ApprovalResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ApprovalResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-028` — GET `/api/v1/approvals/{approvalId}`

- **Method Name:** `getApproval`
- **Summary / Purpose:** Get approval details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApprovalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApprovalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-029` — PATCH `/api/v1/approvals/{approvalId}/approve`

- **Method Name:** `approve`
- **Summary / Purpose:** Approve a pending request
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApprovalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApprovalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-030` — POST `/api/v1/approvals/{approvalId}/cancel`

- **Method Name:** `cancel`
- **Summary / Purpose:** Cancel an approval request
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApprovalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApprovalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-031` — PATCH `/api/v1/approvals/{approvalId}/reject`

- **Method Name:** `reject`
- **Summary / Purpose:** Reject a pending request
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApprovalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApprovalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-158` — GET `/api/v1/projects/{projectId}/approvals`

- **Method Name:** `getProjectApprovals`
- **Summary / Purpose:** Get all approvals for a specific project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ApprovalResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ApprovalResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `status`: `ApprovalStatus`
  - `requestedById`: `Long`
  - `requestedAt`: `LocalDateTime`
  - `approvedById`: `Long`
  - `approvedAt`: `LocalDateTime`
  - `comments`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `AttachmentController`

- **Package:** `org.SprintForge.modules.workspace.attachment.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/attachment/controller/AttachmentController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/attachment/controller/AttachmentController.java)
- **Tag / Domain:** Attachment Controller (REST endpoints for managing task attachments)
- **Endpoints Count:** 8

### `API-032` — DELETE `/api/v1/attachments/{attachmentId}`

- **Method Name:** `deleteAttachment`
- **Summary / Purpose:** Delete an attachment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-033` — GET `/api/v1/attachments/{attachmentId}`

- **Method Name:** `getAttachmentMetadata`
- **Summary / Purpose:** Get metadata of an attachment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AttachmentResponse>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AttachmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-034` — PATCH `/api/v1/attachments/{attachmentId}`

- **Method Name:** `renameAttachment`
- **Summary / Purpose:** Rename an attachment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AttachmentResponse>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `RenameAttachmentRequest`
  - Fields:
    - `fileName`: `String` [@NotBlank(message = "File name cannot be blank"), @Size(min = 1, max = 255, message = "File name must be between 1 and 255 characters")]
- **Response Model Fields (`ResponseEntity<AttachmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-035` — PATCH `/api/v1/attachments/{attachmentId}/archive`

- **Method Name:** `archiveAttachment`
- **Summary / Purpose:** Archive an attachment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AttachmentResponse>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AttachmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-036` — GET `/api/v1/attachments/{attachmentId}/download`

- **Method Name:** `downloadAttachment`
- **Summary / Purpose:** Download attachment file content
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Resource>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-037` — PATCH `/api/v1/attachments/{attachmentId}/restore`

- **Method Name:** `restoreAttachment`
- **Summary / Purpose:** Restore an archived attachment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AttachmentResponse>`
- **Path Variables:** `attachmentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AttachmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-323` — GET `/api/v1/tasks/{taskId}/attachments`

- **Method Name:** `getTaskAttachments`
- **Summary / Purpose:** Get all attachments for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AttachmentResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AttachmentResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

### `API-324` — POST `/api/v1/tasks/{taskId}/attachments`

- **Method Name:** `uploadAttachment`
- **Summary / Purpose:** Upload an attachment for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AttachmentResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AttachmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `taskId`: `Long`
  - `uploadedBy`: `Long`
  - `fileName`: `String`
  - `originalFileName`: `String`
  - `contentType`: `String`
  - `size`: `Long`
  - `storageKey`: `String`
  - `downloadUrl`: `String`
  - `checksum`: `String`
  - `archived`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Detail -> Attachments Grid
- **UI Trigger / Interaction:** Drag and drop files to attach to task, preview images, download files
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AttachmentServiceImplTest)

---

## Controller: `AuditController`

- **Package:** `org.SprintForge.platform.audit.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/audit/controller/AuditController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/audit/controller/AuditController.java)
- **Tag / Domain:** AuditController (N/A)
- **Endpoints Count:** 5

### `API-502` — GET `/audit`

- **Method Name:** `getAllAudits`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AuditRecordResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AuditRecordResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `action`: `String`
  - `actorId`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `correlationId`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-503` — POST `/audit`

- **Method Name:** `createAuditRecord`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AuditRecordResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateAuditRequest`
  - Fields:
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `entityType`: `String`
    - `entityId`: `String`
    - `action`: `String`
    - `actorId`: `String`
    - `oldValue`: `String`
    - `newValue`: `String`
    - `ipAddress`: `String`
    - `userAgent`: `String`
    - `correlationId`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `entityType`: `String`
    - `entityId`: `String`
    - `action`: `String`
    - `actorId`: `String`
    - `oldValue`: `String`
    - `newValue`: `String`
    - `ipAddress`: `String`
    - `userAgent`: `String`
    - `correlationId`: `String`
- **Response Model Fields (`ResponseEntity<AuditRecordResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `action`: `String`
  - `actorId`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `correlationId`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-504` — GET `/audit/entity/{type}/{id}`

- **Method Name:** `getAuditByEntity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AuditRecordResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AuditRecordResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `action`: `String`
  - `actorId`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `correlationId`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-505` — GET `/audit/user/{id}`

- **Method Name:** `getAuditByUser`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AuditRecordResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AuditRecordResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `action`: `String`
  - `actorId`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `correlationId`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-506` — GET `/audit/workspace/{id}`

- **Method Name:** `getAuditByWorkspace`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AuditRecordResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AuditRecordResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `entityType`: `String`
  - `entityId`: `String`
  - `action`: `String`
  - `actorId`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `correlationId`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

## Controller: `AuthController`

- **Package:** `org.SprintForge.modules.auth.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/auth/controller/AuthController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/auth/controller/AuthController.java)
- **Tag / Domain:** AuthController (N/A)
- **Endpoints Count:** 8

### `API-038` — POST `/api/v1/auth/change-password`

- **Method Name:** `changePassword`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ChangePasswordRequest`
  - Fields:
    - `currentPassword`: `String` [@NotBlank(message = "Current password is required")]
    - `newPassword`: `String` [@NotBlank(message = "New password is required"), @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")]
- **Frontend Destination:** User Settings -> Security Tab (/settings/security)
- **UI Trigger / Interaction:** User inputs current and new password; updates credentials
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-039` — POST `/api/v1/auth/forgot-password`

- **Method Name:** `forgotPassword`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ForgotPasswordRequest`
  - Fields:
    - `email`: `String` [@NotBlank(message = "Email is required"), @Email(message = "Invalid email format")]
- **Frontend Destination:** Password Recovery Flow (/forgot-password, /reset-password)
- **UI Trigger / Interaction:** User requests reset email or submits new password with reset token
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-040` — POST `/api/v1/auth/login`

- **Method Name:** `login`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JwtResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `LoginRequest`
  - Fields:
    - `usernameOrEmail`: `String` [@NotBlank(message = "Username or email is required")]
    - `password`: `String` [@NotBlank(message = "Password is required")]
- **Response Model Fields (`ResponseEntity<JwtResponse>`):**
  - `token`: `String`
  - `refreshToken`: `String`
  - `username`: `String`
  - `email`: `String`
  - `role`: `String`
- **Frontend Destination:** Login Page (/login)
- **UI Trigger / Interaction:** User enters credentials; receives JWT and Refresh token
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-041` — POST `/api/v1/auth/logout`

- **Method Name:** `logout`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Top Navigation -> User Profile Menu
- **UI Trigger / Interaction:** User clicks 'Sign Out'; clears local tokens and invalidates session
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-042` — POST `/api/v1/auth/logout-all`

- **Method Name:** `logoutAll`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Top Navigation -> User Profile Menu
- **UI Trigger / Interaction:** User clicks 'Sign Out'; clears local tokens and invalidates session
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-043` — POST `/api/v1/auth/refresh`

- **Method Name:** `refresh`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JwtResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `RefreshRequest`
  - Fields:
    - `refreshToken`: `String` [@NotBlank(message = "Refresh token is required")]
- **Response Model Fields (`ResponseEntity<JwtResponse>`):**
  - `token`: `String`
  - `refreshToken`: `String`
  - `username`: `String`
  - `email`: `String`
  - `role`: `String`
- **Frontend Destination:** Silent Axios / Fetch Interceptor
- **UI Trigger / Interaction:** Triggered automatically on 401 response to renew expired JWT
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-044` — POST `/api/v1/auth/reset-password`

- **Method Name:** `resetPassword`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ResetPasswordRequest`
  - Fields:
    - `token`: `String` [@NotBlank(message = "Token is required")]
    - `newPassword`: `String` [@NotBlank(message = "New password is required"), @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")]
- **Frontend Destination:** Password Recovery Flow (/forgot-password, /reset-password)
- **UI Trigger / Interaction:** User requests reset email or submits new password with reset token
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

### `API-045` — POST `/api/v1/auth/signup`

- **Method Name:** `signup`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JwtResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `SignupRequest`
  - Fields:
    - `username`: `String` [@NotBlank(message = "Username is required"), @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")]
    - `email`: `String` [@NotBlank(message = "Email is required"), @Email(message = "Invalid email format"), @Size(max = 100)]
    - `password`: `String` [@NotBlank(message = "Password is required"), @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")]
- **Response Model Fields (`ResponseEntity<JwtResponse>`):**
  - `token`: `String`
  - `refreshToken`: `String`
  - `username`: `String`
  - `email`: `String`
  - `role`: `String`
- **Frontend Destination:** Signup Page (/signup)
- **UI Trigger / Interaction:** User submits registration form with username, email, password
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthControllerTest, AuthServiceSecurityTest)

---

## Controller: `BulkTaskController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/BulkTaskController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/BulkTaskController.java)
- **Tag / Domain:** Bulk Task Controller (REST endpoints for performing high-throughput batch operations on tasks)
- **Endpoints Count:** 10

### `API-276` — POST `/api/v1/tasks/bulk/archive`

- **Method Name:** `bulkArchive`
- **Summary / Purpose:** Bulk archive tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkArchiveRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-277` — POST `/api/v1/tasks/bulk/assign`

- **Method Name:** `bulkAssign`
- **Summary / Purpose:** Bulk assign tasks to a user or unassign
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkAssignRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `assigneeId`: `Long`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-278` — POST `/api/v1/tasks/bulk/custom-fields`

- **Method Name:** `bulkCustomField`
- **Summary / Purpose:** Bulk update custom field value on tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkCustomFieldRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `customFieldId`: `Long` [@NotNull(message = "Custom field ID is required")]
    - `fieldValue`: `String`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-279` — POST `/api/v1/tasks/bulk/delete`

- **Method Name:** `bulkDelete`
- **Summary / Purpose:** Bulk soft-delete tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkDeleteRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-280` — POST `/api/v1/tasks/bulk/labels`

- **Method Name:** `bulkLabels`
- **Summary / Purpose:** Bulk add or remove labels on tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkLabelRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `labelIdsToAdd`: `List<Long>`
    - `labelIdsToRemove`: `List<Long>`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-281` — POST `/api/v1/tasks/bulk/milestone`

- **Method Name:** `bulkMoveMilestone`
- **Summary / Purpose:** Bulk move tasks to a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkMoveMilestoneRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `milestoneId`: `Long`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-282` — POST `/api/v1/tasks/bulk/priority`

- **Method Name:** `bulkPriority`
- **Summary / Purpose:** Bulk update task priority
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkPriorityRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `priority`: `TaskPriority` [@NotNull(message = "Priority is required")]
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-283` — POST `/api/v1/tasks/bulk/restore`

- **Method Name:** `bulkRestore`
- **Summary / Purpose:** Bulk restore archived or deleted tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `List<Long>`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-284` — POST `/api/v1/tasks/bulk/sprint`

- **Method Name:** `bulkMoveSprint`
- **Summary / Purpose:** Bulk move tasks to a sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkMoveSprintRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `sprintId`: `Long`
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-285` — POST `/api/v1/tasks/bulk/status`

- **Method Name:** `bulkStatus`
- **Summary / Purpose:** Bulk update task status
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BulkOperationResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `BulkStatusRequest`
  - Fields:
    - `taskIds`: `List<Long>` [@NotEmpty(message = "Task IDs are required")]
    - `status`: `TaskStatus` [@NotNull(message = "Status is required")]
- **Response Model Fields (`ResponseEntity<BulkOperationResponse>`):**
  - `operationType`: `String`
  - `totalRequested`: `int`
  - `successCount`: `int`
  - `failureCount`: `int`
  - `successfulTaskIds`: `List<Long>`
  - `failures`: `List<BulkFailure>`
  - `taskId`: `Long`
  - `reason`: `String`
- **Frontend Destination:** Task List Multi-Select Action Bar
- **UI Trigger / Interaction:** User selects multiple rows; bulk changes status, assignee, priority, or moves
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `BusinessCalendarController`

- **Package:** `org.SprintForge.modules.workspace.workspace.calendar.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/calendar/controller/BusinessCalendarController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/calendar/controller/BusinessCalendarController.java)
- **Tag / Domain:** Business Calendar (N/A)
- **Endpoints Count:** 5

### `API-445` — GET `/api/v1/workspaces/{workspaceId}/calendar`

- **Method Name:** `getCalendar`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<BusinessCalendarResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `year` (int, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<BusinessCalendarResponse>`):**
  - `workspaceId`: `Long`
  - `timezone`: `String`
  - `workingDays`: `List<String>`
  - `businessHoursStart`: `String`
  - `businessHoursEnd`: `String`
  - `weekStartsOn`: `String`
  - `holidays`: `List<HolidayResponse>`
  - `year`: `int`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-446` — GET `/api/v1/workspaces/{workspaceId}/calendar/holidays`

- **Method Name:** `getHolidays`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<HolidayResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `year` (int, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<HolidayResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `date`: `LocalDate`
  - `isRecurring`: `Boolean`
  - `type`: `HolidayType`
  - `description`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-447` — POST `/api/v1/workspaces/{workspaceId}/calendar/holidays`

- **Method Name:** `addHoliday`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<HolidayResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateHolidayRequest`
  - Fields:
    - `name`: `String` [@NotBlank]
    - `date`: `LocalDate` [@NotNull]
    - `isRecurring`: `Boolean`
    - `type`: `HolidayType`
    - `description`: `String`
- **Response Model Fields (`ResponseEntity<HolidayResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `date`: `LocalDate`
  - `isRecurring`: `Boolean`
  - `type`: `HolidayType`
  - `description`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-448` — DELETE `/api/v1/workspaces/{workspaceId}/calendar/holidays/{holidayId}`

- **Method Name:** `removeHoliday`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `holidayId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-449` — GET `/api/v1/workspaces/{workspaceId}/calendar/working-days`

- **Method Name:** `getWorkingDays`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<LocalDate>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `CacheController`

- **Package:** `org.SprintForge.platform.cache.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/cache/controller/CacheController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/cache/controller/CacheController.java)
- **Tag / Domain:** CacheController (N/A)
- **Endpoints Count:** 3

### `API-507` — POST `/cache/clear`

- **Method Name:** `clearCache`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<String>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-508` — GET `/cache/statistics`

- **Method Name:** `getStatistics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CacheStatisticsResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CacheStatisticsResponse>`):**
  - `totalEntries`: `long`
  - `cacheHits`: `long`
  - `cacheMisses`: `long`
  - `hitRatio`: `double`
  - `totalEntries`: `long`
  - `cacheHits`: `long`
  - `cacheMisses`: `long`
  - `hitRatio`: `double`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-509` — POST `/cache/warm`

- **Method Name:** `warmCache`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<String>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

## Controller: `CalendarIntegrationController`

- **Package:** `org.SprintForge.modules.workspace.calendar.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/calendar/controller/CalendarIntegrationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/calendar/controller/CalendarIntegrationController.java)
- **Tag / Domain:** Calendar Controller (REST endpoints for calendar views (Day, Week, Month, Agenda))
- **Endpoints Count:** 4

### `API-046` — GET `/api/v1/calendar`

- **Method Name:** `getCalendar`
- **Summary / Purpose:** Get calendar events by date range
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CalendarViewResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CalendarViewResponse>`):**
  - `viewMode`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `totalEvents`: `int`
  - `events`: `List<CalendarEventResponse>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-047` — GET `/api/v1/calendar/day`

- **Method Name:** `getDayCalendar`
- **Summary / Purpose:** Get day calendar view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CalendarViewResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CalendarViewResponse>`):**
  - `viewMode`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `totalEvents`: `int`
  - `events`: `List<CalendarEventResponse>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-048` — GET `/api/v1/calendar/month`

- **Method Name:** `getMonthCalendar`
- **Summary / Purpose:** Get month calendar view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CalendarViewResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CalendarViewResponse>`):**
  - `viewMode`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `totalEvents`: `int`
  - `events`: `List<CalendarEventResponse>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-049` — GET `/api/v1/calendar/week`

- **Method Name:** `getWeekCalendar`
- **Summary / Purpose:** Get week calendar view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CalendarViewResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CalendarViewResponse>`):**
  - `viewMode`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `totalEvents`: `int`
  - `events`: `List<CalendarEventResponse>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ChangeController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.change.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/change/controller/ChangeController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/change/controller/ChangeController.java)
- **Tag / Domain:** Change Controller (REST endpoints for managing project change requests and impact evaluations)
- **Endpoints Count:** 5

### `API-050` — DELETE `/api/v1/changes/{changeId}`

- **Method Name:** `deleteChange`
- **Summary / Purpose:** Delete / archive a change request
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-051` — GET `/api/v1/changes/{changeId}`

- **Method Name:** `getChange`
- **Summary / Purpose:** Get change request details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChangeResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ChangeResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `changeType`: `ChangeType`
  - `title`: `String`
  - `description`: `String`
  - `reason`: `String`
  - `impact`: `String`
  - `requestedById`: `Long`
  - `approvedById`: `Long`
  - `implementedById`: `Long`
  - `status`: `ChangeStatus`
  - `completedAt`: `LocalDateTime`
  - *...and 3 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-052` — PATCH `/api/v1/changes/{changeId}`

- **Method Name:** `updateChangeRequest`
- **Summary / Purpose:** Update change request status or details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChangeResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ChangeResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `changeType`: `ChangeType`
  - `title`: `String`
  - `description`: `String`
  - `reason`: `String`
  - `impact`: `String`
  - `requestedById`: `Long`
  - `approvedById`: `Long`
  - `implementedById`: `Long`
  - `status`: `ChangeStatus`
  - `completedAt`: `LocalDateTime`
  - *...and 3 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-161` — GET `/api/v1/projects/{projectId}/changes`

- **Method Name:** `getProjectChanges`
- **Summary / Purpose:** Get all change requests for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ChangeResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ChangeResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `changeType`: `ChangeType`
  - `title`: `String`
  - `description`: `String`
  - `reason`: `String`
  - `impact`: `String`
  - `requestedById`: `Long`
  - `approvedById`: `Long`
  - `implementedById`: `Long`
  - `status`: `ChangeStatus`
  - `completedAt`: `LocalDateTime`
  - *...and 3 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-162` — POST `/api/v1/projects/{projectId}/changes`

- **Method Name:** `createChangeRequest`
- **Summary / Purpose:** Create a project change request
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChangeResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ChangeResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `changeType`: `ChangeType`
  - `title`: `String`
  - `description`: `String`
  - `reason`: `String`
  - `impact`: `String`
  - `requestedById`: `Long`
  - `approvedById`: `Long`
  - `implementedById`: `Long`
  - `status`: `ChangeStatus`
  - `completedAt`: `LocalDateTime`
  - *...and 3 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ChecklistController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/ChecklistController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/ChecklistController.java)
- **Tag / Domain:** Checklist Controller (REST endpoints for managing task checklists and items)
- **Endpoints Count:** 9

### `API-053` — PATCH `/api/v1/checklist-items/reorder`

- **Method Name:** `reorderItems`
- **Summary / Purpose:** Reorder items in a checklist
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `List<MoveChecklistItemRequest>`
  - Fields:
    - `itemId`: `Long` [@NotNull(message = "Item ID is required")]
    - `position`: `Integer` [@NotNull(message = "Position is required")]
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-054` — DELETE `/api/v1/checklist-items/{id}`

- **Method Name:** `deleteItem`
- **Summary / Purpose:** Delete a checklist item
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-055` — PATCH `/api/v1/checklist-items/{id}`

- **Method Name:** `updateItem`
- **Summary / Purpose:** Update details of a checklist item
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChecklistItemResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateChecklistItemRequest`
  - Fields:
    - `title`: `String` [@Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")]
    - `description`: `String` [@Size(max = 2000, message = "Description must not exceed 2000 characters")]
- **Response Model Fields (`ResponseEntity<ChecklistItemResponse>`):**
  - `id`: `Long`
  - `checklistId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `completed`: `Boolean`
  - `completedBy`: `Long`
  - `completedAt`: `LocalDateTime`
  - `position`: `Integer`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-056` — PATCH `/api/v1/checklist-items/{id}/complete`

- **Method Name:** `completeItem`
- **Summary / Purpose:** Complete or uncomplete a checklist item
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChecklistItemResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `completed` (Boolean, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ChecklistItemResponse>`):**
  - `id`: `Long`
  - `checklistId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `completed`: `Boolean`
  - `completedBy`: `Long`
  - `completedAt`: `LocalDateTime`
  - `position`: `Integer`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-057` — DELETE `/api/v1/checklists/{id}`

- **Method Name:** `deleteChecklist`
- **Summary / Purpose:** Delete a checklist
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-058` — PATCH `/api/v1/checklists/{id}`

- **Method Name:** `updateChecklist`
- **Summary / Purpose:** Update a checklist title
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChecklistResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateChecklistRequest`
  - Fields:
    - `title`: `String` [@NotBlank(message = "Title is required"), @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")]
- **Response Model Fields (`ResponseEntity<ChecklistResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `title`: `String`
  - `position`: `Integer`
  - `items`: `List<ChecklistItemResponse>`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-059` — POST `/api/v1/checklists/{id}/items`

- **Method Name:** `addItem`
- **Summary / Purpose:** Add an item to a checklist
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChecklistItemResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateChecklistItemRequest`
  - Fields:
    - `title`: `String` [@NotBlank(message = "Title is required"), @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")]
    - `description`: `String` [@Size(max = 2000, message = "Description must not exceed 2000 characters")]
- **Response Model Fields (`ResponseEntity<ChecklistItemResponse>`):**
  - `id`: `Long`
  - `checklistId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `completed`: `Boolean`
  - `completedBy`: `Long`
  - `completedAt`: `LocalDateTime`
  - `position`: `Integer`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-327` — GET `/api/v1/tasks/{taskId}/checklists`

- **Method Name:** `getTaskChecklists`
- **Summary / Purpose:** Get all checklists for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ChecklistResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ChecklistResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `title`: `String`
  - `position`: `Integer`
  - `items`: `List<ChecklistItemResponse>`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

### `API-328` — POST `/api/v1/tasks/{taskId}/checklists`

- **Method Name:** `createChecklist`
- **Summary / Purpose:** Create a checklist for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ChecklistResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateChecklistRequest`
  - Fields:
    - `title`: `String` [@NotBlank(message = "Title is required"), @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")]
- **Response Model Fields (`ResponseEntity<ChecklistResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `title`: `String`
  - `position`: `Integer`
  - `items`: `List<ChecklistItemResponse>`
  - `createdBy`: `String`
  - `updatedBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Subtask Checklist
- **UI Trigger / Interaction:** Check off acceptance criteria items, reorder items, add checklist items
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ChecklistServiceImplTest)

---

## Controller: `CommentController`

- **Package:** `org.SprintForge.modules.workspace.comment.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/comment/controller/CommentController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/comment/controller/CommentController.java)
- **Tag / Domain:** Comment Controller (REST endpoints for managing task comments, replies, and reactions)
- **Endpoints Count:** 8

### `API-060` — DELETE `/api/v1/comments/{commentId}`

- **Method Name:** `deleteComment`
- **Summary / Purpose:** Delete a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `commentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-061` — PATCH `/api/v1/comments/{commentId}`

- **Method Name:** `updateComment`
- **Summary / Purpose:** Update a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CommentResponse>`
- **Path Variables:** `commentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateCommentRequest`
  - Fields:
    - `content`: `String` [@NotBlank(message = "Comment content cannot be blank"), @Size(min = 1, max = 10000, message = "Comment content must be between 1 and 10000 characters")]
- **Response Model Fields (`ResponseEntity<CommentResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `parentCommentId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `content`: `String`
  - `edited`: `Boolean`
  - `editedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
  - `replies`: `List<CommentResponse>`
  - `reactions`: `List<ReactionResponse>`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-062` — POST `/api/v1/comments/{commentId}/reactions`

- **Method Name:** `addReaction`
- **Summary / Purpose:** Add a reaction to a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReactionResponse>`
- **Path Variables:** `commentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ReactCommentRequest`
  - Fields:
    - `emoji`: `String` [@NotBlank(message = "Reaction emoji cannot be blank"), @Size(min = 1, max = 100, message = "Reaction emoji must be between 1 and 100 characters")]
- **Response Model Fields (`ResponseEntity<ReactionResponse>`):**
  - `emoji`: `String`
  - `userId`: `Long`
  - `username`: `String`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-063` — DELETE `/api/v1/comments/{commentId}/reactions/{reaction}`

- **Method Name:** `removeReaction`
- **Summary / Purpose:** Remove a reaction from a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `commentId` (Long), `reaction` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-064` — GET `/api/v1/comments/{commentId}/replies`

- **Method Name:** `getReplies`
- **Summary / Purpose:** Get replies to a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<CommentResponse>>`
- **Path Variables:** `commentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<CommentResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `parentCommentId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `content`: `String`
  - `edited`: `Boolean`
  - `editedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
  - `replies`: `List<CommentResponse>`
  - `reactions`: `List<ReactionResponse>`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-065` — POST `/api/v1/comments/{commentId}/reply`

- **Method Name:** `reply`
- **Summary / Purpose:** Reply to a comment
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CommentResponse>`
- **Path Variables:** `commentId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ReplyCommentRequest`
  - Fields:
    - `content`: `String` [@NotBlank(message = "Comment content cannot be blank"), @Size(min = 1, max = 10000, message = "Comment content must be between 1 and 10000 characters")]
- **Response Model Fields (`ResponseEntity<CommentResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `parentCommentId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `content`: `String`
  - `edited`: `Boolean`
  - `editedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
  - `replies`: `List<CommentResponse>`
  - `reactions`: `List<ReactionResponse>`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-329` — GET `/api/v1/tasks/{taskId}/comments`

- **Method Name:** `getTaskComments`
- **Summary / Purpose:** Get all comments for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<CommentResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<CommentResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `parentCommentId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `content`: `String`
  - `edited`: `Boolean`
  - `editedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
  - `replies`: `List<CommentResponse>`
  - `reactions`: `List<ReactionResponse>`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

### `API-330` — POST `/api/v1/tasks/{taskId}/comments`

- **Method Name:** `createComment`
- **Summary / Purpose:** Create a comment on a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CommentResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateCommentRequest`
  - Fields:
    - `content`: `String` [@NotBlank(message = "Comment content cannot be blank"), @Size(min = 1, max = 10000, message = "Comment content must be between 1 and 10000 characters")]
- **Response Model Fields (`ResponseEntity<CommentResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `parentCommentId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `content`: `String`
  - `edited`: `Boolean`
  - `editedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
  - `replies`: `List<CommentResponse>`
  - `reactions`: `List<ReactionResponse>`
- **Frontend Destination:** Task Detail -> Discussion Thread
- **UI Trigger / Interaction:** Post markdown comments, mention team members with @, add emoji reactions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommentServiceImplTest)

---

## Controller: `CommunicationDashboardController`

- **Package:** `org.SprintForge.platform.communication.dashboard`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/dashboard/CommunicationDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/dashboard/CommunicationDashboardController.java)
- **Tag / Domain:** CommunicationDashboardController (N/A)
- **Endpoints Count:** 2

### `API-575` — GET `/platform/communication/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<CommunicationDashboardResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<CommunicationDashboardResponse>>`):**
  - `totalNotificationsSent`: `long`
  - `totalUnreadNotifications`: `long`
  - `totalEmailsSent`: `long`
  - `webhookSuccessRate`: `double`
  - `averageDeliveryTimeMs`: `double`
  - `totalFailures`: `long`
  - `mostActiveUser`: `String`
  - `mostActiveProject`: `String`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-576` — GET `/platform/communication/metrics`

- **Method Name:** `getMetrics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<CommunicationDashboardResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<CommunicationDashboardResponse>>`):**
  - `totalNotificationsSent`: `long`
  - `totalUnreadNotifications`: `long`
  - `totalEmailsSent`: `long`
  - `webhookSuccessRate`: `double`
  - `averageDeliveryTimeMs`: `double`
  - `totalFailures`: `long`
  - `mostActiveUser`: `String`
  - `mostActiveProject`: `String`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ConnectorPackageController`

- **Package:** `org.SprintForge.modules.integration.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/integration/controller/ConnectorPackageController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/integration/controller/ConnectorPackageController.java)
- **Tag / Domain:** ConnectorPackageController (N/A)
- **Endpoints Count:** 5

### `API-537` — GET `/integrations/packages`

- **Method Name:** `getPackages`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<String>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConnectorPackageTest, ConnectorSDKTest, OperationRegistryTest)

---

### `API-538` — GET `/integrations/packages/{packageKey}`

- **Method Name:** `getPackageDetails`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ConnectorDescriptor>`
- **Path Variables:** `packageKey` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ConnectorDescriptor>`):**
  - `connectorKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `version`: `String`
  - `description`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConnectorPackageTest, ConnectorSDKTest, OperationRegistryTest)

---

### `API-539` — GET `/integrations/packages/{packageKey}/health`

- **Method Name:** `getPackageHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Boolean>>`
- **Path Variables:** `packageKey` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConnectorPackageTest, ConnectorSDKTest, OperationRegistryTest)

---

### `API-540` — GET `/integrations/packages/{packageKey}/operations`

- **Method Name:** `getPackageOperations`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<OperationDescriptor>>`
- **Path Variables:** `packageKey` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<OperationDescriptor>>`):**
  - `operationKey`: `String`
  - `displayName`: `String`
  - `description`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConnectorPackageTest, ConnectorSDKTest, OperationRegistryTest)

---

### `API-543` — POST `/integrations/test`

- **Method Name:** `testIntegration`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<OperationResult>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `TestIntegrationRequest`
- **Response Model Fields (`ResponseEntity<OperationResult>`):**
  - `status`: `String`
  - `statusCode`: `int`
  - `errorMessage`: `String`
  - `durationMs`: `long`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConnectorPackageTest, ConnectorSDKTest, OperationRegistryTest)

---

## Controller: `CustomFieldController`

- **Package:** `org.SprintForge.modules.workspace.customfield.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/customfield/controller/CustomFieldController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/customfield/controller/CustomFieldController.java)
- **Tag / Domain:** Custom Field Controller (REST endpoints for managing project-specific custom fields and task custom field values)
- **Endpoints Count:** 6

### `API-066` — DELETE `/api/v1/custom-fields/{id}`

- **Method Name:** `deleteField`
- **Summary / Purpose:** Delete a custom field definition
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

### `API-067` — PATCH `/api/v1/custom-fields/{id}`

- **Method Name:** `updateField`
- **Summary / Purpose:** Update a custom field definition
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CustomFieldResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CustomFieldResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `fieldType`: `CustomFieldType`
  - `options`: `String`
  - `required`: `Boolean`
  - `defaultValue`: `String`
  - `position`: `Integer`
  - `archived`: `Boolean`
  - `validationRules`: `String`
  - `createdBy`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

### `API-164` — GET `/api/v1/projects/{projectId}/custom-fields`

- **Method Name:** `getProjectFields`
- **Summary / Purpose:** Get all custom field definitions in a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<CustomFieldResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<CustomFieldResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `fieldType`: `CustomFieldType`
  - `options`: `String`
  - `required`: `Boolean`
  - `defaultValue`: `String`
  - `position`: `Integer`
  - `archived`: `Boolean`
  - `validationRules`: `String`
  - `createdBy`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

### `API-165` — POST `/api/v1/projects/{projectId}/custom-fields`

- **Method Name:** `createField`
- **Summary / Purpose:** Create a custom field definition for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CustomFieldResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CustomFieldResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `fieldType`: `CustomFieldType`
  - `options`: `String`
  - `required`: `Boolean`
  - `defaultValue`: `String`
  - `position`: `Integer`
  - `archived`: `Boolean`
  - `validationRules`: `String`
  - `createdBy`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

### `API-332` — GET `/api/v1/tasks/{taskId}/custom-fields`

- **Method Name:** `getTaskFields`
- **Summary / Purpose:** Get all custom field values for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskCustomFieldResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskCustomFieldResponse>>`):**
  - `fieldId`: `Long`
  - `fieldName`: `String`
  - `fieldType`: `CustomFieldType`
  - `required`: `Boolean`
  - `defaultValue`: `String`
  - `options`: `String`
  - `validationRules`: `String`
  - `valueId`: `Long`
  - `value`: `String`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

### `API-333` — PATCH `/api/v1/tasks/{taskId}/custom-fields`

- **Method Name:** `assignValue`
- **Summary / Purpose:** Assign or update custom field value on a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskCustomFieldResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskCustomFieldResponse>`):**
  - `fieldId`: `Long`
  - `fieldName`: `String`
  - `fieldType`: `CustomFieldType`
  - `required`: `Boolean`
  - `defaultValue`: `String`
  - `options`: `String`
  - `validationRules`: `String`
  - `valueId`: `Long`
  - `value`: `String`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CustomFieldManagementServiceTest, TaskCustomFieldServiceTest)

---

## Controller: `DashboardWidgetController`

- **Package:** `org.SprintForge.modules.workspace.analytics.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/analytics/controller/DashboardWidgetController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/analytics/controller/DashboardWidgetController.java)
- **Tag / Domain:** Dashboard Widget Controller (REST endpoints for configurable dashboard widgets and real-time data feeds)
- **Endpoints Count:** 4

### `API-404` — POST `/api/v1/widgets`

- **Method Name:** `createWidget`
- **Summary / Purpose:** Create dashboard widget
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DashboardWidget>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `DashboardWidget`
  - Fields:
    - `dashboardId`: `Long`
    - `widgetType`: `String`
    - `title`: `String`
    - `configuration`: `String`
- **Response Model Fields (`ResponseEntity<DashboardWidget>`):**
  - `dashboardId`: `Long`
  - `widgetType`: `String`
  - `title`: `String`
  - `configuration`: `String`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-405` — DELETE `/api/v1/widgets/{id}`

- **Method Name:** `deleteWidget`
- **Summary / Purpose:** Delete dashboard widget
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-406` — PATCH `/api/v1/widgets/{id}`

- **Method Name:** `updateWidget`
- **Summary / Purpose:** Update dashboard widget configuration
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DashboardWidget>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `DashboardWidget`
  - Fields:
    - `dashboardId`: `Long`
    - `widgetType`: `String`
    - `title`: `String`
    - `configuration`: `String`
- **Response Model Fields (`ResponseEntity<DashboardWidget>`):**
  - `dashboardId`: `Long`
  - `widgetType`: `String`
  - `title`: `String`
  - `configuration`: `String`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-407` — GET `/api/v1/widgets/{id}/data`

- **Method Name:** `getWidgetData`
- **Summary / Purpose:** Get widget data payload
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `DecisionController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.decision.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/decision/controller/DecisionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/decision/controller/DecisionController.java)
- **Tag / Domain:** Decision Controller (REST endpoints for managing architectural and business project decisions)
- **Endpoints Count:** 7

### `API-068` — DELETE `/api/v1/decisions/{decisionId}`

- **Method Name:** `deleteDecision`
- **Summary / Purpose:** Delete / archive decision record
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-069` — GET `/api/v1/decisions/{decisionId}`

- **Method Name:** `getDecision`
- **Summary / Purpose:** Get decision details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DecisionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DecisionResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-070` — PATCH `/api/v1/decisions/{decisionId}`

- **Method Name:** `updateDecision`
- **Summary / Purpose:** Update decision record
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DecisionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DecisionResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-071` — POST `/api/v1/decisions/{decisionId}/approve`

- **Method Name:** `approveDecision`
- **Summary / Purpose:** Approve a decision
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DecisionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DecisionResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-072` — POST `/api/v1/decisions/{decisionId}/supersede`

- **Method Name:** `supersedeDecision`
- **Summary / Purpose:** Supersede a decision with a newer record
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DecisionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DecisionResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-167` — GET `/api/v1/projects/{projectId}/decisions`

- **Method Name:** `getDecisions`
- **Summary / Purpose:** Get all decisions for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<DecisionResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<DecisionResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-168` — POST `/api/v1/projects/{projectId}/decisions`

- **Method Name:** `createDecision`
- **Summary / Purpose:** Create a project decision record
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DecisionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DecisionResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `problemStatement`: `String`
  - `decision`: `String`
  - `alternatives`: `String`
  - `reasoning`: `String`
  - `impact`: `String`
  - `status`: `DecisionStatus`
  - `decisionDate`: `LocalDate`
  - `ownerId`: `Long`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `DepartmentController`

- **Package:** `org.SprintForge.modules.workspace.workspace.department.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/department/controller/DepartmentController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/department/controller/DepartmentController.java)
- **Tag / Domain:** Department Management (Endpoints for managing departments)
- **Endpoints Count:** 8

### `API-073` — GET `/api/v1/departments/{id}`

- **Method Name:** `getDepartment`
- **Summary / Purpose:** Get department by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DepartmentResponse`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`DepartmentResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `headMemberId`: `Long`
  - `color`: `String`
  - `isArchived`: `boolean`
  - `teamCount`: `Long`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-074` — PATCH `/api/v1/departments/{id}`

- **Method Name:** `updateDepartment`
- **Summary / Purpose:** Update department
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DepartmentResponse`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateDepartmentRequest`
  - Fields:
    - `name`: `String`
    - `description`: `String`
    - `color`: `String`
    - `headMemberId`: `Long`
- **Response Model Fields (`DepartmentResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `headMemberId`: `Long`
  - `color`: `String`
  - `isArchived`: `boolean`
  - `teamCount`: `Long`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-075` — POST `/api/v1/departments/{id}/archive`

- **Method Name:** `archiveDepartment`
- **Summary / Purpose:** Archive department
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-076` — POST `/api/v1/departments/{id}/head`

- **Method Name:** `assignHead`
- **Summary / Purpose:** Assign department head
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DepartmentResponse`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `headMemberId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`DepartmentResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `headMemberId`: `Long`
  - `color`: `String`
  - `isArchived`: `boolean`
  - `teamCount`: `Long`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-077` — POST `/api/v1/departments/{id}/restore`

- **Method Name:** `restoreDepartment`
- **Summary / Purpose:** Restore department
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-078` — POST `/api/v1/departments/{id}/teams/{teamId}`

- **Method Name:** `moveTeamToDepartment`
- **Summary / Purpose:** Move team to department
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long), `teamId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-450` — GET `/api/v1/workspaces/{workspaceId}/departments`

- **Method Name:** `listByWorkspace`
- **Summary / Purpose:** List departments by workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<DepartmentResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<DepartmentResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `headMemberId`: `Long`
  - `color`: `String`
  - `isArchived`: `boolean`
  - `teamCount`: `Long`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-451` — POST `/api/v1/workspaces/{workspaceId}/departments`

- **Method Name:** `createDepartment`
- **Summary / Purpose:** Create a department
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DepartmentResponse`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateDepartmentRequest`
  - Fields:
    - `name`: `String` [@NotBlank(message = "Name is required")]
    - `description`: `String`
    - `color`: `String`
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `headMemberId`: `Long`
- **Response Model Fields (`DepartmentResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `headMemberId`: `Long`
  - `color`: `String`
  - `isArchived`: `boolean`
  - `teamCount`: `Long`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `DeveloperUtilityController`

- **Package:** `org.SprintForge.platform.dev.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/dev/controller/DeveloperUtilityController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/dev/controller/DeveloperUtilityController.java)
- **Tag / Domain:** DeveloperUtilityController (N/A)
- **Endpoints Count:** 5

### `API-510` — POST `/developer/cache/clear`

- **Method Name:** `clearCache`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Developer Console / API Sandbox (/dev/tools)
- **UI Trigger / Interaction:** Developer executes test data seeding or sandbox utility operations
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-511` — POST `/developer/events/replay`

- **Method Name:** `replayEvents`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Developer Console / API Sandbox (/dev/tools)
- **UI Trigger / Interaction:** Developer executes test data seeding or sandbox utility operations
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-512` — POST `/developer/jobs/retry`

- **Method Name:** `replayFailedJobs`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Developer Console / API Sandbox (/dev/tools)
- **UI Trigger / Interaction:** Developer executes test data seeding or sandbox utility operations
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-513` — POST `/developer/reindex`

- **Method Name:** `reindexSearch`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Developer Console / API Sandbox (/dev/tools)
- **UI Trigger / Interaction:** Developer executes test data seeding or sandbox utility operations
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-514` — POST `/developer/seed`

- **Method Name:** `seedDemoWorkspace`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Developer Console / API Sandbox (/dev/tools)
- **UI Trigger / Interaction:** Developer executes test data seeding or sandbox utility operations
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `DirectoryController`

- **Package:** `org.SprintForge.modules.workspace.workspace.directory.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/directory/controller/DirectoryController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/directory/controller/DirectoryController.java)
- **Tag / Domain:** Organization Directory (Endpoints for viewing workspace members)
- **Endpoints Count:** 3

### `API-452` — GET `/api/v1/workspaces/{workspaceId}/directory`

- **Method Name:** `getDirectory`
- **Summary / Purpose:** Get workspace directory
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DirectoryResponse`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`DirectoryResponse`):**
  - `workspaceId`: `Long`
  - `totalMembers`: `long`
  - `members`: `List<DirectoryMemberResponse>`
  - `page`: `int`
  - `size`: `int`
  - `totalPages`: `int`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-453` — GET `/api/v1/workspaces/{workspaceId}/directory/search`

- **Method Name:** `searchDirectory`
- **Summary / Purpose:** Search workspace directory
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `DirectoryResponse`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`DirectoryResponse`):**
  - `workspaceId`: `Long`
  - `totalMembers`: `long`
  - `members`: `List<DirectoryMemberResponse>`
  - `page`: `int`
  - `size`: `int`
  - `totalPages`: `int`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-454` — GET `/api/v1/workspaces/{workspaceId}/directory/teams/{teamId}`

- **Method Name:** `getTeamDirectory`
- **Summary / Purpose:** Get team directory
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<DirectoryMemberResponse>`
- **Path Variables:** `workspaceId` (Long), `teamId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<DirectoryMemberResponse>`):**
  - `userId`: `Long`
  - `userName`: `String`
  - `email`: `String`
  - `avatarUrl`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `teamIds`: `List<Long>`
  - `teamNames`: `List<String>`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `status`: `String`
  - `joinedAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `DocumentController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.document.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/document/controller/DocumentController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/document/controller/DocumentController.java)
- **Tag / Domain:** Document Controller (REST endpoints for managing project documentation, folders, and markdown version history)
- **Endpoints Count:** 11

### `API-079` — DELETE `/api/v1/documents/{documentId}`

- **Method Name:** `deleteDocument`
- **Summary / Purpose:** Delete / archive document
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-080` — GET `/api/v1/documents/{documentId}`

- **Method Name:** `getDocument`
- **Summary / Purpose:** Get document by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-081` — PATCH `/api/v1/documents/{documentId}`

- **Method Name:** `updateDocument`
- **Summary / Purpose:** Update document content or metadata
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-082` — POST `/api/v1/documents/{documentId}/favorite`

- **Method Name:** `toggleFavorite`
- **Summary / Purpose:** Toggle favorite status of a document
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-083` — POST `/api/v1/documents/{documentId}/pin`

- **Method Name:** `togglePin`
- **Summary / Purpose:** Toggle pin status of a document
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-084` — POST `/api/v1/documents/{documentId}/restore`

- **Method Name:** `restoreVersion`
- **Summary / Purpose:** Restore a previous version of a document
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-085` — GET `/api/v1/documents/{documentId}/versions`

- **Method Name:** `getDocumentVersions`
- **Summary / Purpose:** Get document version history
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<DocumentVersionResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<DocumentVersionResponse>>`):**
  - `id`: `Long`
  - `documentId`: `Long`
  - `versionNumber`: `Integer`
  - `content`: `String`
  - `changeSummary`: `String`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-169` — GET `/api/v1/projects/{projectId}/document-folders`

- **Method Name:** `getFolders`
- **Summary / Purpose:** Get all document folders for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<DocumentFolder>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<DocumentFolder>>`):**
  - `projectId`: `Long`
  - `name`: `String`
  - `parentId`: `Long`
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-170` — POST `/api/v1/projects/{projectId}/document-folders`

- **Method Name:** `createFolder`
- **Summary / Purpose:** Create a document folder
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentFolder>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentFolder>`):**
  - `projectId`: `Long`
  - `name`: `String`
  - `parentId`: `Long`
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-171` — GET `/api/v1/projects/{projectId}/documents`

- **Method Name:** `getDocuments`
- **Summary / Purpose:** Get all documents for a project (optional folder filter)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<DocumentResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<DocumentResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-172` — POST `/api/v1/projects/{projectId}/documents`

- **Method Name:** `createDocument`
- **Summary / Purpose:** Create a project document
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DocumentResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DocumentResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `folderId`: `Long`
  - `title`: `String`
  - `slug`: `String`
  - `content`: `String`
  - `format`: `DocumentFormat`
  - `versionNumber`: `Integer`
  - `authorId`: `Long`
  - `lastEditorId`: `Long`
  - `isPinned`: `Boolean`
  - `isFavorite`: `Boolean`
  - *...and 4 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `EmailController`

- **Package:** `org.SprintForge.platform.communication.email`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/email/EmailController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/email/EmailController.java)
- **Tag / Domain:** EmailController (N/A)
- **Endpoints Count:** 3

### `API-515` — POST `/emails/send`

- **Method Name:** `sendEmail`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<EmailQueue>>`
- **Path Variables:** None
- **Query Parameters:** `recipient` (String, required=True, default=None), `templateName` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<ApiResponse<EmailQueue>>`):**
  - `id`: `Long`
  - `recipient`: `String`
  - `templateName`: `String`
  - `payload`: `String`
  - `status`: `String`
  - `attempts`: `int`
  - `scheduledAt`: `LocalDateTime`
  - `sentAt`: `LocalDateTime`
  - `error`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (EmailQueueReliabilityTest, CommunicationPlatformIntegrationTest)

---

### `API-516` — GET `/emails/templates`

- **Method Name:** `getTemplates`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<EmailTemplate>>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<EmailTemplate>>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `subject`: `String`
  - `body`: `String`
  - `variables`: `String`
  - `active`: `boolean`
  - `version`: `int`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (EmailQueueReliabilityTest, CommunicationPlatformIntegrationTest)

---

### `API-517` — PATCH `/emails/templates/{id}`

- **Method Name:** `updateTemplate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<EmailTemplate>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `EmailTemplate`
  - Fields:
    - `id`: `Long`
    - `name`: `String`
    - `subject`: `String`
    - `body`: `String`
    - `variables`: `String`
    - `active`: `boolean`
    - `version`: `int`
    - `createdAt`: `LocalDateTime`
- **Response Model Fields (`ResponseEntity<ApiResponse<EmailTemplate>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `subject`: `String`
  - `body`: `String`
  - `variables`: `String`
  - `active`: `boolean`
  - `version`: `int`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (EmailQueueReliabilityTest, CommunicationPlatformIntegrationTest)

---

## Controller: `EpicController`

- **Package:** `org.SprintForge.modules.workspace.epic.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/epic/controller/EpicController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/epic/controller/EpicController.java)
- **Tag / Domain:** Epic Controller (REST endpoints for managing epics and tracking progress)
- **Endpoints Count:** 3

### `API-086` — POST `/api/v1/epics/{id}/merge`

- **Method Name:** `mergeEpics`
- **Summary / Purpose:** Merge an epic into another epic
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `EpicMergeRequest`
  - Fields:
    - `targetEpicId`: `Long` [@NotNull(message = "Target epic ID is required")]
- **Frontend Destination:** Epics & Roadmap Timeline (/projects/{id}/epics)
- **UI Trigger / Interaction:** Product manager manages major feature aggregates and child tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-087` — GET `/api/v1/epics/{id}/progress`

- **Method Name:** `getEpicProgress`
- **Summary / Purpose:** Get epic progress statistics
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<EpicProgressResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<EpicProgressResponse>`):**
  - `epicId`: `Long`
  - `epicName`: `String`
  - `totalTaskCount`: `int`
  - `completedTaskCount`: `int`
  - `completedPercentage`: `double`
  - `totalStoryPoints`: `int`
  - `completedStoryPoints`: `int`
  - `estimatedHours`: `double`
  - `remainingHours`: `double`
- **Frontend Destination:** Epics & Roadmap Timeline (/projects/{id}/epics)
- **UI Trigger / Interaction:** Product manager manages major feature aggregates and child tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-088` — POST `/api/v1/epics/{id}/split`

- **Method Name:** `splitEpic`
- **Summary / Purpose:** Split an epic into a new epic
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Epic>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `EpicSplitRequest`
  - Fields:
    - `newEpicName`: `String` [@NotBlank(message = "New epic name is required")]
    - `taskIdsToMove`: `List<Long>` [@NotEmpty(message = "Task IDs to move are required")]
- **Response Model Fields (`ResponseEntity<Epic>`):**
  - `projectId`: `Long`
  - `parentEpicId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `ownerId`: `Long`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
- **Frontend Destination:** Epics & Roadmap Timeline (/projects/{id}/epics)
- **UI Trigger / Interaction:** Product manager manages major feature aggregates and child tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `EstimationController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/EstimationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/EstimationController.java)
- **Tag / Domain:** Task Estimation Controller (REST endpoints for task story points and estimation analytics)
- **Endpoints Count:** 3

### `API-146` — GET `/api/v1/projects/{id}/estimation/accuracy`

- **Method Name:** `getEstimationAccuracy`
- **Summary / Purpose:** Get project estimation accuracy report
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<EstimationAccuracyReportResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<EstimationAccuracyReportResponse>`):**
  - `projectId`: `Long`
  - `totalEstimatedTasks`: `int`
  - `averageEstimatedHours`: `double`
  - `averageActualHours`: `double`
  - `averageVariance`: `double`
  - `accuracyPercentage`: `double`
  - `overEstimatedCount`: `int`
  - `underEstimatedCount`: `int`
  - `accurateCount`: `int`
- **Frontend Destination:** Task Detail Drawer / Sprint Backlog Story Points Popover
- **UI Trigger / Interaction:** User adjusts story point estimates or time-to-complete metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-298` — POST `/api/v1/tasks/{id}/estimate`

- **Method Name:** `estimateTask`
- **Summary / Purpose:** Submit task estimate (story points or hours)
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskEstimateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `EstimateTaskRequest`
  - Fields:
    - `estimateType`: `String` [@NotBlank(message = "Estimate type is required")]
    - `estimatedValue`: `Double` [@NotNull(message = "Estimated value is required")]
    - `actualValue`: `Double`
- **Response Model Fields (`ResponseEntity<TaskEstimateResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `estimateType`: `String`
  - `estimatedValue`: `Double`
  - `actualValue`: `Double`
  - `variance`: `Double`
  - `estimatedBy`: `Long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer / Sprint Backlog Story Points Popover
- **UI Trigger / Interaction:** User adjusts story point estimates or time-to-complete metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-299` — GET `/api/v1/tasks/{id}/estimate/history`

- **Method Name:** `getEstimateHistory`
- **Summary / Purpose:** Get task estimate history
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskEstimateResponse>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskEstimateResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `estimateType`: `String`
  - `estimatedValue`: `Double`
  - `actualValue`: `Double`
  - `variance`: `Double`
  - `estimatedBy`: `Long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer / Sprint Backlog Story Points Popover
- **UI Trigger / Interaction:** User adjusts story point estimates or time-to-complete metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `EventLogController`

- **Package:** `org.SprintForge.platform.event.logging`
- **Source File:** [`src/main/java/org/SprintForge/platform/event/logging/EventLogController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/event/logging/EventLogController.java)
- **Tag / Domain:** EventLogController (N/A)
- **Endpoints Count:** 3

### `API-577` — GET `/platform/events`

- **Method Name:** `getRecentEvents`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<EventLogResponse>>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<EventLogResponse>>>`):**
  - `id`: `Long`
  - `eventId`: `String`
  - `eventType`: `String`
  - `aggregateType`: `String`
  - `aggregateId`: `String`
  - `listener`: `String`
  - `status`: `EventStatus`
  - `durationMs`: `long`
  - `occurredAt`: `Instant`
  - `processedAt`: `Instant`
  - `errorTrace`: `String`
- **Frontend Destination:** Platform Observability -> Event Stream (/admin/events)
- **UI Trigger / Interaction:** Admin inspects domain events, transactional outbox logs, and dead letters
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest, PlatformEventIntegrationTest)

---

### `API-578` — GET `/platform/events/failed`

- **Method Name:** `getFailedEvents`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<EventLogResponse>>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<EventLogResponse>>>`):**
  - `id`: `Long`
  - `eventId`: `String`
  - `eventType`: `String`
  - `aggregateType`: `String`
  - `aggregateId`: `String`
  - `listener`: `String`
  - `status`: `EventStatus`
  - `durationMs`: `long`
  - `occurredAt`: `Instant`
  - `processedAt`: `Instant`
  - `errorTrace`: `String`
- **Frontend Destination:** Platform Observability -> Event Stream (/admin/events)
- **UI Trigger / Interaction:** Admin inspects domain events, transactional outbox logs, and dead letters
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest, PlatformEventIntegrationTest)

---

### `API-581` — GET `/platform/events/{id}`

- **Method Name:** `getEventById`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<EventLogResponse>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<EventLogResponse>>`):**
  - `id`: `Long`
  - `eventId`: `String`
  - `eventType`: `String`
  - `aggregateType`: `String`
  - `aggregateId`: `String`
  - `listener`: `String`
  - `status`: `EventStatus`
  - `durationMs`: `long`
  - `occurredAt`: `Instant`
  - `processedAt`: `Instant`
  - `errorTrace`: `String`
- **Frontend Destination:** Platform Observability -> Event Stream (/admin/events)
- **UI Trigger / Interaction:** Admin inspects domain events, transactional outbox logs, and dead letters
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest, PlatformEventIntegrationTest)

---

## Controller: `EventMonitoringController`

- **Package:** `org.SprintForge.platform.event.monitoring`
- **Source File:** [`src/main/java/org/SprintForge/platform/event/monitoring/EventMonitoringController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/event/monitoring/EventMonitoringController.java)
- **Tag / Domain:** EventMonitoringController (N/A)
- **Endpoints Count:** 2

### `API-579` — GET `/platform/events/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<EventHealthResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<EventHealthResponse>>`):**
  - `status`: `String`
  - `registeredListenersCount`: `int`
  - `deadLetterQueueSize`: `long`
  - `pendingOutboxEvents`: `long`
  - `listenersStatus`: `Map<String, String>`
- **Frontend Destination:** Platform Observability -> Event Stream (/admin/events)
- **UI Trigger / Interaction:** Admin inspects domain events, transactional outbox logs, and dead letters
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PlatformEventIntegrationTest)

---

### `API-580` — GET `/platform/events/metrics`

- **Method Name:** `getMetrics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<EventMetricsResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<EventMetricsResponse>>`):**
  - `totalPublishedEvents`: `long`
  - `totalProcessedEvents`: `long`
  - `totalFailedEvents`: `long`
  - `retryCount`: `long`
  - `averageProcessingTimeMs`: `double`
  - `slowestListener`: `String`
  - `deadLetterCount`: `long`
  - `listenerHealth`: `Map<String, String>`
- **Frontend Destination:** Platform Observability -> Event Stream (/admin/events)
- **UI Trigger / Interaction:** Admin inspects domain events, transactional outbox logs, and dead letters
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PlatformEventIntegrationTest)

---

## Controller: `ExecutiveDashboardController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.executive.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/executive/controller/ExecutiveDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/executive/controller/ExecutiveDashboardController.java)
- **Tag / Domain:** Executive Dashboard Controller (REST endpoints for C-level management visibility, portfolio completion rollups, and cross-project KPIs)
- **Endpoints Count:** 1

### `API-089` — GET `/api/v1/executive/dashboard`

- **Method Name:** `getExecutiveDashboard`
- **Summary / Purpose:** Get executive dashboard metrics across all portfolios and projects
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ExecutiveDashboardResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ExecutiveDashboardResponse>`):**
  - `workspaceId`: `Long`
  - `totalProjects`: `long`
  - `activeProjects`: `long`
  - `portfolioCompletionPercentage`: `double`
  - `overallHealthStatus`: `String`
  - `totalOpenRisks`: `long`
  - `totalPendingApprovals`: `long`
  - `totalVelocity`: `double`
  - `portfolios`: `List<PortfolioResponse>`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `FeatureFlagController`

- **Package:** `org.SprintForge.platform.featureflag.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/featureflag/controller/FeatureFlagController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/featureflag/controller/FeatureFlagController.java)
- **Tag / Domain:** FeatureFlagController (N/A)
- **Endpoints Count:** 4

### `API-518` — GET `/feature-flags`

- **Method Name:** `getAllFlags`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<FeatureFlagResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<FeatureFlagResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `enabled`: `boolean`
  - `scope`: `String`
  - `workspaceId`: `String`
  - `rolloutPercentage`: `int`
  - `createdAt`: `Instant`
  - `updatedAt`: `Instant`
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-519` — POST `/feature-flags`

- **Method Name:** `createOrUpdateFlag`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<FeatureFlagResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<FeatureFlagResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `enabled`: `boolean`
  - `scope`: `String`
  - `workspaceId`: `String`
  - `rolloutPercentage`: `int`
  - `createdAt`: `Instant`
  - `updatedAt`: `Instant`
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-520` — GET `/feature-flags/check/{name}`

- **Method Name:** `checkFlag`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Boolean>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-521` — PATCH `/feature-flags/{id}`

- **Method Name:** `toggleFlag`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<FeatureFlagResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<FeatureFlagResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `enabled`: `boolean`
  - `scope`: `String`
  - `workspaceId`: `String`
  - `rolloutPercentage`: `int`
  - `createdAt`: `Instant`
  - `updatedAt`: `Instant`
  - `id`: `Long`
  - `name`: `String`
  - `description`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

## Controller: `FileStorageController`

- **Package:** `org.SprintForge.platform.storage.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/storage/controller/FileStorageController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/storage/controller/FileStorageController.java)
- **Tag / Domain:** FileStorageController (N/A)
- **Endpoints Count:** 8

### `API-522` — POST `/files`

- **Method Name:** `uploadFile`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<FileUploadResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `FileUploadRequest`
  - Fields:
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `ownerId`: `String`
    - `originalName`: `String`
    - `mimeType`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `ownerId`: `String`
    - `originalName`: `String`
    - `mimeType`: `String`
- **Response Model Fields (`ResponseEntity<FileUploadResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `ownerId`: `String`
  - `originalName`: `String`
  - `mimeType`: `String`
  - `extension`: `String`
  - `sizeBytes`: `long`
  - `checksum`: `String`
  - `storageProvider`: `StorageProviderType`
  - `visibility`: `FileVisibility`
  - `downloadUrl`: `String`
  - *...and 14 additional fields*
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-523` — GET `/files/quota`

- **Method Name:** `getQuota`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<FileQuotaResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<FileQuotaResponse>`):**
  - `workspaceId`: `String`
  - `usedSpaceBytes`: `long`
  - `maxQuotaBytes`: `long`
  - `usagePercentage`: `double`
  - `isQuotaExceeded`: `boolean`
  - `workspaceId`: `String`
  - `usedSpaceBytes`: `long`
  - `maxQuotaBytes`: `long`
  - `usagePercentage`: `double`
  - `isQuotaExceeded`: `boolean`
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-524` — DELETE `/files/{id}`

- **Method Name:** `deleteFile`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-525` — GET `/files/{id}`

- **Method Name:** `getFileDetails`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<StoredFileResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<StoredFileResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `ownerId`: `String`
  - `originalName`: `String`
  - `mimeType`: `String`
  - `extension`: `String`
  - `sizeBytes`: `long`
  - `checksum`: `String`
  - `storageProvider`: `StorageProviderType`
  - `visibility`: `FileVisibility`
  - `isDeleted`: `boolean`
  - *...and 14 additional fields*
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-526` — GET `/files/{id}/download`

- **Method Name:** `unknown`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `unknown`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-527` — GET `/files/{id}/metadata`

- **Method Name:** `getMetadata`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<FileMetadataResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<FileMetadataResponse>>`):**
  - `id`: `Long`
  - `fileId`: `Long`
  - `metaKey`: `String`
  - `metaValue`: `String`
  - `id`: `Long`
  - `fileId`: `Long`
  - `metaKey`: `String`
  - `metaValue`: `String`
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-528` — POST `/files/{id}/restore`

- **Method Name:** `restoreFile`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<StoredFileResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<StoredFileResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `ownerId`: `String`
  - `originalName`: `String`
  - `mimeType`: `String`
  - `extension`: `String`
  - `sizeBytes`: `long`
  - `checksum`: `String`
  - `storageProvider`: `StorageProviderType`
  - `visibility`: `FileVisibility`
  - `isDeleted`: `boolean`
  - *...and 14 additional fields*
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

### `API-529` — GET `/files/{id}/versions`

- **Method Name:** `getVersions`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<FileVersionResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<FileVersionResponse>>`):**
  - `id`: `Long`
  - `fileId`: `Long`
  - `versionNumber`: `int`
  - `uploadedBy`: `String`
  - `checksum`: `String`
  - `sizeBytes`: `long`
  - `uploadedAt`: `Instant`
  - `id`: `Long`
  - `fileId`: `Long`
  - `versionNumber`: `int`
  - `uploadedBy`: `String`
  - `checksum`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SearchAndStoragePlatformIntegrationTest)

---

## Controller: `ForecastController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.forecast.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/forecast/controller/ForecastController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/forecast/controller/ForecastController.java)
- **Tag / Domain:** Forecast Controller (REST endpoints for predictive completion, release delay risk, and capacity forecasting)
- **Endpoints Count:** 3

### `API-175` — GET `/api/v1/projects/{projectId}/forecast`

- **Method Name:** `getCompletionForecast`
- **Summary / Purpose:** Get project completion forecast and estimated completion date
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CompletionForecastResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CompletionForecastResponse>`):**
  - `projectId`: `Long`
  - `estimatedCompletionDate`: `LocalDate`
  - `projectedDaysRemaining`: `long`
  - `confidenceScorePercentage`: `double`
  - `forecastStatus`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-176` — GET `/api/v1/projects/{projectId}/forecast/release`

- **Method Name:** `getReleaseForecast`
- **Summary / Purpose:** Get release delay probability and risk level forecast
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseForecastResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseForecastResponse>`):**
  - `projectId`: `Long`
  - `targetReleaseDate`: `LocalDate`
  - `estimatedReleaseDate`: `LocalDate`
  - `delayProbabilityPercentage`: `double`
  - `riskLevel`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-177` — GET `/api/v1/projects/{projectId}/forecast/resources`

- **Method Name:** `getResourceForecast`
- **Summary / Purpose:** Get 30-day resource capacity and deficit forecast
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ResourceForecastResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ResourceForecastResponse>`):**
  - `projectId`: `Long`
  - `requiredHoursNext30Days`: `double`
  - `availableHoursNext30Days`: `double`
  - `predictedDeficitHours`: `double`
  - `resourceActionRecommendation`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `GlobalSearchController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/GlobalSearchController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/GlobalSearchController.java)
- **Tag / Domain:** Global Search Controller (REST endpoints for specification-based global task search and auto-suggestions)
- **Endpoints Count:** 7

### `API-256` — GET `/api/v1/search`

- **Method Name:** `searchTasks`
- **Summary / Purpose:** Global search tasks with advanced specification filters
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-257` — GET `/api/v1/search/suggestions`

- **Method Name:** `getSuggestions`
- **Summary / Purpose:** Get instant auto-complete suggestions for task search
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<String>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-595` — GET `/search`

- **Method Name:** `search`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SearchResultResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SearchResultResponse>>`):**
  - `id`: `Long`
  - `entityType`: `String`
  - `entityId`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `title`: `String`
  - `contentSnippet`: `String`
  - `highlightedTitle`: `String`
  - `relevanceScore`: `double`
  - `lastIndexedAt`: `Instant`
  - `id`: `Long`
  - `entityType`: `String`
  - *...and 8 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-598` — POST `/search/global`

- **Method Name:** `searchGlobal`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SearchResultResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `GlobalSearchRequest`
  - Fields:
    - `query`: `String`
    - `workspaceId`: `String`
    - `userId`: `String`
    - `entityTypes`: `List<String>`
    - `query`: `String`
    - `workspaceId`: `String`
    - `userId`: `String`
    - `entityTypes`: `List<String>`
- **Response Model Fields (`ResponseEntity<List<SearchResultResponse>>`):**
  - `id`: `Long`
  - `entityType`: `String`
  - `entityId`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `title`: `String`
  - `contentSnippet`: `String`
  - `highlightedTitle`: `String`
  - `relevanceScore`: `double`
  - `lastIndexedAt`: `Instant`
  - `id`: `Long`
  - `entityType`: `String`
  - *...and 8 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-599` — GET `/search/history`

- **Method Name:** `getHistory`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SearchHistoryResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SearchHistoryResponse>>`):**
  - `id`: `Long`
  - `userId`: `String`
  - `workspaceId`: `String`
  - `queryText`: `String`
  - `resultCount`: `int`
  - `executionTimeMs`: `long`
  - `searchedAt`: `Instant`
  - `id`: `Long`
  - `userId`: `String`
  - `workspaceId`: `String`
  - `queryText`: `String`
  - `resultCount`: `int`
  - *...and 2 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-601` — GET `/search/popular`

- **Method Name:** `getPopularQueries`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<String>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-606` — GET `/search/suggestions`

- **Method Name:** `getSuggestions`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SearchSuggestionResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SearchSuggestionResponse>`):**
  - `prefix`: `String`
  - `suggestions`: `List<String>`
  - `prefix`: `String`
  - `suggestions`: `List<String>`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `GoalController`

- **Package:** `org.SprintForge.modules.workspace.project.goal.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/goal/controller/GoalController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/goal/controller/GoalController.java)
- **Tag / Domain:** Goal Controller (REST endpoints for managing strategic goals)
- **Endpoints Count:** 7

### `API-090` — DELETE `/api/v1/goals/{goalId}`

- **Method Name:** `deleteGoal`
- **Summary / Purpose:** Delete a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-091` — GET `/api/v1/goals/{goalId}`

- **Method Name:** `getGoal`
- **Summary / Purpose:** Get goal details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-092` — PATCH `/api/v1/goals/{goalId}`

- **Method Name:** `updateGoal`
- **Summary / Purpose:** Update goal details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-093` — POST `/api/v1/goals/{goalId}/archive`

- **Method Name:** `archiveGoal`
- **Summary / Purpose:** Archive a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-094` — POST `/api/v1/goals/{goalId}/clone`

- **Method Name:** `cloneGoal`
- **Summary / Purpose:** Clone a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-178` — GET `/api/v1/projects/{projectId}/goals`

- **Method Name:** `getGoals`
- **Summary / Purpose:** Get all strategic goals for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<GoalResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<GoalResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-179` — POST `/api/v1/projects/{projectId}/goals`

- **Method Name:** `createGoal`
- **Summary / Purpose:** Create a strategic goal for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `priority`: `String`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `GovernanceDashboardController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.dashboard.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/dashboard/controller/GovernanceDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/dashboard/controller/GovernanceDashboardController.java)
- **Tag / Domain:** Governance Dashboard Controller (REST endpoints for enterprise project governance score, compliance metrics, and risk overview)
- **Endpoints Count:** 2

### `API-180` — GET `/api/v1/projects/{projectId}/governance`

- **Method Name:** `getProjectGovernance`
- **Summary / Purpose:** Get full governance dashboard metrics for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GovernanceDashboardResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GovernanceDashboardResponse>`):**
  - `projectId`: `Long`
  - `openRisks`: `long`
  - `criticalRisks`: `long`
  - `pendingApprovals`: `long`
  - `rejectedApprovals`: `long`
  - `openDecisions`: `long`
  - `documentsCount`: `long`
  - `recentChangesCount`: `long`
  - `governanceScore`: `double`
  - `complianceStatus`: `String`
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (GovernanceDashboardServiceImplTest)

---

### `API-181` — GET `/api/v1/projects/{projectId}/governance/summary`

- **Method Name:** `getProjectGovernanceSummary`
- **Summary / Purpose:** Get executive governance summary for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GovernanceSummaryResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GovernanceSummaryResponse>`):**
  - `projectId`: `Long`
  - `governanceScore`: `double`
  - `healthSummary`: `String`
  - `totalRisks`: `long`
  - `totalDecisions`: `long`
  - `totalApprovals`: `long`
  - `totalDocuments`: `long`
  - `totalChanges`: `long`
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (GovernanceDashboardServiceImplTest)

---

## Controller: `HealthController`

- **Package:** `org.SprintForge.platform.monitoring.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/monitoring/controller/HealthController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/monitoring/controller/HealthController.java)
- **Tag / Domain:** HealthController (N/A)
- **Endpoints Count:** 3

### `API-530` — GET `/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<HealthCheckResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<HealthCheckResponse>`):**
  - `status`: `String`
  - `components`: `Map<String, Object>`
  - `checkedAt`: `Instant`
  - `status`: `String`
  - `components`: `Map<String, Object>`
  - `checkedAt`: `Instant`
- **Frontend Destination:** Platform Health Status Widget / Infrastructure Dashboard
- **UI Trigger / Interaction:** Automated polling for system uptime, memory, and thread metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-531` — GET `/health/details`

- **Method Name:** `getHealthDetails`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<HealthCheckResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<HealthCheckResponse>`):**
  - `status`: `String`
  - `components`: `Map<String, Object>`
  - `checkedAt`: `Instant`
  - `status`: `String`
  - `components`: `Map<String, Object>`
  - `checkedAt`: `Instant`
- **Frontend Destination:** Platform Health Status Widget / Infrastructure Dashboard
- **UI Trigger / Interaction:** Automated polling for system uptime, memory, and thread metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-565` — GET `/metrics`

- **Method Name:** `getMetrics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category F (Internal infrastructure or monitoring)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Platform Health Status Widget / Infrastructure Dashboard
- **UI Trigger / Interaction:** Automated polling for system uptime, memory, and thread metrics
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

## Controller: `ImportExportController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/ImportExportController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/ImportExportController.java)
- **Tag / Domain:** Import & Export Controller (REST endpoints for enterprise CSV, Excel, and JSON task migration)
- **Endpoints Count:** 2

### `API-286` — POST `/api/v1/tasks/export`

- **Method Name:** `exportTasks`
- **Summary / Purpose:** Export tasks to CSV, Excel, JSON, or PDF
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ExportJobResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ExportTaskRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `format`: `String`
- **Response Model Fields (`ResponseEntity<ExportJobResponse>`):**
  - `jobId`: `String`
  - `status`: `String`
  - `downloadUrl`: `String`
  - `format`: `String`
  - `exportedTasksCount`: `int`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-287` — POST `/api/v1/tasks/import`

- **Method Name:** `importTasks`
- **Summary / Purpose:** Import tasks from CSV, Excel, or JSON
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ImportResultResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ImportTaskRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `format`: `String`
    - `fileContentBase64`: `String`
- **Response Model Fields (`ResponseEntity<ImportResultResponse>`):**
  - `totalRecords`: `int`
  - `importedCount`: `int`
  - `failedCount`: `int`
  - `errors`: `List<String>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `InboxController`

- **Package:** `org.SprintForge.modules.workspace.inbox.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/inbox/controller/InboxController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/inbox/controller/InboxController.java)
- **Tag / Domain:** Inbox Controller (REST endpoints for Linear-inspired notification & task update inbox)
- **Endpoints Count:** 4

### `API-110` — GET `/api/v1/me/inbox`

- **Method Name:** `getInbox`
- **Summary / Purpose:** Get active user inbox items
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<InboxItem>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<InboxItem>>`):**
  - `userId`: `Long`
  - `taskId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `summary`: `String`
  - `snoozedUntil`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-111` — PATCH `/api/v1/me/inbox/{id}/archive`

- **Method Name:** `archiveItem`
- **Summary / Purpose:** Archive inbox item
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InboxItem>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<InboxItem>`):**
  - `userId`: `Long`
  - `taskId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `summary`: `String`
  - `snoozedUntil`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-112` — PATCH `/api/v1/me/inbox/{id}/read`

- **Method Name:** `markAsRead`
- **Summary / Purpose:** Mark inbox item as read
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InboxItem>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<InboxItem>`):**
  - `userId`: `Long`
  - `taskId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `summary`: `String`
  - `snoozedUntil`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-113` — PATCH `/api/v1/me/inbox/{id}/snooze`

- **Method Name:** `snoozeItem`
- **Summary / Purpose:** Snooze inbox item for specified minutes
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InboxItem>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<InboxItem>`):**
  - `userId`: `Long`
  - `taskId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `summary`: `String`
  - `snoozedUntil`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `IntegrationController`

- **Package:** `org.SprintForge.modules.integration.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/integration/controller/IntegrationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/integration/controller/IntegrationController.java)
- **Tag / Domain:** IntegrationController (N/A)
- **Endpoints Count:** 7

### `API-532` — POST `/integrations/connections`

- **Method Name:** `createConnection`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ConnectionResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateConnectionRequest`
  - Fields:
    - `sourceNodeId`: `Long` [@NotNull(message = "Source node ID is required")]
    - `targetNodeId`: `Long` [@NotNull(message = "Target node ID is required")]
    - `conditionExpression`: `String`
- **Response Model Fields (`ResponseEntity<ConnectionResponse>`):**
  - `id`: `Long`
  - `connectionName`: `String`
  - `providerKey`: `String`
  - `status`: `String`
  - `healthStatus`: `String`
  - `baseUrl`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-533` — PATCH `/integrations/connections/{id}`

- **Method Name:** `updateConnection`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ConnectionResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateConnectionRequest`
  - Fields:
    - `sourceNodeId`: `Long` [@NotNull(message = "Source node ID is required")]
    - `targetNodeId`: `Long` [@NotNull(message = "Target node ID is required")]
    - `conditionExpression`: `String`
- **Response Model Fields (`ResponseEntity<ConnectionResponse>`):**
  - `id`: `Long`
  - `connectionName`: `String`
  - `providerKey`: `String`
  - `status`: `String`
  - `healthStatus`: `String`
  - `baseUrl`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-534` — POST `/integrations/connections/{id}/validate`

- **Method Name:** `validateConnection`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Boolean>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-535` — GET `/integrations/connectors`

- **Method Name:** `getConnectors`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ConnectorDescriptor>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ConnectorDescriptor>>`):**
  - `connectorKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `version`: `String`
  - `description`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-536` — GET `/integrations/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-541` — GET `/integrations/providers`

- **Method Name:** `getProviders`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<IntegrationProvider>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<IntegrationProvider>>`):**
  - `providerKey`: `String`
  - `displayName`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `documentationUrl`: `String`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

### `API-542` — GET `/integrations/statistics`

- **Method Name:** `getStatistics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (AuthenticationManagerTest)

---

## Controller: `JobDashboardController`

- **Package:** `org.SprintForge.platform.scheduler.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/scheduler/controller/JobDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/scheduler/controller/JobDashboardController.java)
- **Tag / Domain:** JobDashboardController (N/A)
- **Endpoints Count:** 3

### `API-582` — GET `/platform/jobs/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobStatisticsResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobStatisticsResponse>`):**
  - `totalJobs`: `long`
  - `runningJobs`: `long`
  - `queuedJobs`: `long`
  - `completedJobs`: `long`
  - `failedJobs`: `long`
  - `deadJobs`: `long`
  - `averageExecutionTimeMs`: `double`
  - `jobsByType`: `Map<String, Long>`
  - `totalJobs`: `long`
  - `runningJobs`: `long`
  - `queuedJobs`: `long`
  - `completedJobs`: `long`
  - *...and 4 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest)

---

### `API-583` — GET `/platform/jobs/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<String>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest)

---

### `API-584` — GET `/platform/jobs/statistics`

- **Method Name:** `getStatistics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobStatisticsResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobStatisticsResponse>`):**
  - `totalJobs`: `long`
  - `runningJobs`: `long`
  - `queuedJobs`: `long`
  - `completedJobs`: `long`
  - `failedJobs`: `long`
  - `deadJobs`: `long`
  - `averageExecutionTimeMs`: `double`
  - `jobsByType`: `Map<String, Long>`
  - `totalJobs`: `long`
  - `runningJobs`: `long`
  - `queuedJobs`: `long`
  - `completedJobs`: `long`
  - *...and 4 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest)

---

## Controller: `JobExecutionController`

- **Package:** `org.SprintForge.platform.scheduler.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/scheduler/controller/JobExecutionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/scheduler/controller/JobExecutionController.java)
- **Tag / Domain:** JobExecutionController (N/A)
- **Endpoints Count:** 5

### `API-544` — POST `/jobs`

- **Method Name:** `submitJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateJobRequest`
  - Fields:
    - `jobType`: `JobType`
    - `jobName`: `String`
    - `payload`: `String`
    - `createdBy`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `correlationId`: `String`
    - `jobType`: `JobType`
    - `jobName`: `String`
    - `payload`: `String`
    - `createdBy`: `String`
    - `workspaceId`: `String`
    - `projectId`: `String`
    - `correlationId`: `String`
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-546` — GET `/jobs/failed`

- **Method Name:** `getFailedJobs`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<JobResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<JobResponse>>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-547` — GET `/jobs/queued`

- **Method Name:** `getQueuedJobs`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<JobResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<JobResponse>>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-548` — GET `/jobs/{id}`

- **Method Name:** `getJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-549` — POST `/jobs/{id}/cancel`

- **Method Name:** `cancelJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

## Controller: `JobRecoveryController`

- **Package:** `org.SprintForge.platform.scheduler.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/scheduler/controller/JobRecoveryController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/scheduler/controller/JobRecoveryController.java)
- **Tag / Domain:** JobRecoveryController (N/A)
- **Endpoints Count:** 3

### `API-545` — GET `/jobs/dead`

- **Method Name:** `getDeadJobs`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<JobResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<JobResponse>>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-550` — POST `/jobs/{id}/recover`

- **Method Name:** `recoverDeadJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

### `API-551` — POST `/jobs/{id}/retry`

- **Method Name:** `retryJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (BackgroundProcessingPlatformIntegrationTest, QueueDispatcherReliabilityTest)

---

## Controller: `KeyResultController`

- **Package:** `org.SprintForge.modules.workspace.project.keyresult.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/keyresult/controller/KeyResultController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/keyresult/controller/KeyResultController.java)
- **Tag / Domain:** Key Result Controller (REST endpoints for managing objective key results)
- **Endpoints Count:** 7

### `API-102` — DELETE `/api/v1/key-results/{keyResultId}`

- **Method Name:** `deleteKeyResult`
- **Summary / Purpose:** Delete a key result
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-103` — GET `/api/v1/key-results/{keyResultId}`

- **Method Name:** `getKeyResult`
- **Summary / Purpose:** Get key result details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<KeyResultResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<KeyResultResponse>`):**
  - `id`: `Long`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `metricType`: `KeyResultMetricType`
  - `targetValue`: `Double`
  - `currentValue`: `Double`
  - `unit`: `String`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-104` — PATCH `/api/v1/key-results/{keyResultId}`

- **Method Name:** `updateKeyResult`
- **Summary / Purpose:** Update key result details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<KeyResultResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<KeyResultResponse>`):**
  - `id`: `Long`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `metricType`: `KeyResultMetricType`
  - `targetValue`: `Double`
  - `currentValue`: `Double`
  - `unit`: `String`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-105` — GET `/api/v1/key-results/{keyResultId}/forecast`

- **Method Name:** `getForecast`
- **Summary / Purpose:** Get key result forecast
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<KeyResultForecastResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<KeyResultForecastResponse>`):**
  - `keyResultId`: `Long`
  - `title`: `String`
  - `currentValue`: `Double`
  - `targetValue`: `Double`
  - `progressPercentage`: `Double`
  - `runRatePerDay`: `Double`
  - `estimatedCompletionDate`: `LocalDate`
  - `forecastStatus`: `String`
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-106` — PATCH `/api/v1/key-results/{keyResultId}/progress`

- **Method Name:** `updateProgress`
- **Summary / Purpose:** Update key result progress metric
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<KeyResultResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<KeyResultResponse>`):**
  - `id`: `Long`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `metricType`: `KeyResultMetricType`
  - `targetValue`: `Double`
  - `currentValue`: `Double`
  - `unit`: `String`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-137` — GET `/api/v1/objectives/{objectiveId}/key-results`

- **Method Name:** `getKeyResults`
- **Summary / Purpose:** Get all key results for an objective
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<KeyResultResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<KeyResultResponse>>`):**
  - `id`: `Long`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `metricType`: `KeyResultMetricType`
  - `targetValue`: `Double`
  - `currentValue`: `Double`
  - `unit`: `String`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-138` — POST `/api/v1/objectives/{objectiveId}/key-results`

- **Method Name:** `createKeyResult`
- **Summary / Purpose:** Create a key result under an objective
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<KeyResultResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<KeyResultResponse>`):**
  - `id`: `Long`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `metricType`: `KeyResultMetricType`
  - `targetValue`: `Double`
  - `currentValue`: `Double`
  - `unit`: `String`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `MarketplaceController`

- **Package:** `org.SprintForge.modules.workflow.marketplace`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/marketplace/MarketplaceController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/marketplace/MarketplaceController.java)
- **Tag / Domain:** MarketplaceController (N/A)
- **Endpoints Count:** 4

### `API-620` — GET `/workflow/marketplace`

- **Method Name:** `browseMarketplace`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<MarketplaceTemplate>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<MarketplaceTemplate>>`):**
  - `templateKey`: `String`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `author`: `String`
  - `wdlPayload`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-621` — POST `/workflow/marketplace/install`

- **Method Name:** `installTemplate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** None
- **Query Parameters:** `templateKey` (String, required=True, default=None), `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-632` — POST `/workflow/templates/export`

- **Method Name:** `exportTemplate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<String>`
- **Path Variables:** None
- **Query Parameters:** `workflowId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-633` — POST `/workflow/templates/import`

- **Method Name:** `importTemplate`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `String`
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `MetricsController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.metrics.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/metrics/controller/MetricsController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/metrics/controller/MetricsController.java)
- **Tag / Domain:** Metrics Controller (REST endpoints for single source of truth project metrics and historical trends)
- **Endpoints Count:** 3

### `API-119` — POST `/api/v1/metrics/recalculate`

- **Method Name:** `recalculateMetrics`
- **Summary / Purpose:** Force recalculate metrics for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMetricsResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMetricsResponse>`):**
  - `projectId`: `Long`
  - `completionPercentage`: `double`
  - `totalTasks`: `long`
  - `openTasks`: `long`
  - `completedTasks`: `long`
  - `blockedTasks`: `long`
  - `totalStoryPoints`: `int`
  - `velocity`: `double`
  - `cycleTimeDays`: `double`
  - `leadTimeDays`: `double`
  - `sprintSuccessRate`: `double`
  - `releaseProgressPercentage`: `double`
  - *...and 9 additional fields*
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectMetricsServiceImplTest)

---

### `API-197` — GET `/api/v1/projects/{projectId}/metrics`

- **Method Name:** `getProjectMetrics`
- **Summary / Purpose:** Get aggregated single source of truth metrics for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMetricsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMetricsResponse>`):**
  - `projectId`: `Long`
  - `completionPercentage`: `double`
  - `totalTasks`: `long`
  - `openTasks`: `long`
  - `completedTasks`: `long`
  - `blockedTasks`: `long`
  - `totalStoryPoints`: `int`
  - `velocity`: `double`
  - `cycleTimeDays`: `double`
  - `leadTimeDays`: `double`
  - `sprintSuccessRate`: `double`
  - `releaseProgressPercentage`: `double`
  - *...and 9 additional fields*
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectMetricsServiceImplTest)

---

### `API-198` — GET `/api/v1/projects/{projectId}/metrics/history`

- **Method Name:** `getMetricsHistory`
- **Summary / Purpose:** Get historical metrics snapshots for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<MetricHistoryResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<MetricHistoryResponse>>`):**
  - `projectId`: `Long`
  - `snapshotDate`: `LocalDate`
  - `completionPercentage`: `double`
  - `velocity`: `double`
  - `healthScore`: `double`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectMetricsServiceImplTest)

---

## Controller: `MyTasksController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/MyTasksController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/MyTasksController.java)
- **Tag / Domain:** My Tasks Controller (REST endpoints for personal task workspace and daily dashboard)
- **Endpoints Count:** 4

### `API-114` — GET `/api/v1/me/tasks`

- **Method Name:** `getMyTasks`
- **Summary / Purpose:** Get personal tasks overview (assigned, due today, overdue)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MyTasksSummaryResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MyTasksSummaryResponse>`):**
  - `userId`: `Long`
  - `assignedCount`: `int`
  - `dueTodayCount`: `int`
  - `overdueCount`: `int`
  - `blockedCount`: `int`
  - `assignedTasks`: `List<TaskResponse>`
  - `dueTodayTasks`: `List<TaskResponse>`
  - `overdueTasks`: `List<TaskResponse>`
- **Frontend Destination:** My Tasks Personal Cockpit (/my-tasks)
- **UI Trigger / Interaction:** Individual engineer views assigned, overdue, and upcoming tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-115` — GET `/api/v1/me/tasks/blocked`

- **Method Name:** `getMyBlockedTasks`
- **Summary / Purpose:** Get blocked personal tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MyTasksSummaryResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MyTasksSummaryResponse>`):**
  - `userId`: `Long`
  - `assignedCount`: `int`
  - `dueTodayCount`: `int`
  - `overdueCount`: `int`
  - `blockedCount`: `int`
  - `assignedTasks`: `List<TaskResponse>`
  - `dueTodayTasks`: `List<TaskResponse>`
  - `overdueTasks`: `List<TaskResponse>`
- **Frontend Destination:** My Tasks Personal Cockpit (/my-tasks)
- **UI Trigger / Interaction:** Individual engineer views assigned, overdue, and upcoming tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-116` — GET `/api/v1/me/tasks/overdue`

- **Method Name:** `getMyOverdueTasks`
- **Summary / Purpose:** Get overdue personal tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MyTasksSummaryResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MyTasksSummaryResponse>`):**
  - `userId`: `Long`
  - `assignedCount`: `int`
  - `dueTodayCount`: `int`
  - `overdueCount`: `int`
  - `blockedCount`: `int`
  - `assignedTasks`: `List<TaskResponse>`
  - `dueTodayTasks`: `List<TaskResponse>`
  - `overdueTasks`: `List<TaskResponse>`
- **Frontend Destination:** My Tasks Personal Cockpit (/my-tasks)
- **UI Trigger / Interaction:** Individual engineer views assigned, overdue, and upcoming tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-117` — GET `/api/v1/me/tasks/today`

- **Method Name:** `getMyTasksToday`
- **Summary / Purpose:** Get tasks due today
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MyTasksSummaryResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MyTasksSummaryResponse>`):**
  - `userId`: `Long`
  - `assignedCount`: `int`
  - `dueTodayCount`: `int`
  - `overdueCount`: `int`
  - `blockedCount`: `int`
  - `assignedTasks`: `List<TaskResponse>`
  - `dueTodayTasks`: `List<TaskResponse>`
  - `overdueTasks`: `List<TaskResponse>`
- **Frontend Destination:** My Tasks Personal Cockpit (/my-tasks)
- **UI Trigger / Interaction:** Individual engineer views assigned, overdue, and upcoming tasks
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `NodeCatalogController`

- **Package:** `org.SprintForge.modules.workflow.catalog`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/catalog/NodeCatalogController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/catalog/NodeCatalogController.java)
- **Tag / Domain:** NodeCatalogController (N/A)
- **Endpoints Count:** 4

### `API-622` — GET `/workflow/nodes`

- **Method Name:** `getAllNodes`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<NodeDescriptor>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<NodeDescriptor>>`):**
  - `nodeKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `icon`: `String`
  - `description`: `String`
  - `configurationSchema`: `String`
  - `outputSchema`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-623` — GET `/workflow/nodes/category/{category}`

- **Method Name:** `getNodesByCategory`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<NodeDescriptor>>`
- **Path Variables:** `category` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<NodeDescriptor>>`):**
  - `nodeKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `icon`: `String`
  - `description`: `String`
  - `configurationSchema`: `String`
  - `outputSchema`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-624` — GET `/workflow/nodes/search`

- **Method Name:** `searchNodes`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<NodeDescriptor>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<NodeDescriptor>>`):**
  - `nodeKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `icon`: `String`
  - `description`: `String`
  - `configurationSchema`: `String`
  - `outputSchema`: `String`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-625` — GET `/workflow/nodes/{key}`

- **Method Name:** `getNodeByKey`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<NodeDescriptor>`
- **Path Variables:** `key` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<NodeDescriptor>`):**
  - `nodeKey`: `String`
  - `displayName`: `String`
  - `category`: `String`
  - `icon`: `String`
  - `description`: `String`
  - `configurationSchema`: `String`
  - `outputSchema`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `NotificationController`

- **Package:** `org.SprintForge.modules.workspace.notification.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/notification/controller/NotificationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/notification/controller/NotificationController.java)
- **Tag / Domain:** Notification Controller (REST endpoints for in-app notifications and user preferences)
- **Endpoints Count:** 7

### `API-127` — GET `/api/v1/notification-preferences`

- **Method Name:** `getPreferences`
- **Summary / Purpose:** Get notification preferences
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<NotificationPreference>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<NotificationPreference>`):**
  - `userId`: `Long`
  - `muteUntil`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-128` — PATCH `/api/v1/notification-preferences`

- **Method Name:** `updatePreferences`
- **Summary / Purpose:** Update notification preferences
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<NotificationPreference>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `NotificationPreference`
  - Fields:
    - `userId`: `Long`
    - `muteUntil`: `LocalDateTime`
- **Response Model Fields (`ResponseEntity<NotificationPreference>`):**
  - `userId`: `Long`
  - `muteUntil`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-129` — GET `/api/v1/notifications`

- **Method Name:** `getUserNotifications`
- **Summary / Purpose:** Get notifications for the logged in user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskNotification>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskNotification>>`):**
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `actorId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `message`: `String`
  - `actionUrl`: `String`
  - `readAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-130` — PATCH `/api/v1/notifications/read-all`

- **Method Name:** `markAllAsRead`
- **Summary / Purpose:** Mark all notifications as read
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-131` — GET `/api/v1/notifications/unread`

- **Method Name:** `getUnreadNotifications`
- **Summary / Purpose:** Get unread notifications
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskNotification>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskNotification>>`):**
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `actorId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `message`: `String`
  - `actionUrl`: `String`
  - `readAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-132` — DELETE `/api/v1/notifications/{id}`

- **Method Name:** `deleteNotification`
- **Summary / Purpose:** Delete notification
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-133` — PATCH `/api/v1/notifications/{id}/read`

- **Method Name:** `markAsRead`
- **Summary / Purpose:** Mark notification as read
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskNotification>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskNotification>`):**
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `actorId`: `Long`
  - `type`: `String`
  - `title`: `String`
  - `message`: `String`
  - `actionUrl`: `String`
  - `readAt`: `LocalDateTime`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `NotificationPreferenceController`

- **Package:** `org.SprintForge.platform.communication.preference`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/preference/NotificationPreferenceController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/preference/NotificationPreferenceController.java)
- **Tag / Domain:** NotificationPreferenceController (N/A)
- **Endpoints Count:** 2

### `API-566` — GET `/notification-preferences`

- **Method Name:** `getUserPreferences`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<NotificationPreferenceResponse>>>`
- **Path Variables:** None
- **Query Parameters:** `userId` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<NotificationPreferenceResponse>>>`):**
  - `id`: `Long`
  - `userId`: `String`
  - `workspaceId`: `String`
  - `channel`: `NotificationChannel`
  - `notificationType`: `NotificationType`
  - `enabled`: `boolean`
  - `muteUntil`: `LocalDateTime`
  - `digestFrequency`: `DigestFrequency`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-567` — PATCH `/notification-preferences`

- **Method Name:** `updatePreference`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<NotificationPreferenceResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `NotificationPreferenceUpdateRequest`
  - Fields:
    - `userId`: `String`
    - `workspaceId`: `String`
    - `channel`: `NotificationChannel`
    - `notificationType`: `NotificationType`
    - `enabled`: `Boolean`
    - `muteUntil`: `LocalDateTime`
    - `digestFrequency`: `DigestFrequency`
- **Response Model Fields (`ResponseEntity<ApiResponse<NotificationPreferenceResponse>>`):**
  - `id`: `Long`
  - `userId`: `String`
  - `workspaceId`: `String`
  - `channel`: `NotificationChannel`
  - `notificationType`: `NotificationType`
  - `enabled`: `boolean`
  - `muteUntil`: `LocalDateTime`
  - `digestFrequency`: `DigestFrequency`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ObjectiveController`

- **Package:** `org.SprintForge.modules.workspace.project.objective.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/objective/controller/ObjectiveController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/objective/controller/ObjectiveController.java)
- **Tag / Domain:** Objective Controller (REST endpoints for managing goal objectives)
- **Endpoints Count:** 6

### `API-095` — GET `/api/v1/goals/{goalId}/objectives`

- **Method Name:** `getObjectives`
- **Summary / Purpose:** Get all objectives for a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ObjectiveResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ObjectiveResponse>>`):**
  - `id`: `Long`
  - `goalId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `ObjectiveStatus`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `totalKeyResults`: `Integer`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-096` — POST `/api/v1/goals/{goalId}/objectives`

- **Method Name:** `createObjective`
- **Summary / Purpose:** Create an objective under a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ObjectiveResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ObjectiveResponse>`):**
  - `id`: `Long`
  - `goalId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `ObjectiveStatus`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `totalKeyResults`: `Integer`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-134` — DELETE `/api/v1/objectives/{objectiveId}`

- **Method Name:** `deleteObjective`
- **Summary / Purpose:** Delete an objective
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-135` — GET `/api/v1/objectives/{objectiveId}`

- **Method Name:** `getObjective`
- **Summary / Purpose:** Get objective details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ObjectiveResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ObjectiveResponse>`):**
  - `id`: `Long`
  - `goalId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `ObjectiveStatus`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `totalKeyResults`: `Integer`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-136` — PATCH `/api/v1/objectives/{objectiveId}`

- **Method Name:** `updateObjective`
- **Summary / Purpose:** Update objective details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ObjectiveResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ObjectiveResponse>`):**
  - `id`: `Long`
  - `goalId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `ObjectiveStatus`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `totalKeyResults`: `Integer`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-139` — POST `/api/v1/objectives/{objectiveId}/move`

- **Method Name:** `moveObjective`
- **Summary / Purpose:** Move an objective to a different goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ObjectiveResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ObjectiveResponse>`):**
  - `id`: `Long`
  - `goalId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `ObjectiveStatus`
  - `weight`: `Double`
  - `progressPercentage`: `Double`
  - `startDate`: `LocalDate`
  - `targetDate`: `LocalDate`
  - `totalKeyResults`: `Integer`
  - `createdBy`: `Long`
  - *...and 2 additional fields*
- **Frontend Destination:** Strategic Goals & OKR Hub (/goals)
- **UI Trigger / Interaction:** Set corporate/project objectives, update key result target percentages, track alignment
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `PlatformManagementController`

- **Package:** `org.SprintForge.modules.management.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/management/controller/PlatformManagementController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/management/controller/PlatformManagementController.java)
- **Tag / Domain:** PlatformManagementController (N/A)
- **Endpoints Count:** 13

### `API-552` — GET `/management/configuration`

- **Method Name:** `getConfiguration`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-553` — PATCH `/management/configuration`

- **Method Name:** `updateConfiguration`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `Object>`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-554` — GET `/management/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-555` — GET `/management/feature-flags`

- **Method Name:** `getFeatureFlags`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<FeatureFlagEntity>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<FeatureFlagEntity>>`):**
  - `flagKey`: `String`
  - `displayName`: `String`
  - `description`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-556` — PATCH `/management/feature-flags`

- **Method Name:** `updateFeatureFlag`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<FeatureFlagEntity>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `FeatureFlagUpdateRequest`
- **Response Model Fields (`ResponseEntity<FeatureFlagEntity>`):**
  - `flagKey`: `String`
  - `displayName`: `String`
  - `description`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-557` — GET `/management/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, HealthReport>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<Map<String, HealthReport>>`):**
  - `moduleId`: `String`
  - `status`: `HealthStatus`
  - `message`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-558` — GET `/management/license`

- **Method Name:** `getLicense`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<LicenseEntity>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<LicenseEntity>`):**
  - `licenseKey`: `String`
  - `organizationName`: `String`
  - `validUntil`: `LocalDateTime`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-559` — GET `/management/metrics`

- **Method Name:** `getMetrics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, MetricsSnapshot>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<Map<String, MetricsSnapshot>>`):**
  - `moduleId`: `String`
  - `uptimeSeconds`: `long`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-560` — GET `/management/modules`

- **Method Name:** `getModules`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ManagementDescriptor>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ManagementDescriptor>>`):**
  - `moduleId`: `String`
  - `moduleName`: `String`
  - `version`: `String`
  - `category`: `String`
  - `description`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-561` — GET `/management/packages`

- **Method Name:** `getPackages`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<InstalledPackageEntity>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<InstalledPackageEntity>>`):**
  - `packageKey`: `String`
  - `packageName`: `String`
  - `installedBy`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-562` — POST `/management/packages/disable`

- **Method Name:** `disablePackage`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InstalledPackageEntity>`
- **Path Variables:** None
- **Query Parameters:** `packageKey` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<InstalledPackageEntity>`):**
  - `packageKey`: `String`
  - `packageName`: `String`
  - `installedBy`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-563` — POST `/management/packages/enable`

- **Method Name:** `enablePackage`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InstalledPackageEntity>`
- **Path Variables:** None
- **Query Parameters:** `packageKey` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<InstalledPackageEntity>`):**
  - `packageKey`: `String`
  - `packageName`: `String`
  - `installedBy`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

### `API-564` — POST `/management/packages/install`

- **Method Name:** `installPackage`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<InstalledPackageEntity>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `InstallPackageRequest`
- **Response Model Fields (`ResponseEntity<InstalledPackageEntity>`):**
  - `packageKey`: `String`
  - `packageName`: `String`
  - `installedBy`: `String`
- **Frontend Destination:** Platform Management Console (/admin/platform)
- **UI Trigger / Interaction:** Platform Admin manages licenses, installed packages, tenant policies, and rollout flags
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ManagementFacadeTest)

---

## Controller: `PlatformNotificationController`

- **Package:** `org.SprintForge.platform.communication.notification`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/notification/PlatformNotificationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/notification/PlatformNotificationController.java)
- **Tag / Domain:** PlatformNotificationController (N/A)
- **Endpoints Count:** 7

### `API-568` — GET `/notifications`

- **Method Name:** `getAllNotifications`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<NotificationResponse>>>`
- **Path Variables:** None
- **Query Parameters:** `userId` (String, required=True, default=None), `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<NotificationResponse>>>`):**
  - `id`: `Long`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-569` — GET `/notifications/count`

- **Method Name:** `getUnreadCount`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<UnreadCountResponse>>`
- **Path Variables:** None
- **Query Parameters:** `userId` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<UnreadCountResponse>>`):**
  - `recipientId`: `String`
  - `unreadCount`: `long`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-570` — PATCH `/notifications/read-all`

- **Method Name:** `markAllAsRead`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<String>>`
- **Path Variables:** None
- **Query Parameters:** `userId` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-571` — GET `/notifications/unread`

- **Method Name:** `getUnreadNotifications`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<NotificationResponse>>>`
- **Path Variables:** None
- **Query Parameters:** `userId` (String, required=True, default=None), `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<NotificationResponse>>>`):**
  - `id`: `Long`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-572` — DELETE `/notifications/{id}`

- **Method Name:** `deleteNotification`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<Void>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-573` — PATCH `/notifications/{id}/archive`

- **Method Name:** `archiveNotification`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<NotificationResponse>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<NotificationResponse>>`):**
  - `id`: `Long`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

### `API-574` — PATCH `/notifications/{id}/read`

- **Method Name:** `markAsRead`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<NotificationResponse>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<NotificationResponse>>`):**
  - `id`: `Long`
- **Frontend Destination:** Notification Center & Activity Feed Drawer
- **UI Trigger / Interaction:** Bell icon shows unread count; user clicks to read updates, filter by mention, mark all read
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (CommunicationPlatformIntegrationTest)

---

## Controller: `PlatformWebhookController`

- **Package:** `org.SprintForge.platform.communication.webhook`
- **Source File:** [`src/main/java/org/SprintForge/platform/communication/webhook/PlatformWebhookController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/communication/webhook/PlatformWebhookController.java)
- **Tag / Domain:** PlatformWebhookController (N/A)
- **Endpoints Count:** 2

### `API-609` — GET `/webhooks/deliveries`

- **Method Name:** `getDeliveries`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<List<PlatformWebhookDelivery>>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<List<PlatformWebhookDelivery>>>`):**
  - `id`: `Long`
  - `webhookUrl`: `String`
  - `eventType`: `String`
  - `payload`: `String`
  - `status`: `String`
  - `responseCode`: `Integer`
  - `attempts`: `int`
  - `durationMs`: `long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-610` — POST `/webhooks/retry/{id}`

- **Method Name:** `retryDelivery`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiResponse<PlatformWebhookDelivery>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ApiResponse<PlatformWebhookDelivery>>`):**
  - `id`: `Long`
  - `webhookUrl`: `String`
  - `eventType`: `String`
  - `payload`: `String`
  - `status`: `String`
  - `responseCode`: `Integer`
  - `attempts`: `int`
  - `durationMs`: `long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `PortfolioController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.portfolio.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/portfolio/controller/PortfolioController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/portfolio/controller/PortfolioController.java)
- **Tag / Domain:** Portfolio Controller (REST endpoints for managing multi-project portfolios and enterprise rollups)
- **Endpoints Count:** 5

### `API-140` — GET `/api/v1/portfolios`

- **Method Name:** `getPortfolios`
- **Summary / Purpose:** Get all portfolios for a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<PortfolioResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<PortfolioResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `projectIds`: `List<Long>`
  - `overallProgressPercentage`: `double`
  - `totalProjects`: `long`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PortfolioServiceImplTest)

---

### `API-141` — POST `/api/v1/portfolios`

- **Method Name:** `createPortfolio`
- **Summary / Purpose:** Create a multi-project portfolio
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<PortfolioResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreatePortfolioRequest`
  - Fields:
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `name`: `String` [@NotBlank(message = "Portfolio name is required"), @Size(min = 2, max = 150, message = "Portfolio name must be between 2 and 150 characters")]
    - `description`: `String`
    - `ownerId`: `Long`
    - `projectIds`: `List<Long>`
- **Response Model Fields (`ResponseEntity<PortfolioResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `projectIds`: `List<Long>`
  - `overallProgressPercentage`: `double`
  - `totalProjects`: `long`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PortfolioServiceImplTest)

---

### `API-142` — DELETE `/api/v1/portfolios/{portfolioId}`

- **Method Name:** `deletePortfolio`
- **Summary / Purpose:** Delete / archive portfolio
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PortfolioServiceImplTest)

---

### `API-143` — GET `/api/v1/portfolios/{portfolioId}`

- **Method Name:** `getPortfolio`
- **Summary / Purpose:** Get portfolio details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<PortfolioResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<PortfolioResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `projectIds`: `List<Long>`
  - `overallProgressPercentage`: `double`
  - `totalProjects`: `long`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PortfolioServiceImplTest)

---

### `API-144` — PATCH `/api/v1/portfolios/{portfolioId}`

- **Method Name:** `updatePortfolio`
- **Summary / Purpose:** Update portfolio metadata or project assignments
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<PortfolioResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<PortfolioResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `ownerId`: `Long`
  - `status`: `String`
  - `projectIds`: `List<Long>`
  - `overallProgressPercentage`: `double`
  - `totalProjects`: `long`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (PortfolioServiceImplTest)

---

## Controller: `ProgressController`

- **Package:** `org.SprintForge.modules.workspace.project.progress.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/progress/controller/ProgressController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/progress/controller/ProgressController.java)
- **Tag / Domain:** Progress Controller (REST endpoints for strategic progress engine calculation and analytics)
- **Endpoints Count:** 3

### `API-097` — GET `/api/v1/goals/{goalId}/progress`

- **Method Name:** `getGoalProgress`
- **Summary / Purpose:** Get strategic progress for a goal
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<GoalProgressResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<GoalProgressResponse>`):**
  - `goalId`: `Long`
  - `title`: `String`
  - `progressPercentage`: `Double`
  - `status`: `String`
  - `totalObjectives`: `Integer`
  - `completedObjectives`: `Integer`
  - `objectiveDetails`: `List<ObjectiveProgressDetail>`
  - `objectiveId`: `Long`
  - `title`: `String`
  - `progressPercentage`: `Double`
  - `weight`: `Double`
  - `totalKeyResults`: `Integer`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProgressEngineServiceImplTest)

---

### `API-202` — GET `/api/v1/projects/{projectId}/progress`

- **Method Name:** `getProjectProgress`
- **Summary / Purpose:** Get aggregate strategic progress for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectProgressResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectProgressResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `overallProgressPercentage`: `Double`
  - `goalsProgressPercentage`: `Double`
  - `releasesProgressPercentage`: `Double`
  - `tasksProgressPercentage`: `Double`
  - `healthScore`: `String`
  - `totalGoals`: `Integer`
  - `totalReleases`: `Integer`
  - `totalTasks`: `Integer`
  - `goalDetails`: `List<GoalProgressDetail>`
  - `goalId`: `Long`
  - *...and 3 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProgressEngineServiceImplTest)

---

### `API-203` — POST `/api/v1/projects/{projectId}/progress/recalculate`

- **Method Name:** `recalculateProgress`
- **Summary / Purpose:** Recalculate project progress metrics
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectProgressResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectProgressResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `overallProgressPercentage`: `Double`
  - `goalsProgressPercentage`: `Double`
  - `releasesProgressPercentage`: `Double`
  - `tasksProgressPercentage`: `Double`
  - `healthScore`: `String`
  - `totalGoals`: `Integer`
  - `totalReleases`: `Integer`
  - `totalTasks`: `Integer`
  - `goalDetails`: `List<GoalProgressDetail>`
  - `goalId`: `Long`
  - *...and 3 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProgressEngineServiceImplTest)

---

## Controller: `ProjectController`

- **Package:** `org.SprintForge.modules.workspace.project.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/controller/ProjectController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/controller/ProjectController.java)
- **Tag / Domain:** Project Controller (REST endpoints for managing project lifecycle, queries, settings, membership, roles, categories, and dashboard)
- **Endpoints Count:** 46

### `API-120` — DELETE `/api/v1/milestones/{milestoneId}`

- **Method Name:** `deleteMilestone`
- **Summary / Purpose:** Delete a milestone (soft delete)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-121` — PATCH `/api/v1/milestones/{milestoneId}`

- **Method Name:** `updateMilestone`
- **Summary / Purpose:** Update a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-122` — POST `/api/v1/milestones/{milestoneId}/archive`

- **Method Name:** `archiveMilestone`
- **Summary / Purpose:** Archive a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-123` — POST `/api/v1/milestones/{milestoneId}/complete`

- **Method Name:** `completeMilestone`
- **Summary / Purpose:** Complete a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-124` — GET `/api/v1/milestones/{milestoneId}/progress`

- **Method Name:** `getMilestoneProgress`
- **Summary / Purpose:** Get milestone progress
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneProgressResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneProgressResponse>`):**
  - `milestoneId`: `Long`
  - `milestoneName`: `String`
  - `totalTasks`: `long`
  - `completedTasks`: `long`
  - `progressPercentage`: `double`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-125` — DELETE `/api/v1/milestones/{milestoneId}/tasks/{taskId}`

- **Method Name:** `removeTask`
- **Summary / Purpose:** Remove a task from a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-126` — POST `/api/v1/milestones/{milestoneId}/tasks/{taskId}`

- **Method Name:** `assignTask`
- **Summary / Purpose:** Assign a task to a milestone
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-152` — DELETE `/api/v1/projects/{projectId}`

- **Method Name:** `deleteProject`
- **Summary / Purpose:** Delete a project (soft delete)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-153` — GET `/api/v1/projects/{projectId}`

- **Method Name:** `getProject`
- **Summary / Purpose:** Get a project by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-154` — PATCH `/api/v1/projects/{projectId}`

- **Method Name:** `updateProject`
- **Summary / Purpose:** Update a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-159` — POST `/api/v1/projects/{projectId}/archive`

- **Method Name:** `archiveProject`
- **Summary / Purpose:** Archive a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-163` — PATCH `/api/v1/projects/{projectId}/color`

- **Method Name:** `changeColor`
- **Summary / Purpose:** Change project color
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-166` — GET `/api/v1/projects/{projectId}/dashboard`

- **Method Name:** `getDashboardSummary`
- **Summary / Purpose:** Get project dashboard summary
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectDashboardSummaryResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectDashboardSummaryResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `projectKey`: `String`
  - `status`: `String`
  - `completionPercentage`: `Double`
  - `totalTasks`: `Integer`
  - `openTasks`: `Integer`
  - `completedTasks`: `Integer`
  - `velocity`: `Double`
  - `activeSprintId`: `Long`
  - `activeSprintName`: `String`
  - `nextMilestoneId`: `Long`
  - *...and 7 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-173` — POST `/api/v1/projects/{projectId}/duplicate`

- **Method Name:** `duplicateProject`
- **Summary / Purpose:** Duplicate a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-174` — POST `/api/v1/projects/{projectId}/favorite`

- **Method Name:** `toggleFavorite`
- **Summary / Purpose:** Toggle favorite status for project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMemberResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMemberResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `workspaceMemberId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `joinedAt`: `LocalDateTime`
  - `status`: `ProjectMemberStatus`
  - `favorite`: `Boolean`
  - *...and 1 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-182` — PATCH `/api/v1/projects/{projectId}/icon`

- **Method Name:** `changeIcon`
- **Summary / Purpose:** Change project icon
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-183` — PATCH `/api/v1/projects/{projectId}/key`

- **Method Name:** `updateProjectKey`
- **Summary / Purpose:** Update project key
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-186` — POST `/api/v1/projects/{projectId}/lead`

- **Method Name:** `changeLead`
- **Summary / Purpose:** Change project lead
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-187` — POST `/api/v1/projects/{projectId}/lifecycle/activate`

- **Method Name:** `activateProject`
- **Summary / Purpose:** Activate project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-188` — POST `/api/v1/projects/{projectId}/lifecycle/cancel`

- **Method Name:** `cancelProject`
- **Summary / Purpose:** Cancel project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-189` — POST `/api/v1/projects/{projectId}/lifecycle/complete`

- **Method Name:** `completeProject`
- **Summary / Purpose:** Complete project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-190` — POST `/api/v1/projects/{projectId}/lifecycle/pause`

- **Method Name:** `pauseProject`
- **Summary / Purpose:** Pause project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-191` — POST `/api/v1/projects/{projectId}/lifecycle/resume`

- **Method Name:** `resumeProject`
- **Summary / Purpose:** Resume project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-192` — GET `/api/v1/projects/{projectId}/members`

- **Method Name:** `getMembers`
- **Summary / Purpose:** Get all members of a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectMemberResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectMemberResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `workspaceMemberId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `joinedAt`: `LocalDateTime`
  - `status`: `ProjectMemberStatus`
  - `favorite`: `Boolean`
  - *...and 1 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-193` — POST `/api/v1/projects/{projectId}/members`

- **Method Name:** `addMember`
- **Summary / Purpose:** Add a member to a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMemberResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMemberResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `workspaceMemberId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `joinedAt`: `LocalDateTime`
  - `status`: `ProjectMemberStatus`
  - `favorite`: `Boolean`
  - *...and 1 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-194` — DELETE `/api/v1/projects/{projectId}/members/{memberId}`

- **Method Name:** `removeMember`
- **Summary / Purpose:** Remove a member from a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-195` — PATCH `/api/v1/projects/{projectId}/members/{memberId}/allocation`

- **Method Name:** `changeAllocation`
- **Summary / Purpose:** Change allocation percentage of a project member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMemberResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMemberResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `workspaceMemberId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `joinedAt`: `LocalDateTime`
  - `status`: `ProjectMemberStatus`
  - `favorite`: `Boolean`
  - *...and 1 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-196` — PATCH `/api/v1/projects/{projectId}/members/{memberId}/role`

- **Method Name:** `changeRole`
- **Summary / Purpose:** Change role of a project member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectMemberResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectMemberResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `workspaceMemberId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `joinedAt`: `LocalDateTime`
  - `status`: `ProjectMemberStatus`
  - `favorite`: `Boolean`
  - *...and 1 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-199` — GET `/api/v1/projects/{projectId}/milestones`

- **Method Name:** `getProjectMilestones`
- **Summary / Purpose:** Get all active milestones in a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<MilestoneResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<MilestoneResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-200` — POST `/api/v1/projects/{projectId}/milestones`

- **Method Name:** `createMilestone`
- **Summary / Purpose:** Create a milestone in a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MilestoneResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MilestoneResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-201` — GET `/api/v1/projects/{projectId}/milestones/overdue`

- **Method Name:** `getOverdueMilestones`
- **Summary / Purpose:** Get overdue milestones in a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<MilestoneResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<MilestoneResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `dueDate`: `LocalDate`
  - `status`: `MilestoneStatus`
  - `completedAt`: `LocalDateTime`
  - `isArchived`: `Boolean`
  - `progressPercentage`: `Double`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-208` — POST `/api/v1/projects/{projectId}/restore`

- **Method Name:** `restoreProject`
- **Summary / Purpose:** Restore an archived project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-212` — GET `/api/v1/projects/{projectId}/roles`

- **Method Name:** `getRoles`
- **Summary / Purpose:** Get project roles
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectRoleResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectRoleResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `permissions`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-213` — POST `/api/v1/projects/{projectId}/roles`

- **Method Name:** `createRole`
- **Summary / Purpose:** Create a project role
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectRoleResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectRoleResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `permissions`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-214` — POST `/api/v1/projects/{projectId}/roles/{roleId}/clone`

- **Method Name:** `cloneRole`
- **Summary / Purpose:** Clone a project role
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectRoleResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectRoleResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `permissions`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-215` — GET `/api/v1/projects/{projectId}/settings`

- **Method Name:** `getSettings`
- **Summary / Purpose:** Get project settings
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectSettingsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectSettingsResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `allowMultipleAssignees`: `Boolean`
  - `allowTimeTracking`: `Boolean`
  - `allowStoryPoints`: `Boolean`
  - `allowCustomFields`: `Boolean`
  - `allowRecurringTasks`: `Boolean`
  - `allowAutomation`: `Boolean`
  - `allowGuestAccess`: `Boolean`
  - `defaultIssueType`: `String`
  - `defaultTaskStatus`: `String`
  - `defaultPriority`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-216` — PATCH `/api/v1/projects/{projectId}/settings`

- **Method Name:** `updateSettings`
- **Summary / Purpose:** Update project settings
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectSettingsResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectSettingsResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `allowMultipleAssignees`: `Boolean`
  - `allowTimeTracking`: `Boolean`
  - `allowStoryPoints`: `Boolean`
  - `allowCustomFields`: `Boolean`
  - `allowRecurringTasks`: `Boolean`
  - `allowAutomation`: `Boolean`
  - `allowGuestAccess`: `Boolean`
  - `defaultIssueType`: `String`
  - `defaultTaskStatus`: `String`
  - `defaultPriority`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-220` — GET `/api/v1/projects/{projectId}/tags`

- **Method Name:** `getTags`
- **Summary / Purpose:** Get project tags
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectTag>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectTag>>`):**
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-221` — POST `/api/v1/projects/{projectId}/tags`

- **Method Name:** `createTag`
- **Summary / Purpose:** Create project tag
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectTag>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectTag>`):**
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-224` — POST `/api/v1/projects/{projectId}/transfer-ownership`

- **Method Name:** `transferOwnership`
- **Summary / Purpose:** Transfer ownership of a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-228` — PATCH `/api/v1/projects/{projectId}/visibility`

- **Method Name:** `changeVisibility`
- **Summary / Purpose:** Change project visibility
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-470` — GET `/api/v1/workspaces/{workspaceId}/project-categories`

- **Method Name:** `getCategories`
- **Summary / Purpose:** Get workspace project categories
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectCategory>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectCategory>>`):**
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-471` — POST `/api/v1/workspaces/{workspaceId}/project-categories`

- **Method Name:** `createCategory`
- **Summary / Purpose:** Create workspace project category
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectCategory>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectCategory>`):**
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-472` — GET `/api/v1/workspaces/{workspaceId}/projects`

- **Method Name:** `getProjects`
- **Summary / Purpose:** Get all active projects in a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-473` — POST `/api/v1/workspaces/{workspaceId}/projects`

- **Method Name:** `createProject`
- **Summary / Purpose:** Create a project in a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ProjectResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ProjectResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

### `API-474` — GET `/api/v1/workspaces/{workspaceId}/projects/archived`

- **Method Name:** `getArchivedProjects`
- **Summary / Purpose:** Get all archived projects in a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ProjectResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ProjectResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `projectKey`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `color`: `String`
  - `visibility`: `ProjectVisibility`
  - `status`: `ProjectStatusType`
  - `ownerId`: `Long`
  - `slug`: `String`
  - *...and 16 additional fields*
- **Frontend Destination:** Projects Hub & Project Settings (/projects)
- **UI Trigger / Interaction:** Create, edit, star, archive projects, manage project members and custom roles
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ProjectServiceTest, ProjectMemberServiceTest, ProjectRoleServiceImplTest, ProjectDashboardServiceImplTest)

---

## Controller: `RecurringTaskController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/RecurringTaskController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/RecurringTaskController.java)
- **Tag / Domain:** Recurring Task Controller (REST endpoints for configuring enterprise task recurrence schedules)
- **Endpoints Count:** 7

### `API-231` — DELETE `/api/v1/recurring/{id}`

- **Method Name:** `cancelRecurringTask`
- **Summary / Purpose:** Cancel recurring task schedule
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-232` — PATCH `/api/v1/recurring/{id}`

- **Method Name:** `updateRecurringTask`
- **Summary / Purpose:** Update recurring task schedule
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecurringTaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateRecurringTaskRequest`
  - Fields:
    - `frequency`: `RecurringTaskFrequency`
    - `intervalValue`: `Integer`
    - `daysOfWeek`: `List<String>`
    - `dayOfMonth`: `Integer`
    - `monthOfYear`: `Integer`
    - `cronExpression`: `String`
    - `startDate`: `LocalDate`
    - `endDate`: `LocalDate`
    - `maxOccurrences`: `Integer`
    - `timezone`: `String`
    - `skipWeekends`: `Boolean`
    - `skipHolidays`: `Boolean`
    - `autoAssign`: `Boolean`
    - `autoNotify`: `Boolean`
- **Response Model Fields (`ResponseEntity<RecurringTaskResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `frequency`: `RecurringTaskFrequency`
  - `intervalValue`: `Integer`
  - `daysOfWeek`: `List<String>`
  - `dayOfMonth`: `Integer`
  - `monthOfYear`: `Integer`
  - `cronExpression`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - *...and 14 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-233` — POST `/api/v1/recurring/{id}/pause`

- **Method Name:** `pauseRecurringTask`
- **Summary / Purpose:** Pause recurring task schedule
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecurringTaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<RecurringTaskResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `frequency`: `RecurringTaskFrequency`
  - `intervalValue`: `Integer`
  - `daysOfWeek`: `List<String>`
  - `dayOfMonth`: `Integer`
  - `monthOfYear`: `Integer`
  - `cronExpression`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - *...and 14 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-234` — POST `/api/v1/recurring/{id}/preview`

- **Method Name:** `previewOccurrences`
- **Summary / Purpose:** Preview future occurrence dates for a recurring task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<OccurrencePreviewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `PreviewOccurrencesRequest`
  - Fields:
    - `frequency`: `RecurringTaskFrequency`
    - `intervalValue`: `Integer`
    - `daysOfWeek`: `List<String>`
    - `dayOfMonth`: `Integer`
    - `monthOfYear`: `Integer`
    - `startDate`: `LocalDate`
    - `endDate`: `LocalDate`
- **Response Model Fields (`ResponseEntity<OccurrencePreviewResponse>`):**
  - `recurringTaskId`: `Long`
  - `requestedCount`: `Integer`
  - `previewDates`: `List<LocalDateTime>`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-235` — POST `/api/v1/recurring/{id}/resume`

- **Method Name:** `resumeRecurringTask`
- **Summary / Purpose:** Resume recurring task schedule
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecurringTaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RecurringTaskResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `frequency`: `RecurringTaskFrequency`
  - `intervalValue`: `Integer`
  - `daysOfWeek`: `List<String>`
  - `dayOfMonth`: `Integer`
  - `monthOfYear`: `Integer`
  - `cronExpression`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - *...and 14 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-305` — GET `/api/v1/tasks/{id}/recurring`

- **Method Name:** `getRecurringTaskByTaskId`
- **Summary / Purpose:** Get recurrence schedule for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecurringTaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RecurringTaskResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `frequency`: `RecurringTaskFrequency`
  - `intervalValue`: `Integer`
  - `daysOfWeek`: `List<String>`
  - `dayOfMonth`: `Integer`
  - `monthOfYear`: `Integer`
  - `cronExpression`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - *...and 14 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-306` — POST `/api/v1/tasks/{id}/recurring`

- **Method Name:** `scheduleRecurringTask`
- **Summary / Purpose:** Schedule recurrence for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecurringTaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateRecurringTaskRequest`
  - Fields:
    - `frequency`: `RecurringTaskFrequency` [@NotNull(message = "Frequency is required")]
    - `intervalValue`: `Integer`
    - `daysOfWeek`: `List<String>`
    - `dayOfMonth`: `Integer`
    - `monthOfYear`: `Integer`
    - `cronExpression`: `String`
    - `startDate`: `LocalDate` [@NotNull(message = "Start date is required")]
    - `endDate`: `LocalDate`
    - `maxOccurrences`: `Integer`
    - `timezone`: `String`
    - `skipWeekends`: `Boolean`
    - `skipHolidays`: `Boolean`
    - `autoAssign`: `Boolean`
    - `autoNotify`: `Boolean`
- **Response Model Fields (`ResponseEntity<RecurringTaskResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `frequency`: `RecurringTaskFrequency`
  - `intervalValue`: `Integer`
  - `daysOfWeek`: `List<String>`
  - `dayOfMonth`: `Integer`
  - `monthOfYear`: `Integer`
  - `cronExpression`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - *...and 14 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ReleaseController`

- **Package:** `org.SprintForge.modules.workspace.project.release.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/release/controller/ReleaseController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/release/controller/ReleaseController.java)
- **Tag / Domain:** Release Controller (REST endpoints for project release management and planning)
- **Endpoints Count:** 9

### `API-204` — GET `/api/v1/projects/{projectId}/releases`

- **Method Name:** `getReleases`
- **Summary / Purpose:** Get all releases for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ReleaseResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ReleaseResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-205` — POST `/api/v1/projects/{projectId}/releases`

- **Method Name:** `createRelease`
- **Summary / Purpose:** Create a new release for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-237` — DELETE `/api/v1/releases/{releaseId}`

- **Method Name:** `deleteRelease`
- **Summary / Purpose:** Delete / archive a release
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-238` — GET `/api/v1/releases/{releaseId}`

- **Method Name:** `getRelease`
- **Summary / Purpose:** Get release details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-239` — PATCH `/api/v1/releases/{releaseId}`

- **Method Name:** `updateRelease`
- **Summary / Purpose:** Update release details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-240` — POST `/api/v1/releases/{releaseId}/clone`

- **Method Name:** `cloneRelease`
- **Summary / Purpose:** Clone a release
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-241` — GET `/api/v1/releases/{releaseId}/progress`

- **Method Name:** `getReleaseProgress`
- **Summary / Purpose:** Get release progress metrics
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseProgressResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseProgressResponse>`):**
  - `releaseId`: `Long`
  - `releaseName`: `String`
  - `version`: `String`
  - `status`: `String`
  - `progressPercentage`: `Double`
  - `totalTasks`: `Integer`
  - `completedTasks`: `Integer`
  - `openTasks`: `Integer`
  - `totalSprints`: `Integer`
  - `totalMilestones`: `Integer`
  - `plannedReleaseDate`: `LocalDate`
  - `daysRemaining`: `Long`
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-242` — POST `/api/v1/releases/{releaseId}/publish`

- **Method Name:** `publishRelease`
- **Summary / Purpose:** Publish a release
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReleaseResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReleaseResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `version`: `String`
  - `description`: `String`
  - `releaseType`: `ReleaseType`
  - `status`: `ReleaseStatus`
  - `plannedStart`: `LocalDate`
  - `plannedReleaseDate`: `LocalDate`
  - `actualReleaseDate`: `LocalDate`
  - `ownerId`: `Long`
  - `releaseNotes`: `String`
  - *...and 7 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

### `API-243` — POST `/api/v1/releases/{releaseId}/tasks/{taskId}`

- **Method Name:** `assignTaskToRelease`
- **Summary / Purpose:** Assign a task to a release
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ReleaseServiceImplTest)

---

## Controller: `ReportController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.reporting.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/reporting/controller/ReportController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/reporting/controller/ReportController.java)
- **Tag / Domain:** Report Controller (REST endpoints for creating, retrieving, and generating PDF/CSV project reports)
- **Endpoints Count:** 6

### `API-206` — GET `/api/v1/projects/{projectId}/reports`

- **Method Name:** `getProjectReports`
- **Summary / Purpose:** Get all saved report templates for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SavedReportResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SavedReportResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `reportType`: `ReportType`
  - `format`: `ReportFormat`
  - `parameters`: `String`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-244` — POST `/api/v1/reports`

- **Method Name:** `createReport`
- **Summary / Purpose:** Create a saved report template
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedReportResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateReportRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `name`: `String` [@NotBlank(message = "Report name is required"), @Size(min = 2, max = 150, message = "Report name must be between 2 and 150 characters")]
    - `reportType`: `ReportType`
    - `format`: `ReportFormat`
    - `parameters`: `String`
- **Response Model Fields (`ResponseEntity<SavedReportResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `reportType`: `ReportType`
  - `format`: `ReportFormat`
  - `parameters`: `String`
  - `createdBy`: `Long`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-249` — POST `/api/v1/reports/{reportId}/generate`

- **Method Name:** `generateReport`
- **Summary / Purpose:** Generate/export a report instantly
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReportExecutionResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReportExecutionResponse>`):**
  - `id`: `Long`
  - `reportId`: `Long`
  - `executedAt`: `LocalDateTime`
  - `status`: `String`
  - `downloadUrl`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-585` — GET `/platform/reports`

- **Method Name:** `getAllReports`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ReportResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ReportResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - `filters`: `String`
  - `generatedBy`: `String`
  - `generatedAt`: `Instant`
  - `format`: `String`
  - `downloadUrl`: `String`
  - `status`: `String`
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-586` — POST `/platform/reports`

- **Method Name:** `generateReport`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReportResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `GenerateReportRequest`
  - Fields:
    - `name`: `String`
    - `reportType`: `String`
    - `filters`: `String`
    - `generatedBy`: `String`
    - `format`: `String`
    - `name`: `String`
    - `reportType`: `String`
    - `filters`: `String`
    - `generatedBy`: `String`
    - `format`: `String`
- **Response Model Fields (`ResponseEntity<ReportResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - `filters`: `String`
  - `generatedBy`: `String`
  - `generatedAt`: `Instant`
  - `format`: `String`
  - `downloadUrl`: `String`
  - `status`: `String`
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-587` — GET `/platform/reports/{id}`

- **Method Name:** `getReport`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ReportResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ReportResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - `filters`: `String`
  - `generatedBy`: `String`
  - `generatedAt`: `Instant`
  - `format`: `String`
  - `downloadUrl`: `String`
  - `status`: `String`
  - `id`: `Long`
  - `name`: `String`
  - `reportType`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `ResourceAllocationController`

- **Package:** `org.SprintForge.modules.workspace.project.insights.resource.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/insights/resource/controller/ResourceAllocationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/insights/resource/controller/ResourceAllocationController.java)
- **Tag / Domain:** Resource Allocation Controller (REST endpoints for enterprise capacity planning, workload utilization, and bottleneck detection)
- **Endpoints Count:** 3

### `API-160` — GET `/api/v1/projects/{projectId}/capacity`

- **Method Name:** `getCapacityPlanning`
- **Summary / Purpose:** Get capacity planning and net capacity margin for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<CapacityPlanningResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<CapacityPlanningResponse>`):**
  - `projectId`: `Long`
  - `availableCapacityHours`: `double`
  - `requiredCapacityHours`: `double`
  - `netCapacityMarginHours`: `double`
  - `capacityStatus`: `String`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-207` — GET `/api/v1/projects/{projectId}/resources`

- **Method Name:** `getResourceAllocation`
- **Summary / Purpose:** Get resource allocation and role hours breakdown for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ResourceAllocationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<ResourceAllocationResponse>`):**
  - `projectId`: `Long`
  - `totalMembers`: `long`
  - `totalAssignedHours`: `double`
  - `totalCapacityHours`: `double`
  - `overallUtilizationPercentage`: `double`
  - `roleAllocationsHours`: `Map<String, Double>`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-225` — GET `/api/v1/projects/{projectId}/utilization`

- **Method Name:** `getUtilization`
- **Summary / Purpose:** Get team utilization metrics and overallocation counts for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UtilizationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UtilizationResponse>`):**
  - `projectId`: `Long`
  - `overallocatedMembersCount`: `long`
  - `underutilizedMembersCount`: `long`
  - `optimalMembersCount`: `long`
  - `averageUtilizationPercentage`: `double`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `RiskController`

- **Package:** `org.SprintForge.modules.workspace.project.governance.risk.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/governance/risk/controller/RiskController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/governance/risk/controller/RiskController.java)
- **Tag / Domain:** Risk Controller (REST endpoints for managing project risk register and mitigation plans)
- **Endpoints Count:** 8

### `API-209` — GET `/api/v1/projects/{projectId}/risks`

- **Method Name:** `getRisks`
- **Summary / Purpose:** Get all risks for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<RiskResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<RiskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-210` — POST `/api/v1/projects/{projectId}/risks`

- **Method Name:** `createRisk`
- **Summary / Purpose:** Create a project risk
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RiskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RiskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-250` — DELETE `/api/v1/risks/{riskId}`

- **Method Name:** `deleteRisk`
- **Summary / Purpose:** Delete / archive a risk
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-251` — GET `/api/v1/risks/{riskId}`

- **Method Name:** `getRisk`
- **Summary / Purpose:** Get risk details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RiskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RiskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-252` — PATCH `/api/v1/risks/{riskId}`

- **Method Name:** `updateRisk`
- **Summary / Purpose:** Update risk details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RiskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RiskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-253` — POST `/api/v1/risks/{riskId}/mitigation`

- **Method Name:** `createMitigationTask`
- **Summary / Purpose:** Generate a mitigation task for a risk
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-254` — POST `/api/v1/risks/{riskId}/reopen`

- **Method Name:** `reopenRisk`
- **Summary / Purpose:** Reopen a risk
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RiskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RiskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

### `API-255` — POST `/api/v1/risks/{riskId}/resolve`

- **Method Name:** `resolveRisk`
- **Summary / Purpose:** Resolve a risk
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RiskResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RiskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `category`: `RiskCategory`
  - `status`: `RiskStatus`
  - `probability`: `RiskProbability`
  - `impact`: `RiskImpact`
  - `severity`: `String`
  - `riskScore`: `Integer`
  - `ownerId`: `Long`
  - `identifiedDate`: `LocalDate`
  - *...and 9 additional fields*
- **Frontend Destination:** Project Governance Suite (/projects/{id}/governance)
- **UI Trigger / Interaction:** Log project risks with impact matrix, create architectural decisions, request change approvals
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (RiskServiceImplTest)

---

## Controller: `RoadmapController`

- **Package:** `org.SprintForge.modules.workspace.project.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/controller/RoadmapController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/controller/RoadmapController.java)
- **Tag / Domain:** Roadmap Controller (REST endpoints for project roadmaps, quarterly views, and releases)
- **Endpoints Count:** 3

### `API-147` — GET `/api/v1/projects/{id}/roadmap`

- **Method Name:** `getProjectRoadmap`
- **Summary / Purpose:** Get project roadmap view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RoadmapResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RoadmapResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `epics`: `List<RoadmapEpicItem>`
  - `sprints`: `List<RoadmapSprintItem>`
  - `id`: `Long`
  - `name`: `String`
  - `status`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `progressPercentage`: `Double`
  - `id`: `Long`
  - `name`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Gantt & Roadmap View (/projects/{id}/roadmap)
- **UI Trigger / Interaction:** Interactive milestone timeline showing project progress and dependencies
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-148` — POST `/api/v1/projects/{id}/roadmap/releases`

- **Method Name:** `createRelease`
- **Summary / Purpose:** Create release plan for project roadmap
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RoadmapResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ReleasePlanRequest`
  - Fields:
    - `releaseVersion`: `String` [@NotBlank(message = "Release version is required")]
    - `releaseName`: `String`
    - `releaseDate`: `LocalDate`
- **Response Model Fields (`ResponseEntity<RoadmapResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `epics`: `List<RoadmapEpicItem>`
  - `sprints`: `List<RoadmapSprintItem>`
  - `id`: `Long`
  - `name`: `String`
  - `status`: `String`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `progressPercentage`: `Double`
  - `id`: `Long`
  - `name`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Releases & Version Tracker (/projects/{id}/releases)
- **UI Trigger / Interaction:** Release manager creates release bundles, tracks release readiness
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-211` — GET `/api/v1/projects/{projectId}/roadmap`

- **Method Name:** `getProjectRoadmap`
- **Summary / Purpose:** Get strategic project roadmap
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<StrategicRoadmapResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<StrategicRoadmapResponse>`):**
  - `projectId`: `Long`
  - `projectName`: `String`
  - `timeframe`: `String`
  - `viewMode`: `String`
  - `items`: `List<RoadmapItemResponse>`
- **Frontend Destination:** Gantt & Roadmap View (/projects/{id}/roadmap)
- **UI Trigger / Interaction:** Interactive milestone timeline showing project progress and dependencies
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SavedViewController`

- **Package:** `org.SprintForge.modules.workspace.bookmark.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/bookmark/controller/SavedViewController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/bookmark/controller/SavedViewController.java)
- **Tag / Domain:** Saved View Controller (REST endpoints for managing saved task filters, layouts, and views)
- **Endpoints Count:** 8

### `API-226` — GET `/api/v1/projects/{projectId}/views`

- **Method Name:** `getProjectViews`
- **Summary / Purpose:** Get accessible saved views for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SavedViewSummaryResponse>>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SavedViewSummaryResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `viewType`: `String`
  - `isDefault`: `Boolean`
  - `isShared`: `Boolean`
  - `isFavorite`: `Boolean`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-227` — POST `/api/v1/projects/{projectId}/views`

- **Method Name:** `createView`
- **Summary / Purpose:** Create saved view for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateSavedViewRequest`
  - Fields:
    - `workspaceId`: `Long`
    - `projectId`: `Long`
    - `name`: `String` [@NotBlank(message = "View name is required")]
    - `description`: `String`
    - `viewType`: `String`
    - `filters`: `String`
    - `sorting`: `String`
    - `grouping`: `String`
    - `columns`: `String`
    - `layout`: `String`
    - `visibility`: `String`
    - `isDefault`: `Boolean`
    - `isShared`: `Boolean`
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-398` — DELETE `/api/v1/views/{id}`

- **Method Name:** `deleteView`
- **Summary / Purpose:** Delete saved view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-399` — GET `/api/v1/views/{id}`

- **Method Name:** `getViewById`
- **Summary / Purpose:** Get saved view by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-400` — PATCH `/api/v1/views/{id}`

- **Method Name:** `updateView`
- **Summary / Purpose:** Update saved view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateSavedViewRequest`
  - Fields:
    - `name`: `String`
    - `description`: `String`
    - `viewType`: `String`
    - `filters`: `String`
    - `sorting`: `String`
    - `grouping`: `String`
    - `columns`: `String`
    - `layout`: `String`
    - `visibility`: `String`
    - `isDefault`: `Boolean`
    - `isShared`: `Boolean`
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-401` — POST `/api/v1/views/{id}/apply`

- **Method Name:** `applyView`
- **Summary / Purpose:** Apply saved view to retrieve configured task state
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-402` — POST `/api/v1/views/{id}/favorite`

- **Method Name:** `favoriteView`
- **Summary / Purpose:** Toggle favorite status on saved view
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-403` — POST `/api/v1/views/{id}/share`

- **Method Name:** `shareView`
- **Summary / Purpose:** Share saved view with project or workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SavedViewResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ShareSavedViewRequest`
  - Fields:
    - `isShared`: `Boolean` [@NotNull(message = "isShared is required")]
    - `visibility`: `String`
- **Response Model Fields (`ResponseEntity<SavedViewResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `userId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `viewType`: `String`
  - `filters`: `String`
  - `sorting`: `String`
  - `grouping`: `String`
  - `columns`: `String`
  - `layout`: `String`
  - *...and 6 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SchedulerController`

- **Package:** `org.SprintForge.platform.scheduler.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/scheduler/controller/SchedulerController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/scheduler/controller/SchedulerController.java)
- **Tag / Domain:** SchedulerController (N/A)
- **Endpoints Count:** 4

### `API-591` — GET `/scheduler/jobs`

- **Method Name:** `getAllScheduledJobs`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<ScheduledJob>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<ScheduledJob>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `cronExpression`: `String`
  - `nextExecution`: `Instant`
  - `lastExecution`: `Instant`
  - `jobType`: `JobType`
  - `payload`: `String`
  - `createdBy`: `String`
  - `id`: `Long`
  - `name`: `String`
  - `cronExpression`: `String`
  - `nextExecution`: `Instant`
  - *...and 4 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-592` — POST `/scheduler/jobs`

- **Method Name:** `createScheduledJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ScheduledJob>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ScheduleJobRequest`
  - Fields:
    - `name`: `String`
    - `cronExpression`: `String`
    - `jobType`: `JobType`
    - `payload`: `String`
    - `createdBy`: `String`
    - `scheduledAt`: `Instant`
    - `name`: `String`
    - `cronExpression`: `String`
    - `jobType`: `JobType`
    - `payload`: `String`
    - `createdBy`: `String`
    - `scheduledAt`: `Instant`
- **Response Model Fields (`ResponseEntity<ScheduledJob>`):**
  - `id`: `Long`
  - `name`: `String`
  - `cronExpression`: `String`
  - `nextExecution`: `Instant`
  - `lastExecution`: `Instant`
  - `jobType`: `JobType`
  - `payload`: `String`
  - `createdBy`: `String`
  - `id`: `Long`
  - `name`: `String`
  - `cronExpression`: `String`
  - `nextExecution`: `Instant`
  - *...and 4 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-593` — POST `/scheduler/jobs/one-time`

- **Method Name:** `scheduleOneTimeJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<JobResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ScheduleJobRequest`
  - Fields:
    - `name`: `String`
    - `cronExpression`: `String`
    - `jobType`: `JobType`
    - `payload`: `String`
    - `createdBy`: `String`
    - `scheduledAt`: `Instant`
    - `name`: `String`
    - `cronExpression`: `String`
    - `jobType`: `JobType`
    - `payload`: `String`
    - `createdBy`: `String`
    - `scheduledAt`: `Instant`
- **Response Model Fields (`ResponseEntity<JobResponse>`):**
  - `id`: `Long`
  - `jobType`: `JobType`
  - `jobName`: `String`
  - `priority`: `JobPriority`
  - `status`: `JobStatus`
  - `payload`: `String`
  - `result`: `String`
  - `createdBy`: `String`
  - `workspaceId`: `String`
  - `projectId`: `String`
  - `scheduledAt`: `Instant`
  - `startedAt`: `Instant`
  - *...and 26 additional fields*
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

### `API-594` — DELETE `/scheduler/jobs/{id}`

- **Method Name:** `deleteScheduledJob`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category E (Background processing or asynchronous operations)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Platform Administration -> Background Jobs & Queues (/admin/scheduler)
- **UI Trigger / Interaction:** System monitor displays scheduled job status, retry failed jobs, triggers ad-hoc jobs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (SecurityAuthorizationIntegrationTest)

---

## Controller: `SearchDashboardController`

- **Package:** `org.SprintForge.platform.search.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/search/controller/SearchDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/search/controller/SearchDashboardController.java)
- **Tag / Domain:** SearchDashboardController (N/A)
- **Endpoints Count:** 1

### `API-588` — GET `/platform/search/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SearchDashboardResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SearchDashboardResponse>`):**
  - `totalIndexedEntities`: `long`
  - `pendingReindexJobs`: `long`
  - `totalSearchesExecuted`: `long`
  - `averageSearchTimeMs`: `double`
  - `indexedEntitiesByType`: `Map<String, Long>`
  - `popularSearches`: `List<String>`
  - `totalIndexedEntities`: `long`
  - `pendingReindexJobs`: `long`
  - `totalSearchesExecuted`: `long`
  - `averageSearchTimeMs`: `double`
  - `indexedEntitiesByType`: `Map<String, Long>`
  - `popularSearches`: `List<String>`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SearchIndexController`

- **Package:** `org.SprintForge.platform.search.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/search/controller/SearchIndexController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/search/controller/SearchIndexController.java)
- **Tag / Domain:** SearchIndexController (N/A)
- **Endpoints Count:** 3

### `API-600` — GET `/search/index/status`

- **Method Name:** `getIndexStatus`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, String>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-602` — POST `/search/reindex`

- **Method Name:** `reindexAll`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-603` — POST `/search/reindex/{entity}`

- **Method Name:** `reindexEntity`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SecretController`

- **Package:** `org.SprintForge.modules.workflow.secret`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/secret/SecretController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/secret/SecretController.java)
- **Tag / Domain:** SecretController (N/A)
- **Endpoints Count:** 3

### `API-626` — GET `/workflow/secrets`

- **Method Name:** `getSecrets`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkflowSecret>>`
- **Path Variables:** None
- **Query Parameters:** `workspaceId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkflowSecret>>`):**
  - `workspaceId`: `Long`
  - `name`: `String`
  - `valueEncrypted`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-627` — POST `/workflow/secrets`

- **Method Name:** `createSecret`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowSecret>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateSecretRequest`
- **Response Model Fields (`ResponseEntity<WorkflowSecret>`):**
  - `workspaceId`: `Long`
  - `name`: `String`
  - `valueEncrypted`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-628` — DELETE `/workflow/secrets/{id}`

- **Method Name:** `deleteSecret`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SimulationController`

- **Package:** `org.SprintForge.modules.workflow.simulation`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/simulation/SimulationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/simulation/SimulationController.java)
- **Tag / Domain:** SimulationController (N/A)
- **Endpoints Count:** 5

### `API-617` — POST `/workflow/debug/{executionId}/pause`

- **Method Name:** `pauseExecution`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `executionId` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-618` — POST `/workflow/debug/{executionId}/resume`

- **Method Name:** `resumeExecution`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `executionId` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-629` — POST `/workflow/simulations`

- **Method Name:** `runSimulation`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SimulationResult>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `SimulationContext`
  - Fields:
    - `simulationId`: `String`
    - `workflowVersionId`: `Long`
    - `dryRun`: `boolean`
    - `stepByStep`: `boolean`
- **Response Model Fields (`ResponseEntity<SimulationResult>`):**
  - `simulationId`: `String`
  - `status`: `String`
  - `totalDurationMillis`: `long`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-630` — GET `/workflow/simulations/{id}`

- **Method Name:** `getSimulationStatus`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SimulationResult>`
- **Path Variables:** `id` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SimulationResult>`):**
  - `simulationId`: `String`
  - `status`: `String`
  - `totalDurationMillis`: `long`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-631` — POST `/workflow/simulations/{id}/replay`

- **Method Name:** `replaySimulation`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SimulationResult>`
- **Path Variables:** `id` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SimulationResult>`):**
  - `simulationId`: `String`
  - `status`: `String`
  - `totalDurationMillis`: `long`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `SprintController`

- **Package:** `org.SprintForge.modules.workspace.sprint.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/sprint/controller/SprintController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/sprint/controller/SprintController.java)
- **Tag / Domain:** Sprint Controller (REST endpoints for managing sprints lifecycle and planning workflows)
- **Endpoints Count:** 13

### `API-217` — GET `/api/v1/projects/{projectId}/sprints`

- **Method Name:** `getProjectSprints`
- **Summary / Purpose:** Get sprints for a project (optionally filtered by status)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SprintResponse>>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SprintResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-218` — POST `/api/v1/projects/{projectId}/sprints`

- **Method Name:** `createSprint`
- **Summary / Purpose:** Create a new sprint for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `SprintCreateRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `name`: `String` [@NotBlank(message = "Sprint name is required"), @Size(min = 2, max = 100, message = "Sprint name must be between 2 and 100 characters")]
    - `goal`: `String` [@Size(max = 1000, message = "Goal must not exceed 1000 characters")]
    - `startDate`: `LocalDate`
    - `endDate`: `LocalDate`
    - `plannedStoryPoints`: `Integer`
    - `capacity`: `Double`
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-219` — GET `/api/v1/projects/{projectId}/sprints/active`

- **Method Name:** `getActiveSprint`
- **Summary / Purpose:** Get the active sprint for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-258` — DELETE `/api/v1/sprints/{id}`

- **Method Name:** `deleteSprint`
- **Summary / Purpose:** Delete a PLANNED or CANCELLED sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-259` — GET `/api/v1/sprints/{id}`

- **Method Name:** `getSprint`
- **Summary / Purpose:** Get sprint by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-260` — PATCH `/api/v1/sprints/{id}`

- **Method Name:** `updateSprint`
- **Summary / Purpose:** Update sprint details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `SprintUpdateRequest`
  - Fields:
    - `name`: `String` [@Size(min = 2, max = 100, message = "Sprint name must be between 2 and 100 characters")]
    - `goal`: `String` [@Size(max = 1000, message = "Goal must not exceed 1000 characters")]
    - `startDate`: `LocalDate`
    - `endDate`: `LocalDate`
    - `plannedStoryPoints`: `Integer`
    - `completedStoryPoints`: `Integer`
    - `capacity`: `Double`
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-261` — POST `/api/v1/sprints/{id}/archive`

- **Method Name:** `archiveSprint`
- **Summary / Purpose:** Archive a sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-262` — GET `/api/v1/sprints/{id}/burndown`

- **Method Name:** `getSprintBurndown`
- **Summary / Purpose:** Get sprint burndown and burnup chart data
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintBurndownResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintBurndownResponse>`):**
  - `sprintId`: `Long`
  - `sprintName`: `String`
  - `totalPlannedStoryPoints`: `Integer`
  - `burndownData`: `List<BurndownPoint>`
  - `burnupData`: `List<BurndownPoint>`
  - `date`: `LocalDate`
  - `idealRemaining`: `Double`
  - `actualRemaining`: `Double`
  - `completedToDate`: `Double`
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-263` — POST `/api/v1/sprints/{id}/clone`

- **Method Name:** `cloneSprint`
- **Summary / Purpose:** Clone a sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-264` — POST `/api/v1/sprints/{id}/complete`

- **Method Name:** `completeSprint`
- **Summary / Purpose:** Complete a sprint (transition ACTIVE -> COMPLETED)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-265` — GET `/api/v1/sprints/{id}/detail`

- **Method Name:** `getSprintDetail`
- **Summary / Purpose:** Get sprint details by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintDetailResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<SprintDetailResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `projectName`: `String`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - *...and 10 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-266` — POST `/api/v1/sprints/{id}/start`

- **Method Name:** `startSprint`
- **Summary / Purpose:** Start a sprint (transition PLANNED -> ACTIVE)
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-267` — POST `/api/v1/sprints/{id}/tasks`

- **Method Name:** `moveTasksToSprint`
- **Summary / Purpose:** Bulk move tasks into a sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SprintResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `List<Long>`
- **Response Model Fields (`ResponseEntity<SprintResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `goal`: `String`
  - `status`: `SprintStatus`
  - `startDate`: `LocalDate`
  - `endDate`: `LocalDate`
  - `completedAt`: `LocalDateTime`
  - `plannedStoryPoints`: `Integer`
  - `completedStoryPoints`: `Integer`
  - `velocity`: `Double`
  - `capacity`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Sprint Planning & Active Sprint Board (/projects/{id}/sprints)
- **UI Trigger / Interaction:** Scrum master creates sprint, moves backlog items, starts/completes sprint
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `StorageDashboardController`

- **Package:** `org.SprintForge.platform.storage.controller`
- **Source File:** [`src/main/java/org/SprintForge/platform/storage/controller/StorageDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/platform/storage/controller/StorageDashboardController.java)
- **Tag / Domain:** StorageDashboardController (N/A)
- **Endpoints Count:** 1

### `API-589` — GET `/platform/storage/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<StorageDashboardResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<StorageDashboardResponse>`):**
  - `totalFiles`: `long`
  - `totalStorageUsedBytes`: `long`
  - `duplicateFilesCount`: `long`
  - `uploadSuccessRate`: `double`
  - `largestFiles`: `List<StoredFileResponse>`
  - `totalFiles`: `long`
  - `totalStorageUsedBytes`: `long`
  - `duplicateFilesCount`: `long`
  - `uploadSuccessRate`: `double`
  - `largestFiles`: `List<StoredFileResponse>`
- **Frontend Destination:** Storage Platform & File Asset Manager (/assets)
- **UI Trigger / Interaction:** Upload project assets, view storage quota usage, preview binary documents
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskAdminController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskAdminController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskAdminController.java)
- **Tag / Domain:** Task Administration Controller (REST endpoints for system maintenance, indexing, and governance operations)
- **Endpoints Count:** 5

### `API-021` — POST `/api/v1/admin/tasks/archive-old`

- **Method Name:** `archiveOldTasks`
- **Summary / Purpose:** Archive tasks completed older than N days
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Admin -> Task Governance (/admin/tasks)
- **UI Trigger / Interaction:** System Administrator forces task deletion or runs administrative audit
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-022` — POST `/api/v1/admin/tasks/cleanup`

- **Method Name:** `cleanupTrash`
- **Summary / Purpose:** Cleanup expired trash items
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Admin -> Task Governance (/admin/tasks)
- **UI Trigger / Interaction:** System Administrator forces task deletion or runs administrative audit
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-023` — POST `/api/v1/admin/tasks/health-check`

- **Method Name:** `runHealthCheck`
- **Summary / Purpose:** Run administrative health check
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Admin -> Task Governance (/admin/tasks)
- **UI Trigger / Interaction:** System Administrator forces task deletion or runs administrative audit
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-024` — POST `/api/v1/admin/tasks/recalculate`

- **Method Name:** `recalculateMetrics`
- **Summary / Purpose:** Recalculate story points and metrics
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Admin -> Task Governance (/admin/tasks)
- **UI Trigger / Interaction:** System Administrator forces task deletion or runs administrative audit
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-025` — POST `/api/v1/admin/tasks/reindex`

- **Method Name:** `reindexTasks`
- **Summary / Purpose:** Reindex all tasks in search engine
- **Description:** N/A
- **Category:** Category B (Administrative)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Admin -> Task Governance (/admin/tasks)
- **UI Trigger / Interaction:** System Administrator forces task deletion or runs administrative audit
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskController.java)
- **Tag / Domain:** Task Controller (REST endpoints for managing tasks lifecycle, queries and workflow)
- **Endpoints Count:** 39

### `API-107` — DELETE `/api/v1/labels/{labelId}`

- **Method Name:** `deleteLabel`
- **Summary / Purpose:** Delete a label
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `labelId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-108` — PATCH `/api/v1/labels/{labelId}`

- **Method Name:** `updateLabel`
- **Summary / Purpose:** Update a label
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<LabelResponse>`
- **Path Variables:** `labelId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateLabelRequest`
  - Fields:
    - `name`: `String` [@Size(min = 2, max = 50, message = "Label name must be between 2 and 50 characters")]
    - `color`: `String` [@Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Label color must be a valid hex color code (e.g. #FF5733)")]
    - `description`: `String` [@Size(max = 255, message = "Description must not exceed 255 characters")]
- **Response Model Fields (`ResponseEntity<LabelResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
  - `archived`: `Boolean`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-109` — GET `/api/v1/labels/{labelId}/tasks`

- **Method Name:** `getTasksByLabel`
- **Summary / Purpose:** Get tasks containing a label
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `labelId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-118` — GET `/api/v1/members/{projectMemberId}/tasks`

- **Method Name:** `getMemberTasks`
- **Summary / Purpose:** Get tasks assigned to a project member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `projectMemberId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-184` — GET `/api/v1/projects/{projectId}/labels`

- **Method Name:** `getLabels`
- **Summary / Purpose:** Get all labels of a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<LabelResponse>>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<LabelResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
  - `archived`: `Boolean`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-185` — POST `/api/v1/projects/{projectId}/labels`

- **Method Name:** `createLabel`
- **Summary / Purpose:** Create a project label
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<LabelResponse>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateLabelRequest`
  - Fields:
    - `name`: `String` [@NotBlank(message = "Label name is required"), @Size(min = 2, max = 50, message = "Label name must be between 2 and 50 characters")]
    - `color`: `String` [@NotBlank(message = "Label color is required"), @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Label color must be a valid hex color code (e.g. #FF5733)")]
    - `description`: `String` [@Size(max = 255, message = "Description must not exceed 255 characters")]
- **Response Model Fields (`ResponseEntity<LabelResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
  - `archived`: `Boolean`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-222` — GET `/api/v1/projects/{projectId}/tasks`

- **Method Name:** `getProjectTasks`
- **Summary / Purpose:** Get tasks for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-223` — POST `/api/v1/projects/{projectId}/tasks`

- **Method Name:** `createTask`
- **Summary / Purpose:** Create a new task for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `projectId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTaskRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `sprintId`: `Long`
    - `parentTaskId`: `Long`
    - `title`: `String` [@NotBlank(message = "Task title is required"), @Size(min = 2, max = 255, message = "Task title must be between 2 and 255 characters")]
    - `description`: `String` [@Size(max = 10000, message = "Description must not exceed 10000 characters")]
    - `status`: `TaskStatus`
    - `priority`: `TaskPriority`
    - `type`: `TaskType`
    - `dueDate`: `LocalDateTime`
    - `estimatedHours`: `Double`
    - `storyPoints`: `Integer`
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-313` — DELETE `/api/v1/tasks/{taskId}`

- **Method Name:** `deleteTask`
- **Summary / Purpose:** Delete a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-314` — GET `/api/v1/tasks/{taskId}`

- **Method Name:** `getTask`
- **Summary / Purpose:** Get task by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-315` — PATCH `/api/v1/tasks/{taskId}`

- **Method Name:** `updateTask`
- **Summary / Purpose:** Update task details
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateTaskRequest`
  - Fields:
    - `title`: `String` [@Size(min = 2, max = 255, message = "Task title must be between 2 and 255 characters")]
    - `description`: `String` [@Size(max = 10000, message = "Description must not exceed 10000 characters")]
    - `status`: `TaskStatus`
    - `priority`: `TaskPriority`
    - `type`: `TaskType`
    - `dueDate`: `LocalDateTime`
    - `estimatedHours`: `Double`
    - `actualHours`: `Double`
    - `storyPoints`: `Integer`
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-317` — GET `/api/v1/tasks/{taskId}/allowed-transitions`

- **Method Name:** `getAllowedTransitions`
- **Summary / Purpose:** Get allowed transitions for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AllowedTransitionsResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<AllowedTransitionsResponse>`):**
  - `taskId`: `Long`
  - `allowedTransitions`: `List<TaskStatus>`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-318` — POST `/api/v1/tasks/{taskId}/archive`

- **Method Name:** `archiveTask`
- **Summary / Purpose:** Archive a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-319` — POST `/api/v1/tasks/{taskId}/assign`

- **Method Name:** `assignMember`
- **Summary / Purpose:** Assign a project member to a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskAssignmentResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AssignTaskRequest`
  - Fields:
    - `projectMemberId`: `Long` [@NotNull(message = "Project member ID is required")]
- **Response Model Fields (`ResponseEntity<TaskAssignmentResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `projectMemberId`: `Long`
  - `assignedBy`: `Long`
  - `assignedAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-320` — POST `/api/v1/tasks/{taskId}/assign/bulk`

- **Method Name:** `assignMembers`
- **Summary / Purpose:** Bulk assign project members to a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskAssignmentResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `be`
- **Response Model Fields (`ResponseEntity<List<TaskAssignmentResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `projectMemberId`: `Long`
  - `assignedBy`: `Long`
  - `assignedAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-321` — GET `/api/v1/tasks/{taskId}/assignees`

- **Method Name:** `getTaskAssignees`
- **Summary / Purpose:** Get assignees of a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskAssigneeResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskAssigneeResponse>>`):**
  - `projectMemberId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `roleName`: `String`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-322` — DELETE `/api/v1/tasks/{taskId}/assignees/{projectMemberId}`

- **Method Name:** `unassignMember`
- **Summary / Purpose:** Unassign a project member from a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long), `projectMemberId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-325` — GET `/api/v1/tasks/{taskId}/blocking`

- **Method Name:** `getBlockingTasks`
- **Summary / Purpose:** Get blocking tasks for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-326` — POST `/api/v1/tasks/{taskId}/cancel`

- **Method Name:** `cancelTask`
- **Summary / Purpose:** Cancel task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-331` — POST `/api/v1/tasks/{taskId}/complete`

- **Method Name:** `completeTask`
- **Summary / Purpose:** Complete task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-334` — GET `/api/v1/tasks/{taskId}/dependencies`

- **Method Name:** `getDependencies`
- **Summary / Purpose:** Get task dependencies
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskDependencyResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskDependencyResponse>>`):**
  - `id`: `Long`
  - `predecessorTaskId`: `Long`
  - `successorTaskId`: `Long`
  - `type`: `TaskDependencyType`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-335` — POST `/api/v1/tasks/{taskId}/dependencies`

- **Method Name:** `addDependency`
- **Summary / Purpose:** Add a task dependency
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskDependencyResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTaskDependencyRequest`
  - Fields:
    - `predecessorTaskId`: `Long` [@NotNull(message = "Predecessor task ID is required")]
    - `successorTaskId`: `Long` [@NotNull(message = "Successor task ID is required")]
    - `type`: `TaskDependencyType` [@NotNull(message = "Dependency type is required")]
- **Response Model Fields (`ResponseEntity<TaskDependencyResponse>`):**
  - `id`: `Long`
  - `predecessorTaskId`: `Long`
  - `successorTaskId`: `Long`
  - `type`: `TaskDependencyType`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-336` — DELETE `/api/v1/tasks/{taskId}/dependencies/{dependencyId}`

- **Method Name:** `removeDependency`
- **Summary / Purpose:** Remove a task dependency
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long), `dependencyId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-337` — GET `/api/v1/tasks/{taskId}/dependents`

- **Method Name:** `getDependentTasks`
- **Summary / Purpose:** Get dependent tasks for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-338` — GET `/api/v1/tasks/{taskId}/hierarchy`

- **Method Name:** `getTaskHierarchy`
- **Summary / Purpose:** Get task hierarchy tree
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskHierarchyResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskHierarchyResponse>`):**
  - `task`: `TaskResponse`
  - `children`: `List<TaskHierarchyResponse>`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-341` — GET `/api/v1/tasks/{taskId}/labels`

- **Method Name:** `getTaskLabels`
- **Summary / Purpose:** Get labels assigned to a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<LabelResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<LabelResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `description`: `String`
  - `archived`: `Boolean`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-342` — DELETE `/api/v1/tasks/{taskId}/labels/{labelId}`

- **Method Name:** `removeLabel`
- **Summary / Purpose:** Remove a label from a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long), `labelId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-343` — POST `/api/v1/tasks/{taskId}/labels/{labelId}`

- **Method Name:** `assignLabel`
- **Summary / Purpose:** Assign a label to a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long), `labelId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-344` — POST `/api/v1/tasks/{taskId}/move`

- **Method Name:** `moveTask`
- **Summary / Purpose:** Move task to a sprint
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `MoveTaskRequest`
  - Fields:
    - `sprintId`: `Long`
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-345` — DELETE `/api/v1/tasks/{taskId}/parent`

- **Method Name:** `removeParent`
- **Summary / Purpose:** Remove parent relationship from a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-346` — PATCH `/api/v1/tasks/{taskId}/parent`

- **Method Name:** `moveSubtask`
- **Summary / Purpose:** Change parent task of a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SubtaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `MoveSubtaskRequest`
  - Fields:
    - `parentTaskId`: `Long`
- **Response Model Fields (`ResponseEntity<SubtaskResponse>`):**
  - `id`: `Long`
  - `parentTaskId`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-347` — PUT `/api/v1/tasks/{taskId}/reassign`

- **Method Name:** `reassignTask`
- **Summary / Purpose:** Reassign task assignees
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskAssignmentResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ReassignTaskRequest`
  - Fields:
    - `projectMemberIds`: `List<Long>` [@NotEmpty(message = "Project member IDs list cannot be empty")]
- **Response Model Fields (`ResponseEntity<List<TaskAssignmentResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `projectMemberId`: `Long`
  - `assignedBy`: `Long`
  - `assignedAt`: `LocalDateTime`
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-348` — POST `/api/v1/tasks/{taskId}/reopen`

- **Method Name:** `reopenTask`
- **Summary / Purpose:** Reopen task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-349` — POST `/api/v1/tasks/{taskId}/restore`

- **Method Name:** `restoreTask`
- **Summary / Purpose:** Restore an archived task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-350` — POST `/api/v1/tasks/{taskId}/review`

- **Method Name:** `sendForReview`
- **Summary / Purpose:** Submit task for review
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-351` — POST `/api/v1/tasks/{taskId}/start`

- **Method Name:** `startTask`
- **Summary / Purpose:** Start progress on a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-352` — PATCH `/api/v1/tasks/{taskId}/status`

- **Method Name:** `changeStatus`
- **Summary / Purpose:** Change task status
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ChangeTaskStatusRequest`
  - Fields:
    - `status`: `TaskStatus` [@NotNull(message = "Status is required")]
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-353` — GET `/api/v1/tasks/{taskId}/subtasks`

- **Method Name:** `getSubtasks`
- **Summary / Purpose:** Get direct subtasks of a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<SubtaskResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<SubtaskResponse>>`):**
  - `id`: `Long`
  - `parentTaskId`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

### `API-354` — POST `/api/v1/tasks/{taskId}/subtasks`

- **Method Name:** `createSubtask`
- **Summary / Purpose:** Create a subtask
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<SubtaskResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateSubtaskRequest`
  - Fields:
    - `title`: `String` [@NotBlank(message = "Task title is required"), @Size(min = 2, max = 255, message = "Task title must be between 2 and 255 characters")]
    - `description`: `String` [@Size(max = 10000, message = "Description must not exceed 10000 characters")]
    - `status`: `TaskStatus`
    - `priority`: `TaskPriority`
    - `type`: `TaskType`
    - `dueDate`: `LocalDateTime`
    - `estimatedHours`: `Double`
    - `storyPoints`: `Integer`
- **Response Model Fields (`ResponseEntity<SubtaskResponse>`):**
  - `id`: `Long`
  - `parentTaskId`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 4 additional fields*
- **Frontend Destination:** Task Board & Task Details Drawer (/projects/{id}/board, /tasks/{id})
- **UI Trigger / Interaction:** Card drag-and-drop, inline field editing, status changes, task creation
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TaskServiceTest, TaskHistoryServiceTest, TaskWatcherServiceImplTest)

---

## Controller: `TaskHealthController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskHealthController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskHealthController.java)
- **Tag / Domain:** Task Health Controller (REST endpoints for task health scoring, stale detection, and compliance governance)
- **Endpoints Count:** 4

### `API-288` — GET `/api/v1/tasks/non-compliant`

- **Method Name:** `getNonCompliantTasks`
- **Summary / Purpose:** Get non-compliant tasks lacking required fields
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskHealthResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskHealthResponse>>`):**
  - `taskId`: `Long`
  - `healthScore`: `int`
  - `healthStatus`: `String`
  - `isOverdue`: `boolean`
  - `isBlocked`: `boolean`
  - `isStale`: `boolean`
  - `missingAssignee`: `boolean`
  - `missingEstimate`: `boolean`
  - `missingDueDate`: `boolean`
  - `healthWarnings`: `List<String>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-290` — GET `/api/v1/tasks/risk`

- **Method Name:** `getRiskTasks`
- **Summary / Purpose:** Get high-risk tasks requiring intervention
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskHealthResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskHealthResponse>>`):**
  - `taskId`: `Long`
  - `healthScore`: `int`
  - `healthStatus`: `String`
  - `isOverdue`: `boolean`
  - `isBlocked`: `boolean`
  - `isStale`: `boolean`
  - `missingAssignee`: `boolean`
  - `missingEstimate`: `boolean`
  - `missingDueDate`: `boolean`
  - `healthWarnings`: `List<String>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-291` — GET `/api/v1/tasks/stale`

- **Method Name:** `getStaleTasks`
- **Summary / Purpose:** Get stale tasks un-updated for 14+ days
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskHealthResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskHealthResponse>>`):**
  - `taskId`: `Long`
  - `healthScore`: `int`
  - `healthStatus`: `String`
  - `isOverdue`: `boolean`
  - `isBlocked`: `boolean`
  - `isStale`: `boolean`
  - `missingAssignee`: `boolean`
  - `missingEstimate`: `boolean`
  - `missingDueDate`: `boolean`
  - `healthWarnings`: `List<String>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-301` — GET `/api/v1/tasks/{id}/health`

- **Method Name:** `getTaskHealth`
- **Summary / Purpose:** Get task health score and warnings
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskHealthResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskHealthResponse>`):**
  - `taskId`: `Long`
  - `healthScore`: `int`
  - `healthStatus`: `String`
  - `isOverdue`: `boolean`
  - `isBlocked`: `boolean`
  - `isStale`: `boolean`
  - `missingAssignee`: `boolean`
  - `missingEstimate`: `boolean`
  - `missingDueDate`: `boolean`
  - `healthWarnings`: `List<String>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskHistoryController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskHistoryController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskHistoryController.java)
- **Tag / Domain:** Task History Controller (REST endpoints for retrieving task audit logs and history feeds)
- **Endpoints Count:** 3

### `API-316` — GET `/api/v1/tasks/{taskId}/activity`

- **Method Name:** `getRecentActivity`
- **Summary / Purpose:** Get recent activity summary feed for a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskHistorySummaryResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskHistorySummaryResponse>>`):**
  - `id`: `Long`
  - `actionType`: `TaskHistoryActionType`
  - `description`: `String`
  - `performedByName`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Activity & Audit Log Tab
- **UI Trigger / Interaction:** Displays chronological changelog of field modifications and actor updates
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-339` — DELETE `/api/v1/tasks/{taskId}/history`

- **Method Name:** `deleteHistory`
- **Summary / Purpose:** Clear/delete audit history for a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail Drawer -> Activity & Audit Log Tab
- **UI Trigger / Interaction:** Displays chronological changelog of field modifications and actor updates
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-340` — GET `/api/v1/tasks/{taskId}/history`

- **Method Name:** `getTaskHistory`
- **Summary / Purpose:** Get full chronological history for a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskHistoryResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskHistoryResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `performedById`: `Long`
  - `performedByUsername`: `String`
  - `performedByName`: `String`
  - `actionType`: `TaskHistoryActionType`
  - `fieldName`: `String`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `description`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail Drawer -> Activity & Audit Log Tab
- **UI Trigger / Interaction:** Displays chronological changelog of field modifications and actor updates
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskPermissionController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskPermissionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskPermissionController.java)
- **Tag / Domain:** Task Permission Controller (REST endpoints for fine-grained task permission overrides)
- **Endpoints Count:** 3

### `API-302` — GET `/api/v1/tasks/{id}/permissions`

- **Method Name:** `getPermissions`
- **Summary / Purpose:** Get task permission overrides
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskPermissionOverride>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskPermissionOverride>>`):**
  - `taskId`: `Long`
  - `userId`: `Long`
  - `permission`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-303` — PATCH `/api/v1/tasks/{id}/permissions`

- **Method Name:** `setPermission`
- **Summary / Purpose:** Set task permission override for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskPermissionOverride>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None), `permission` (String, required=True, default=None), `allowed` (Boolean, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskPermissionOverride>`):**
  - `taskId`: `Long`
  - `userId`: `Long`
  - `permission`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-304` — DELETE `/api/v1/tasks/{id}/permissions/{overrideId}`

- **Method Name:** `removePermission`
- **Summary / Purpose:** Remove task permission override
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long), `overrideId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskRelationshipController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskRelationshipController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskRelationshipController.java)
- **Tag / Domain:** Task Relationship Controller (REST endpoints for advanced task relationships, dependency graph, and cycle detection)
- **Endpoints Count:** 5

### `API-236` — DELETE `/api/v1/relationships/{id}`

- **Method Name:** `deleteRelationship`
- **Summary / Purpose:** Delete a task relationship
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-296` — GET `/api/v1/tasks/{id}/blocked`

- **Method Name:** `getBlockedTasks`
- **Summary / Purpose:** Get tasks blocked by this task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AdvancedTaskRelationship>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AdvancedTaskRelationship>>`):**
  - `sourceTaskId`: `Long`
  - `targetTaskId`: `Long`
  - `relationshipType`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-297` — GET `/api/v1/tasks/{id}/blocking`

- **Method Name:** `getBlockingTasks`
- **Summary / Purpose:** Get tasks blocking this task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<AdvancedTaskRelationship>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<AdvancedTaskRelationship>>`):**
  - `sourceTaskId`: `Long`
  - `targetTaskId`: `Long`
  - `relationshipType`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-300` — GET `/api/v1/tasks/{id}/graph`

- **Method Name:** `getDependencyGraph`
- **Summary / Purpose:** Get full dependency graph rooted at a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<DependencyGraphResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<DependencyGraphResponse>`):**
  - `rootTaskId`: `Long`
  - `nodes`: `List<GraphNode>`
  - `edges`: `List<GraphEdge>`
  - `taskId`: `Long`
  - `title`: `String`
  - `status`: `String`
  - `sourceTaskId`: `Long`
  - `targetTaskId`: `Long`
  - `relationshipType`: `String`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-307` — POST `/api/v1/tasks/{id}/relationships`

- **Method Name:** `createRelationship`
- **Summary / Purpose:** Create an advanced task relationship
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<AdvancedTaskRelationship>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTaskRelationshipRequest`
  - Fields:
    - `targetTaskId`: `Long` [@NotNull(message = "Target task ID is required")]
    - `relationshipType`: `String` [@NotBlank(message = "Relationship type is required")]
- **Response Model Fields (`ResponseEntity<AdvancedTaskRelationship>`):**
  - `sourceTaskId`: `Long`
  - `targetTaskId`: `Long`
  - `relationshipType`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Supporting UI Widget
- **UI Trigger / Interaction:** Modal, popover, or background data fetch supporting primary views
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskReportController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskReportController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskReportController.java)
- **Tag / Domain:** Task Report Controller (REST endpoints for operational task, workload, and time reports)
- **Endpoints Count:** 4

### `API-245` — GET `/api/v1/reports/overdue`

- **Method Name:** `getOverdueReport`
- **Summary / Purpose:** Get overdue tasks report
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskOperationalReportResponse>`
- **Path Variables:** None
- **Query Parameters:** `projectId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskOperationalReportResponse>`):**
  - `projectId`: `Long`
  - `totalTasks`: `int`
  - `completedTasks`: `int`
  - `overdueTasks`: `int`
  - `averageTaskAgingDays`: `double`
  - `tasksByStatus`: `Map<String, Long>`
  - `tasksByPriority`: `Map<String, Long>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-246` — GET `/api/v1/reports/tasks`

- **Method Name:** `getTaskReport`
- **Summary / Purpose:** Get task operational report
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskOperationalReportResponse>`
- **Path Variables:** None
- **Query Parameters:** `projectId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskOperationalReportResponse>`):**
  - `projectId`: `Long`
  - `totalTasks`: `int`
  - `completedTasks`: `int`
  - `overdueTasks`: `int`
  - `averageTaskAgingDays`: `double`
  - `tasksByStatus`: `Map<String, Long>`
  - `tasksByPriority`: `Map<String, Long>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-247` — GET `/api/v1/reports/time`

- **Method Name:** `getTimeReport`
- **Summary / Purpose:** Get time logged report
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskOperationalReportResponse>`
- **Path Variables:** None
- **Query Parameters:** `projectId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskOperationalReportResponse>`):**
  - `projectId`: `Long`
  - `totalTasks`: `int`
  - `completedTasks`: `int`
  - `overdueTasks`: `int`
  - `averageTaskAgingDays`: `double`
  - `tasksByStatus`: `Map<String, Long>`
  - `tasksByPriority`: `Map<String, Long>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-248` — GET `/api/v1/reports/workload`

- **Method Name:** `getWorkloadReport`
- **Summary / Purpose:** Get workload report
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskOperationalReportResponse>`
- **Path Variables:** None
- **Query Parameters:** `projectId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskOperationalReportResponse>`):**
  - `projectId`: `Long`
  - `totalTasks`: `int`
  - `completedTasks`: `int`
  - `overdueTasks`: `int`
  - `averageTaskAgingDays`: `double`
  - `tasksByStatus`: `Map<String, Long>`
  - `tasksByPriority`: `Map<String, Long>`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskSchedulingController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskSchedulingController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskSchedulingController.java)
- **Tag / Domain:** Task Scheduling Controller (REST endpoints for task date scheduling, overdue tracking, and deadlines)
- **Endpoints Count:** 4

### `API-289` — GET `/api/v1/tasks/overdue`

- **Method Name:** `getOverdueTasks`
- **Summary / Purpose:** Get all overdue tasks
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskScheduleResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskScheduleResponse>>`):**
  - `taskId`: `Long`
  - `title`: `String`
  - `startDate`: `LocalDateTime`
  - `dueDate`: `LocalDateTime`
  - `targetDate`: `LocalDateTime`
  - `isOverdue`: `boolean`
  - `isDueToday`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-292` — GET `/api/v1/tasks/today`

- **Method Name:** `getTasksDueToday`
- **Summary / Purpose:** Get tasks due today
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskScheduleResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskScheduleResponse>>`):**
  - `taskId`: `Long`
  - `title`: `String`
  - `startDate`: `LocalDateTime`
  - `dueDate`: `LocalDateTime`
  - `targetDate`: `LocalDateTime`
  - `isOverdue`: `boolean`
  - `isDueToday`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-293` — GET `/api/v1/tasks/upcoming`

- **Method Name:** `getUpcomingTasks`
- **Summary / Purpose:** Get upcoming tasks due within 7 days
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskScheduleResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskScheduleResponse>>`):**
  - `taskId`: `Long`
  - `title`: `String`
  - `startDate`: `LocalDateTime`
  - `dueDate`: `LocalDateTime`
  - `targetDate`: `LocalDateTime`
  - `isOverdue`: `boolean`
  - `isDueToday`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-309` — PATCH `/api/v1/tasks/{id}/schedule`

- **Method Name:** `scheduleTask`
- **Summary / Purpose:** Schedule task start date, due date, and target date
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskScheduleResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ScheduleTaskRequest`
  - Fields:
    - `startDate`: `LocalDateTime`
    - `dueDate`: `LocalDateTime`
    - `targetDate`: `LocalDateTime`
    - `timezone`: `String`
- **Response Model Fields (`ResponseEntity<TaskScheduleResponse>`):**
  - `taskId`: `Long`
  - `title`: `String`
  - `startDate`: `LocalDateTime`
  - `dueDate`: `LocalDateTime`
  - `targetDate`: `LocalDateTime`
  - `isOverdue`: `boolean`
  - `isDueToday`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskTemplateController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskTemplateController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskTemplateController.java)
- **Tag / Domain:** Task Template Controller (REST endpoints for managing reusable task templates)
- **Endpoints Count:** 10

### `API-268` — DELETE `/api/v1/task-templates/{id}`

- **Method Name:** `deleteTemplate`
- **Summary / Purpose:** Delete task template
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-269` — GET `/api/v1/task-templates/{id}`

- **Method Name:** `getTemplateById`
- **Summary / Purpose:** Get task template by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-270` — PATCH `/api/v1/task-templates/{id}`

- **Method Name:** `updateTemplate`
- **Summary / Purpose:** Update task template
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateTaskTemplateRequest`
  - Fields:
    - `name`: `String`
    - `description`: `String`
    - `defaultStatusId`: `Long`
    - `defaultPriorityId`: `Long`
    - `estimatedHours`: `Double`
    - `storyPoints`: `Integer`
    - `isPublic`: `Boolean`
    - `watcherUserIds`: `List<Long>`
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-271` — POST `/api/v1/task-templates/{id}/apply`

- **Method Name:** `applyTemplate`
- **Summary / Purpose:** Apply task template to create a new task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ApplyTaskTemplateRequest`
  - Fields:
    - `projectId`: `Long` [@NotNull(message = "Project ID is required")]
    - `taskTitleOverride`: `String`
    - `assigneeId`: `Long`
    - `dueDate`: `LocalDate`
    - `sprintId`: `Long`
    - `milestoneId`: `Long`
- **Response Model Fields (`ResponseEntity<TaskResponse>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-272` — GET `/api/v1/task-templates/{id}/details`

- **Method Name:** `getTemplateDetails`
- **Summary / Purpose:** Get detailed task template information
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateDetailResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskTemplateDetailResponse>`):**
  - `template`: `TaskTemplateResponse`
  - `checklistCount`: `Integer`
  - `labelCount`: `Integer`
  - `attachmentCount`: `Integer`
  - `customFieldCount`: `Integer`
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-273` — POST `/api/v1/task-templates/{id}/duplicate`

- **Method Name:** `duplicateTemplate`
- **Summary / Purpose:** Duplicate task template
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `DuplicateTaskTemplateRequest`
  - Fields:
    - `newName`: `String` [@NotBlank(message = "New template name is required")]
    - `targetWorkspaceId`: `Long`
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-274` — POST `/api/v1/task-templates/{id}/favorite`

- **Method Name:** `favoriteTemplate`
- **Summary / Purpose:** Toggle favorite status on task template
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-275` — POST `/api/v1/task-templates/{id}/share`

- **Method Name:** `shareTemplate`
- **Summary / Purpose:** Share task template publicly in workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `ShareTemplateRequest`
  - Fields:
    - `isPublic`: `Boolean` [@NotNull(message = "isPublic flag is required")]
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-482` — GET `/api/v1/workspaces/{workspaceId}/task-templates`

- **Method Name:** `getWorkspaceTemplates`
- **Summary / Purpose:** Get workspace task templates
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskTemplateSummaryResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskTemplateSummaryResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - `usageCount`: `Integer`
  - `favoritedCount`: `Integer`
  - `isFavoritedByCurrentUser`: `Boolean`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-483` — POST `/api/v1/workspaces/{workspaceId}/task-templates`

- **Method Name:** `createTemplate`
- **Summary / Purpose:** Create task template in workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTemplateResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTaskTemplateRequest`
  - Fields:
    - `workspaceId`: `Long`
    - `projectId`: `Long`
    - `name`: `String` [@NotBlank(message = "Template name is required")]
    - `description`: `String`
    - `defaultStatusId`: `Long`
    - `defaultPriorityId`: `Long`
    - `estimatedHours`: `Double`
    - `storyPoints`: `Integer`
    - `isPublic`: `Boolean`
    - `checklists`: `List<ChecklistDTO>`
    - `labels`: `List<LabelDTO>`
    - `attachments`: `List<AttachmentDTO>`
    - `customFields`: `List<CustomFieldDTO>`
    - `watcherUserIds`: `List<Long>`
    - `title`: `String`
    - `position`: `Integer`
    - `items`: `List<ChecklistItemDTO>`
    - `title`: `String`
    - `description`: `String`
    - `position`: `Integer`
    - `labelId`: `Long`
    - `name`: `String`
    - `color`: `String`
    - `fileName`: `String`
    - `fileUrl`: `String`
    - `fileSize`: `Long`
    - `fileType`: `String`
    - `customFieldId`: `Long`
    - `fieldName`: `String`
    - `fieldValue`: `String`
- **Response Model Fields (`ResponseEntity<TaskTemplateResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `projectId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `defaultStatusId`: `Long`
  - `defaultPriorityId`: `Long`
  - `estimatedHours`: `Double`
  - `storyPoints`: `Integer`
  - `createdByUserId`: `Long`
  - `isPublic`: `Boolean`
  - `isArchived`: `Boolean`
  - *...and 31 additional fields*
- **Frontend Destination:** Task Creation Modal / Template Library (/templates)
- **UI Trigger / Interaction:** Select predefined issue template with default subtasks, labels, and fields
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskVersionController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskVersionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskVersionController.java)
- **Tag / Domain:** Task Versioning Controller (REST endpoints for task snapshot versioning, diff, and rollback)
- **Endpoints Count:** 4

### `API-311` — GET `/api/v1/tasks/{id}/versions`

- **Method Name:** `listVersions`
- **Summary / Purpose:** Get task version history
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskVersion>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskVersion>>`):**
  - `taskId`: `Long`
  - `versionNumber`: `Integer`
  - `snapshotJson`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Task Detail Drawer -> Version Diff Viewer
- **UI Trigger / Interaction:** User inspects historical snapshot diffs and rollback states
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-312` — POST `/api/v1/tasks/{id}/versions`

- **Method Name:** `createVersion`
- **Summary / Purpose:** Create task version snapshot
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskVersion>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskVersion>`):**
  - `taskId`: `Long`
  - `versionNumber`: `Integer`
  - `snapshotJson`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Task Detail Drawer -> Version Diff Viewer
- **UI Trigger / Interaction:** User inspects historical snapshot diffs and rollback states
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-396` — GET `/api/v1/versions/{id}/diff`

- **Method Name:** `getDiff`
- **Summary / Purpose:** Get diff between task versions
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail Drawer -> Version Diff Viewer
- **UI Trigger / Interaction:** User inspects historical snapshot diffs and rollback states
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-397` — POST `/api/v1/versions/{id}/restore`

- **Method Name:** `restoreVersion`
- **Summary / Purpose:** Restore task to a specific version
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskVersion>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskVersion>`):**
  - `taskId`: `Long`
  - `versionNumber`: `Integer`
  - `snapshotJson`: `String`
  - `createdByUserId`: `Long`
- **Frontend Destination:** Task Detail Drawer -> Version Diff Viewer
- **UI Trigger / Interaction:** User inspects historical snapshot diffs and rollback states
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TaskWatcherController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TaskWatcherController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TaskWatcherController.java)
- **Tag / Domain:** Task Watcher Controller (REST endpoints for managing task watchers)
- **Endpoints Count:** 4

### `API-359` — GET `/api/v1/tasks/{taskId}/watchers`

- **Method Name:** `getTaskWatchers`
- **Summary / Purpose:** Get all watchers of a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskWatcherResponse>>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskWatcherResponse>>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `watchingSince`: `LocalDateTime`
  - `notificationPreference`: `String`
- **Frontend Destination:** Task Detail Drawer -> Watchers Section
- **UI Trigger / Interaction:** User clicks 'Watch / Unwatch' or assigns colleagues to receive task alerts
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-360` — POST `/api/v1/tasks/{taskId}/watchers`

- **Method Name:** `addWatcher`
- **Summary / Purpose:** Add a watcher to a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskWatcherResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AddWatcherRequest`
  - Fields:
    - `userId`: `Long` [@NotNull(message = "User ID is required")]
    - `notificationPreference`: `String`
- **Response Model Fields (`ResponseEntity<TaskWatcherResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `username`: `String`
  - `watchingSince`: `LocalDateTime`
  - `notificationPreference`: `String`
- **Frontend Destination:** Task Detail Drawer -> Watchers Section
- **UI Trigger / Interaction:** User clicks 'Watch / Unwatch' or assigns colleagues to receive task alerts
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-361` — DELETE `/api/v1/tasks/{taskId}/watchers/{userId}`

- **Method Name:** `removeWatcher`
- **Summary / Purpose:** Remove a watcher from a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `taskId` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail Drawer -> Watchers Section
- **UI Trigger / Interaction:** User clicks 'Watch / Unwatch' or assigns colleagues to receive task alerts
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-395` — GET `/api/v1/users/{userId}/watching`

- **Method Name:** `getWatchingTasks`
- **Summary / Purpose:** Get tasks watched by a user
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TaskResponse>>`
- **Path Variables:** `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TaskResponse>>`):**
  - `id`: `Long`
  - `projectId`: `Long`
  - `sprintId`: `Long`
  - `parentTaskId`: `Long`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `status`: `TaskStatus`
  - `priority`: `TaskPriority`
  - `type`: `TaskType`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - *...and 7 additional fields*
- **Frontend Destination:** Task Detail Drawer -> Watchers Section
- **UI Trigger / Interaction:** User clicks 'Watch / Unwatch' or assigns colleagues to receive task alerts
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TeamController`

- **Package:** `org.SprintForge.modules.workspace.workspace.team.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/team/controller/TeamController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/team/controller/TeamController.java)
- **Tag / Domain:** Team Management (Endpoints for managing teams)
- **Endpoints Count:** 10

### `API-362` — GET `/api/v1/teams/{id}`

- **Method Name:** `getTeam`
- **Summary / Purpose:** Get team by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `TeamResponse`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`TeamResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `departmentId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `icon`: `String`
  - `leadMemberId`: `Long`
  - `parentTeamId`: `Long`
  - `isArchived`: `boolean`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-363` — PATCH `/api/v1/teams/{id}`

- **Method Name:** `updateTeam`
- **Summary / Purpose:** Update team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `TeamResponse`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateTeamRequest`
  - Fields:
    - `name`: `String`
    - `description`: `String`
    - `color`: `String`
    - `icon`: `String`
    - `leadMemberId`: `Long`
    - `departmentId`: `Long`
- **Response Model Fields (`TeamResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `departmentId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `icon`: `String`
  - `leadMemberId`: `Long`
  - `parentTeamId`: `Long`
  - `isArchived`: `boolean`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-364` — POST `/api/v1/teams/{id}/archive`

- **Method Name:** `archiveTeam`
- **Summary / Purpose:** Archive team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-365` — POST `/api/v1/teams/{id}/members`

- **Method Name:** `addMember`
- **Summary / Purpose:** Add member to team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AssignTeamMemberRequest`
  - Fields:
    - `memberId`: `Long` [@NotNull(message = "Member ID is required")]
    - `role`: `TeamMemberRole` [@NotNull(message = "Role is required")]
    - `allocationPercentage`: `Integer` [@Min(0), @Max(100)]
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-366` — POST `/api/v1/teams/{id}/members/transfer`

- **Method Name:** `transferMember`
- **Summary / Purpose:** Transfer member to another team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `TransferTeamMemberRequest`
  - Fields:
    - `memberId`: `Long` [@NotNull(message = "Member ID is required")]
    - `targetTeamId`: `Long` [@NotNull(message = "Target team ID is required")]
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-367` — DELETE `/api/v1/teams/{id}/members/{memberId}`

- **Method Name:** `removeMember`
- **Summary / Purpose:** Remove member from team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long), `memberId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-368` — POST `/api/v1/teams/{id}/restore`

- **Method Name:** `restoreTeam`
- **Summary / Purpose:** Restore team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `void`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-484` — GET `/api/v1/workspaces/{workspaceId}/teams`

- **Method Name:** `listWorkspaceTeams`
- **Summary / Purpose:** List workspace teams
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<TeamResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<TeamResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `departmentId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `icon`: `String`
  - `leadMemberId`: `Long`
  - `parentTeamId`: `Long`
  - `isArchived`: `boolean`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-485` — POST `/api/v1/workspaces/{workspaceId}/teams`

- **Method Name:** `createTeam`
- **Summary / Purpose:** Create a team
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `TeamResponse`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTeamRequest`
  - Fields:
    - `name`: `String` [@NotBlank(message = "Name is required")]
    - `description`: `String`
    - `color`: `String`
    - `icon`: `String`
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `departmentId`: `Long`
    - `leadMemberId`: `Long`
- **Response Model Fields (`TeamResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `departmentId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `icon`: `String`
  - `leadMemberId`: `Long`
  - `parentTeamId`: `Long`
  - `isArchived`: `boolean`
  - `memberCount`: `Long`
  - `createdAt`: `LocalDateTime`
  - *...and 1 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-486` — GET `/api/v1/workspaces/{workspaceId}/teams/search`

- **Method Name:** `searchTeams`
- **Summary / Purpose:** Search teams
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<TeamSummaryResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<TeamSummaryResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `color`: `String`
  - `icon`: `String`
  - `memberCount`: `Long`
  - `isArchived`: `boolean`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `TimeTrackingController`

- **Package:** `org.SprintForge.modules.workspace.timelog.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/timelog/controller/TimeTrackingController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/timelog/controller/TimeTrackingController.java)
- **Tag / Domain:** Time Tracking Controller (REST endpoints for task timer and logging)
- **Endpoints Count:** 7

### `API-355` — GET `/api/v1/tasks/{taskId}/time`

- **Method Name:** `getTaskTimeSummary`
- **Summary / Purpose:** Get time entries summary for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TaskTimeSummaryResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TaskTimeSummaryResponse>`):**
  - `taskId`: `Long`
  - `totalDurationMinutes`: `Long`
  - `billableDurationMinutes`: `Long`
  - `timeEntries`: `List<TimeEntryResponse>`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-356` — POST `/api/v1/tasks/{taskId}/time`

- **Method Name:** `logTime`
- **Summary / Purpose:** Log manual time entry for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TimeEntryResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateTimeEntryRequest`
  - Fields:
    - `description`: `String`
    - `startTime`: `LocalDateTime` [@NotNull(message = "Start time is required")]
    - `endTime`: `LocalDateTime`
    - `durationMinutes`: `Long`
    - `billable`: `Boolean`
- **Response Model Fields (`ResponseEntity<TimeEntryResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `description`: `String`
  - `startTime`: `LocalDateTime`
  - `endTime`: `LocalDateTime`
  - `durationMinutes`: `Long`
  - `billable`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-357` — POST `/api/v1/tasks/{taskId}/time/start`

- **Method Name:** `startTimer`
- **Summary / Purpose:** Start timer for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TimeEntryResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<TimeEntryResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `description`: `String`
  - `startTime`: `LocalDateTime`
  - `endTime`: `LocalDateTime`
  - `durationMinutes`: `Long`
  - `billable`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-358` — POST `/api/v1/tasks/{taskId}/time/stop`

- **Method Name:** `stopTimer`
- **Summary / Purpose:** Stop running timer for a task
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TimeEntryResponse>`
- **Path Variables:** `taskId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<TimeEntryResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `description`: `String`
  - `startTime`: `LocalDateTime`
  - `endTime`: `LocalDateTime`
  - `durationMinutes`: `Long`
  - `billable`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-369` — DELETE `/api/v1/time-entries/{id}`

- **Method Name:** `deleteTimeEntry`
- **Summary / Purpose:** Delete time entry
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-370` — PATCH `/api/v1/time-entries/{id}`

- **Method Name:** `updateTimeEntry`
- **Summary / Purpose:** Update time entry
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TimeEntryResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateTimeEntryRequest`
  - Fields:
    - `description`: `String`
    - `startTime`: `LocalDateTime`
    - `endTime`: `LocalDateTime`
    - `durationMinutes`: `Long`
    - `billable`: `Boolean`
- **Response Model Fields (`ResponseEntity<TimeEntryResponse>`):**
  - `id`: `Long`
  - `taskId`: `Long`
  - `userId`: `Long`
  - `description`: `String`
  - `startTime`: `LocalDateTime`
  - `endTime`: `LocalDateTime`
  - `durationMinutes`: `Long`
  - `billable`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

### `API-394` — GET `/api/v1/users/{userId}/time`

- **Method Name:** `getUserTimeSummary`
- **Summary / Purpose:** Get time entries summary for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserTimeSummaryResponse>`
- **Path Variables:** `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserTimeSummaryResponse>`):**
  - `userId`: `Long`
  - `totalDurationMinutes`: `Long`
  - `billableDurationMinutes`: `Long`
  - `timeEntries`: `List<TimeEntryResponse>`
- **Frontend Destination:** Task Detail -> Time Tracker & Timesheets (/timesheets)
- **UI Trigger / Interaction:** User logs work hours with descriptions, starts/stops timer widget
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (TimeTrackingServiceImplTest)

---

## Controller: `TrashController`

- **Package:** `org.SprintForge.modules.workspace.task.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/task/controller/TrashController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/task/controller/TrashController.java)
- **Tag / Domain:** Trash & Archive Controller (REST endpoints for task archiving, trash management, and data restoration)
- **Endpoints Count:** 5

### `API-295` — POST `/api/v1/tasks/{id}/archive`

- **Method Name:** `archiveTask`
- **Summary / Purpose:** Archive a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Task>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<Task>`):**
  - `project`: `Project`
  - `sprint`: `Sprint`
  - `milestoneId`: `Long`
  - `releaseId`: `Long`
  - `parentTask`: `Task`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - `actualHours`: `Double`
  - `storyPoints`: `Integer`
- **Frontend Destination:** Workspace Trash Bin / Archive Drawer
- **UI Trigger / Interaction:** User views soft-deleted tasks; clicks Restore or Permanently Delete
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-308` — POST `/api/v1/tasks/{id}/restore-archive`

- **Method Name:** `unarchiveTask`
- **Summary / Purpose:** Unarchive a task
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Task>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<Task>`):**
  - `project`: `Project`
  - `sprint`: `Sprint`
  - `milestoneId`: `Long`
  - `releaseId`: `Long`
  - `parentTask`: `Task`
  - `title`: `String`
  - `description`: `String`
  - `identifier`: `String`
  - `dueDate`: `LocalDateTime`
  - `estimatedHours`: `Double`
  - `actualHours`: `Double`
  - `storyPoints`: `Integer`
- **Frontend Destination:** Workspace Trash Bin / Archive Drawer
- **UI Trigger / Interaction:** User views soft-deleted tasks; clicks Restore or Permanently Delete
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-310` — POST `/api/v1/tasks/{id}/trash`

- **Method Name:** `moveToTrash`
- **Summary / Purpose:** Move task to trash
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<TrashRecord>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<TrashRecord>`):**
  - `entityType`: `String`
  - `entityId`: `Long`
  - `deletedByUserId`: `Long`
  - `deletedAt`: `LocalDateTime`
  - `scheduledPurgeAt`: `LocalDateTime`
  - `reason`: `String`
- **Frontend Destination:** Workspace Trash Bin / Archive Drawer
- **UI Trigger / Interaction:** User views soft-deleted tasks; clicks Restore or Permanently Delete
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-371` — GET `/api/v1/trash`

- **Method Name:** `getTrash`
- **Summary / Purpose:** Get list of trash records
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<TrashRecord>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<TrashRecord>>`):**
  - `entityType`: `String`
  - `entityId`: `Long`
  - `deletedByUserId`: `Long`
  - `deletedAt`: `LocalDateTime`
  - `scheduledPurgeAt`: `LocalDateTime`
  - `reason`: `String`
- **Frontend Destination:** Workspace Trash Bin / Archive Drawer
- **UI Trigger / Interaction:** User views soft-deleted tasks; clicks Restore or Permanently Delete
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-372` — POST `/api/v1/trash/{id}/restore`

- **Method Name:** `restoreFromTrash`
- **Summary / Purpose:** Restore item from trash
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Trash Bin / Archive Drawer
- **UI Trigger / Interaction:** User views soft-deleted tasks; clicks Restore or Permanently Delete
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `UserAccountController`

- **Package:** `org.SprintForge.modules.user.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/user/controller/UserAccountController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/user/controller/UserAccountController.java)
- **Tag / Domain:** User Account Controller (APIs for account lifecycle, soft delete, restore, deactivation, statistics, and data export)
- **Endpoints Count:** 7

### `API-374` — DELETE `/api/v1/users/me`

- **Method Name:** `softDeleteAccount`
- **Summary / Purpose:** Soft delete current account
- **Description:** Sets is_deleted = true on user account.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-377` — GET `/api/v1/users/me/activity`

- **Method Name:** `getUserActivityFeed`
- **Summary / Purpose:** Get user activity feed
- **Description:** Fetches recent activity logs for user.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<UserActivityResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<UserActivityResponse>>`):**
  - `id`: `Long`
  - `userId`: `Long`
  - `action`: `String`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `description`: `String`
  - `timestamp`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-380` — POST `/api/v1/users/me/deactivate`

- **Method Name:** `deactivateAccount`
- **Summary / Purpose:** Deactivate current account
- **Description:** Sets is_suspended = true to suspend account.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-381` — GET `/api/v1/users/me/export`

- **Method Name:** `exportUserData`
- **Summary / Purpose:** Export user data
- **Description:** Generates a full JSON payload export of user profile, preferences, statistics, and activities.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserDataExportResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserDataExportResponse>`):**
  - `profile`: `UserProfileResponse`
  - `preferences`: `UserPreferenceResponse`
  - `statistics`: `UserStatisticsResponse`
  - `recentActivities`: `List<UserActivityResponse>`
  - `exportedAt`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-388` — GET `/api/v1/users/me/statistics`

- **Method Name:** `getUserStatistics`
- **Summary / Purpose:** Get user statistics
- **Description:** Fetches metrics for user workspaces, projects, tasks, and comments.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserStatisticsResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserStatisticsResponse>`):**
  - `userId`: `Long`
  - `totalWorkspaces`: `long`
  - `totalProjects`: `long`
  - `assignedTasksCount`: `long`
  - `completedTasksCount`: `long`
  - `totalCommentsCount`: `long`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-392` — POST `/api/v1/users/{id}/reactivate`

- **Method Name:** `reactivateAccount`
- **Summary / Purpose:** Reactivate account by ID
- **Description:** Resets is_suspended = false to reactivate account.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-393` — POST `/api/v1/users/{id}/restore`

- **Method Name:** `restoreDeletedAccount`
- **Summary / Purpose:** Restore soft deleted account
- **Description:** Resets is_deleted = false on user account.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `UserAvatarController`

- **Package:** `org.SprintForge.modules.user.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/user/controller/UserAvatarController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/user/controller/UserAvatarController.java)
- **Tag / Domain:** User Avatar Controller (APIs for uploading and removing profile avatars)
- **Endpoints Count:** 2

### `API-378` — DELETE `/api/v1/users/me/avatar`

- **Method Name:** `removeProfileAvatar`
- **Summary / Purpose:** Remove profile avatar
- **Description:** Deletes current avatar image and resets profile avatar.
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** User Profile Modal / Account Settings
- **UI Trigger / Interaction:** User uploads image via dropzone; updates top navigation avatar
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-379` — POST `/api/v1/users/me/avatar`

- **Method Name:** `uploadProfileAvatar`
- **Summary / Purpose:** Upload profile avatar
- **Description:** Uploads a new avatar image (JPEG, PNG, GIF, WEBP up to 5MB).
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** User Profile Modal / Account Settings
- **UI Trigger / Interaction:** User uploads image via dropzone; updates top navigation avatar
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `UserController`

- **Package:** `org.SprintForge.modules.user.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/user/controller/UserController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/user/controller/UserController.java)
- **Tag / Domain:** User Profile Controller (Core APIs for managing user profiles and searching users)
- **Endpoints Count:** 5

### `API-373` — GET `/api/v1/users/check-username`

- **Method Name:** `checkUsernameAvailability`
- **Summary / Purpose:** Check username availability
- **Description:** Verifies if a username format is valid and not already taken.
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UsernameCheckResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UsernameCheckResponse>`):**
  - `username`: `String`
  - `isAvailable`: `boolean`
  - `message`: `String`
- **Frontend Destination:** Signup & Profile Forms
- **UI Trigger / Interaction:** Debounced keystroke validation checking username uniqueness in real-time
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserControllerTest, UserServiceTest)

---

### `API-375` — GET `/api/v1/users/me`

- **Method Name:** `getCurrentUserProfile`
- **Summary / Purpose:** Get current authenticated user profile
- **Description:** Returns full profile payload including preferences, stats, and roles.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserControllerTest, UserServiceTest)

---

### `API-376` — PUT `/api/v1/users/me`

- **Method Name:** `updateUserProfile`
- **Summary / Purpose:** Update current user profile
- **Description:** Updates username, fullName, displayName, or bio.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserProfileResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `email`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `provider`: `AuthProvider`
  - `emailVerified`: `boolean`
  - `isSuspended`: `boolean`
  - `isDeleted`: `boolean`
  - *...and 8 additional fields*
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserControllerTest, UserServiceTest)

---

### `API-389` — GET `/api/v1/users/search`

- **Method Name:** `searchUsers`
- **Summary / Purpose:** Search users
- **Description:** Paginated search across users by username, email, or full name.
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserSearchResponse>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserSearchResponse>`):**
  - `users`: `List<PublicUserProfileResponse>`
  - `page`: `int`
  - `size`: `int`
  - `totalElements`: `long`
  - `totalPages`: `int`
  - `last`: `boolean`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserControllerTest, UserServiceTest)

---

### `API-390` — GET `/api/v1/users/{id}`

- **Method Name:** `getPublicUserProfile`
- **Summary / Purpose:** Get public user profile by ID
- **Description:** Returns non-sensitive public user information.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<PublicUserProfileResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<PublicUserProfileResponse>`):**
  - `id`: `Long`
  - `username`: `String`
  - `fullName`: `String`
  - `displayName`: `String`
  - `bio`: `String`
  - `profilePicture`: `String`
  - `avatar`: `String`
  - `roleName`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserControllerTest, UserServiceTest)

---

## Controller: `UserPreferenceController`

- **Package:** `org.SprintForge.modules.user.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/user/controller/UserPreferenceController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/user/controller/UserPreferenceController.java)
- **Tag / Domain:** User Preferences Controller (APIs for viewing and modifying theme, language, timezone, and notification settings)
- **Endpoints Count:** 6

### `API-382` — GET `/api/v1/users/me/preferences`

- **Method Name:** `getUserPreferences`
- **Summary / Purpose:** Get user preferences
- **Description:** Fetches preferences or returns system default settings.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

### `API-383` — PUT `/api/v1/users/me/preferences`

- **Method Name:** `updateUserPreferences`
- **Summary / Purpose:** Update user preferences
- **Description:** Updates full user preferences object.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

### `API-384` — PATCH `/api/v1/users/me/preferences/language`

- **Method Name:** `updateLanguage`
- **Summary / Purpose:** Update language preference
- **Description:** Patches user language preference (e.g. en, es, fr).
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

### `API-385` — POST `/api/v1/users/me/preferences/reset`

- **Method Name:** `resetPreferences`
- **Summary / Purpose:** Reset user preferences
- **Description:** Resets user preferences back to system defaults.
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

### `API-386` — PATCH `/api/v1/users/me/preferences/theme`

- **Method Name:** `updateTheme`
- **Summary / Purpose:** Update theme preference
- **Description:** Patches user theme preference (LIGHT, DARK, SYSTEM).
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

### `API-387` — PATCH `/api/v1/users/me/preferences/timezone`

- **Method Name:** `updateTimezone`
- **Summary / Purpose:** Update timezone preference
- **Description:** Patches user timezone preference (e.g. UTC, Asia/Kolkata).
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<UserPreferenceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<UserPreferenceResponse>`):**
  - `theme`: `String`
  - `language`: `String`
  - `timezone`: `String`
  - `dateFormat`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `taskReminderEnabled`: `boolean`
- **Frontend Destination:** Main Workspace
- **UI Trigger / Interaction:** User initiated
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (UserPreferenceServiceTest)

---

## Controller: `UserProductivityController`

- **Package:** `org.SprintForge.modules.workspace.productivity.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/productivity/controller/UserProductivityController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/productivity/controller/UserProductivityController.java)
- **Tag / Domain:** User Productivity Controller (REST endpoints for recently viewed items and personal productivity tracking)
- **Endpoints Count:** 2

### `API-229` — GET `/api/v1/recent`

- **Method Name:** `getRecent`
- **Summary / Purpose:** Get list of recently viewed items
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<RecentlyViewed>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<RecentlyViewed>>`):**
  - `userId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `lastViewedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-230` — POST `/api/v1/recent`

- **Method Name:** `recordView`
- **Summary / Purpose:** Record a recently viewed item
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<RecentlyViewed>`
- **Path Variables:** None
- **Query Parameters:** `entityType` (String, required=True, default=None), `entityId` (Long, required=True, default=None), `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<RecentlyViewed>`):**
  - `userId`: `Long`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `title`: `String`
  - `lastViewedAt`: `LocalDateTime`
- **Frontend Destination:** Analytics & Executive Dashboards (/analytics)
- **UI Trigger / Interaction:** Interactive charts for velocity, cycle time, burndown, portfolio health, export reports
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkflowController`

- **Package:** `org.SprintForge.modules.workflow.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/controller/WorkflowController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/controller/WorkflowController.java)
- **Tag / Domain:** WorkflowController (N/A)
- **Endpoints Count:** 9

### `API-634` — GET `/workflows`

- **Method Name:** `getWorkflows`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Page<WorkflowSummaryResponse>>`
- **Path Variables:** None
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<Page<WorkflowSummaryResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `currentVersionNumber`: `Integer`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-635` — POST `/workflows`

- **Method Name:** `createWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateWorkflowRequest`
  - Fields:
    - `workspaceId`: `Long`
    - `name`: `String` [@NotBlank(message = "Workflow name is required")]
    - `description`: `String`
    - `category`: `String`
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-636` — DELETE `/workflows/{id}`

- **Method Name:** `deleteWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-637` — GET `/workflows/{id}`

- **Method Name:** `getWorkflowById`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-638` — PATCH `/workflows/{id}`

- **Method Name:** `updateWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateWorkflowRequest`
  - Fields:
    - `name`: `String`
    - `description`: `String`
    - `category`: `String`
    - `status`: `WorkflowStatus`
    - `enabled`: `Boolean`
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-639` — POST `/workflows/{id}/execute`

- **Method Name:** `executeWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowExecutionResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<WorkflowExecutionResponse>`):**
  - `id`: `Long`
  - `workflowVersionId`: `Long`
  - `workflowId`: `Long`
  - `status`: `ExecutionStatus`
  - `triggeredBy`: `String`
  - `triggerSource`: `String`
  - `startedAt`: `LocalDateTime`
  - `completedAt`: `LocalDateTime`
  - `duration`: `Long`
  - `currentNodeId`: `Long`
  - `contextId`: `String`
  - `result`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-640` — POST `/workflows/{id}/publish`

- **Method Name:** `publishWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `=`
- **Response Model Fields (`ResponseEntity<WorkflowResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `category`: `String`
  - `status`: `WorkflowStatus`
  - `enabled`: `Boolean`
  - `published`: `Boolean`
  - `archived`: `Boolean`
  - `currentVersionId`: `Long`
  - `currentVersionNumber`: `Integer`
  - `createdBy`: `String`
  - *...and 3 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-641` — POST `/workflows/{id}/validate`

- **Method Name:** `validateWorkflow`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowValidationResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowValidationResponse>`):**
  - `valid`: `boolean`
  - `code`: `String`
  - `message`: `String`
  - `targetId`: `String`
  - `code`: `String`
  - `message`: `String`
  - `targetId`: `String`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

### `API-642` — GET `/workflows/{id}/versions`

- **Method Name:** `getWorkflowVersions`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkflowVersionResponse>>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkflowVersionResponse>>`):**
  - `id`: `Long`
  - `workflowId`: `Long`
  - `versionNumber`: `Integer`
  - `published`: `Boolean`
  - `publishedAt`: `LocalDateTime`
  - `changeSummary`: `String`
  - `createdBy`: `String`
  - `createdAt`: `LocalDateTime`
  - `nodes`: `List<WorkflowNodeResponse>`
  - `connections`: `List<WorkflowConnectionResponse>`
  - `variables`: `List<WorkflowVariableResponse>`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (ConditionNodesTest, CycleDetectorTest, EncryptionServiceTest, GraphValidatorTest, NodeSDKTest, NodeTypeRegistryTest, WdlTest)

---

## Controller: `WorkflowDashboardController`

- **Package:** `org.SprintForge.modules.workflow.monitoring.platform`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/monitoring/platform/WorkflowDashboardController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/monitoring/platform/WorkflowDashboardController.java)
- **Tag / Domain:** WorkflowDashboardController (N/A)
- **Endpoints Count:** 3

### `API-615` — GET `/workflow/analytics`

- **Method Name:** `getAnalytics`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-616` — GET `/workflow/dashboard`

- **Method Name:** `getDashboard`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-619` — GET `/workflow/health`

- **Method Name:** `getHealth`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Map<String, Object>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkflowExecutionController`

- **Package:** `org.SprintForge.modules.workflow.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workflow/controller/WorkflowExecutionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workflow/controller/WorkflowExecutionController.java)
- **Tag / Domain:** WorkflowExecutionController (N/A)
- **Endpoints Count:** 4

### `API-611` — GET `/workflow-executions/monitoring/{workflowId}`

- **Method Name:** `getMonitoringData`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowMonitoringResponse>`
- **Path Variables:** `workflowId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowMonitoringResponse>`):**
  - `workflowId`: `Long`
  - `totalExecutions`: `long`
  - `successfulExecutions`: `long`
  - `failedExecutions`: `long`
  - `successRate`: `double`
  - `failureRate`: `double`
  - `averageExecutionTimeMillis`: `double`
  - `recentExecutions`: `List<WorkflowExecutionResponse>`
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (NodeSDKTest, GraphValidatorTest)

---

### `API-612` — GET `/workflow-executions/{id}`

- **Method Name:** `getExecutionById`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowExecutionResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowExecutionResponse>`):**
  - `id`: `Long`
  - `workflowVersionId`: `Long`
  - `workflowId`: `Long`
  - `status`: `ExecutionStatus`
  - `triggeredBy`: `String`
  - `triggerSource`: `String`
  - `startedAt`: `LocalDateTime`
  - `completedAt`: `LocalDateTime`
  - `duration`: `Long`
  - `currentNodeId`: `Long`
  - `contextId`: `String`
  - `result`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (NodeSDKTest, GraphValidatorTest)

---

### `API-613` — POST `/workflow-executions/{id}/cancel`

- **Method Name:** `cancelExecution`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowExecutionResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowExecutionResponse>`):**
  - `id`: `Long`
  - `workflowVersionId`: `Long`
  - `workflowId`: `Long`
  - `status`: `ExecutionStatus`
  - `triggeredBy`: `String`
  - `triggerSource`: `String`
  - `startedAt`: `LocalDateTime`
  - `completedAt`: `LocalDateTime`
  - `duration`: `Long`
  - `currentNodeId`: `Long`
  - `contextId`: `String`
  - `result`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (NodeSDKTest, GraphValidatorTest)

---

### `API-614` — POST `/workflow-executions/{id}/retry`

- **Method Name:** `retryExecution`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkflowExecutionResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkflowExecutionResponse>`):**
  - `id`: `Long`
  - `workflowVersionId`: `Long`
  - `workflowId`: `Long`
  - `status`: `ExecutionStatus`
  - `triggeredBy`: `String`
  - `triggerSource`: `String`
  - `startedAt`: `LocalDateTime`
  - `completedAt`: `LocalDateTime`
  - `duration`: `Long`
  - `currentNodeId`: `Long`
  - `contextId`: `String`
  - `result`: `String`
  - *...and 2 additional fields*
- **Frontend Destination:** Visual Workflow Studio (/workflows)
- **UI Trigger / Interaction:** Interactive node canvas to build, simulate, publish, and monitor automated pipelines
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (NodeSDKTest, GraphValidatorTest)

---

## Controller: `WorkloadController`

- **Package:** `org.SprintForge.modules.workspace.project.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/project/controller/WorkloadController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/project/controller/WorkloadController.java)
- **Tag / Domain:** Workload Controller (REST endpoints for user capacity, allocation, and workload reports)
- **Endpoints Count:** 3

### `API-149` — GET `/api/v1/projects/{id}/workload`

- **Method Name:** `getProjectWorkload`
- **Summary / Purpose:** Get team workload report for a project
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkloadReportResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkloadReportResponse>`):**
  - `projectId`: `Long`
  - `totalTeamMembers`: `int`
  - `memberWorkloads`: `List<UserWorkloadSummary>`
  - `userId`: `Long`
  - `assignedTaskCount`: `int`
  - `totalAssignedHours`: `double`
  - `weeklyCapacityHours`: `double`
  - `remainingCapacityHours`: `double`
  - `isOverloaded`: `boolean`
  - `isOnVacation`: `boolean`
- **Frontend Destination:** Team Workload & Capacity Grid (/projects/{id}/workload)
- **UI Trigger / Interaction:** Resource manager inspects hours assigned per team member
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-150` — GET `/api/v1/projects/{id}/workload/busy`

- **Method Name:** `unknown`
- **Summary / Purpose:** Get most busy users sorted by assigned hours
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `unknown`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Team Workload & Capacity Grid (/projects/{id}/workload)
- **UI Trigger / Interaction:** Resource manager inspects hours assigned per team member
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-151` — GET `/api/v1/projects/{id}/workload/overloaded`

- **Method Name:** `unknown`
- **Summary / Purpose:** Get overloaded users exceeding weekly capacity
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `unknown`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Team Workload & Capacity Grid (/projects/{id}/workload)
- **UI Trigger / Interaction:** Resource manager inspects hours assigned per team member
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceApiTokenController`

- **Package:** `org.SprintForge.modules.workspace.workspace.security.token.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/security/token/controller/WorkspaceApiTokenController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/security/token/controller/WorkspaceApiTokenController.java)
- **Tag / Domain:** API Token Management (N/A)
- **Endpoints Count:** 5

### `API-487` — GET `/api/v1/workspaces/{workspaceId}/tokens`

- **Method Name:** `listTokens`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<ApiTokenResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<ApiTokenResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `permissions`: `List<String>`
  - `expiresAt`: `LocalDateTime`
  - `lastUsedAt`: `LocalDateTime`
  - `isRevoked`: `Boolean`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-488` — POST `/api/v1/workspaces/{workspaceId}/tokens`

- **Method Name:** `generateToken`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<ApiTokenCreatedResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateApiTokenRequest`
  - Fields:
    - `name`: `String` [@NotBlank]
    - `permissions`: `List<String>`
    - `expiresAt`: `LocalDateTime`
- **Response Model Fields (`ResponseEntity<ApiTokenCreatedResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `permissions`: `List<String>`
  - `expiresAt`: `LocalDateTime`
  - `lastUsedAt`: `LocalDateTime`
  - `isRevoked`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `rawToken`: `String`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-489` — DELETE `/api/v1/workspaces/{workspaceId}/tokens/{tokenId}`

- **Method Name:** `revokeToken`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `tokenId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-490` — GET `/api/v1/workspaces/{workspaceId}/tokens/{tokenId}`

- **Method Name:** `getToken`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ApiTokenResponse`
- **Path Variables:** `workspaceId` (Long), `tokenId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ApiTokenResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `permissions`: `List<String>`
  - `expiresAt`: `LocalDateTime`
  - `lastUsedAt`: `LocalDateTime`
  - `isRevoked`: `Boolean`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-491` — POST `/api/v1/workspaces/{workspaceId}/tokens/{tokenId}/rotate`

- **Method Name:** `rotateToken`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ApiTokenCreatedResponse`
- **Path Variables:** `workspaceId` (Long), `tokenId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `RotateApiTokenRequest`
  - Fields:
    - `name`: `String`
- **Response Model Fields (`ApiTokenCreatedResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `permissions`: `List<String>`
  - `expiresAt`: `LocalDateTime`
  - `lastUsedAt`: `LocalDateTime`
  - `isRevoked`: `Boolean`
  - `createdAt`: `LocalDateTime`
  - `rawToken`: `String`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceAuditController`

- **Package:** `org.SprintForge.modules.workspace.workspace.security.audit.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/security/audit/controller/WorkspaceAuditController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/security/audit/controller/WorkspaceAuditController.java)
- **Tag / Domain:** Workspace Audit (N/A)
- **Endpoints Count:** 1

### `API-441` — GET `/api/v1/workspaces/{workspaceId}/audit`

- **Method Name:** `getAuditLog`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<WorkspaceAuditLogResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<WorkspaceAuditLogResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `actorId`: `Long`
  - `action`: `String`
  - `entityType`: `String`
  - `entityId`: `Long`
  - `oldValue`: `String`
  - `newValue`: `String`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceBrandingController`

- **Package:** `org.SprintForge.modules.workspace.workspace.branding.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/branding/controller/WorkspaceBrandingController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/branding/controller/WorkspaceBrandingController.java)
- **Tag / Domain:** Workspace Branding (N/A)
- **Endpoints Count:** 3

### `API-442` — DELETE `/api/v1/workspaces/{workspaceId}/branding`

- **Method Name:** `resetBranding`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-443` — GET `/api/v1/workspaces/{workspaceId}/branding`

- **Method Name:** `getBranding`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceBrandingResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceBrandingResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `logo`: `String`
  - `banner`: `String`
  - `icon`: `String`
  - `theme`: `String`
  - `accentColor`: `String`
  - `primaryColor`: `String`
  - `fontFamily`: `String`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-444` — PUT `/api/v1/workspaces/{workspaceId}/branding`

- **Method Name:** `updateBranding`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceBrandingResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceBrandingUpdateRequest`
  - Fields:
    - `logo`: `String`
    - `banner`: `String`
    - `icon`: `String`
    - `theme`: `String`
    - `accentColor`: `String`
    - `primaryColor`: `String`
    - `fontFamily`: `String`
- **Response Model Fields (`ResponseEntity<WorkspaceBrandingResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `logo`: `String`
  - `banner`: `String`
  - `icon`: `String`
  - `theme`: `String`
  - `accentColor`: `String`
  - `primaryColor`: `String`
  - `fontFamily`: `String`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceController`

- **Package:** `org.SprintForge.modules.workspace.workspace.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceController.java)
- **Tag / Domain:** Workspace Controller (REST endpoints for managing workspace lifecycle, settings, and preferences)
- **Endpoints Count:** 26

### `API-408` — POST `/api/v1/workspaces`

- **Method Name:** `createWorkspace`
- **Summary / Purpose:** Create a new workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceCreateRequest`
  - Fields:
    - `name`: `String` [@NotBlank(message = "Workspace name is required"), @Size(min = 2, max = 100, message = "Workspace name must be between 2 and 100 characters")]
    - `slug`: `String` [@Size(max = 100, message = "Slug must not exceed 100 characters")]
    - `description`: `String` [@Size(max = 1000, message = "Description must not exceed 1000 characters")]
    - `icon`: `String`
    - `coverImage`: `String`
    - `visibility`: `WorkspaceVisibility`
    - `ownerId`: `Long` [@NotNull(message = "Owner ID is required")]
    - `defaultRoleId`: `Long`
    - `defaultTaskStatusId`: `Long`
    - `defaultTaskPriorityId`: `Long`
    - `defaultView`: `WorkspaceDefaultView`
    - `storageLimit`: `Long`
    - `maxMembers`: `Integer`
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-409` — GET `/api/v1/workspaces/archived`

- **Method Name:** `listArchivedWorkspaces`
- **Summary / Purpose:** List archived workspaces
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-415` — POST `/api/v1/workspaces/search`

- **Method Name:** `searchWorkspaces`
- **Summary / Purpose:** Search workspaces
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceResponse>>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceSearchRequest`
  - Fields:
    - `query`: `String`
    - `visibility`: `WorkspaceVisibility`
    - `isArchived`: `Boolean`
    - `ownerId`: `Long`
- **Response Model Fields (`ResponseEntity<List<WorkspaceResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-416` — GET `/api/v1/workspaces/slug/{slug}`

- **Method Name:** `getWorkspaceBySlug`
- **Summary / Purpose:** Get workspace by slug
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `slug` (String)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-417` — GET `/api/v1/workspaces/user/{userId}`

- **Method Name:** `listUserWorkspaces`
- **Summary / Purpose:** List workspaces for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceResponse>>`
- **Path Variables:** `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-418` — GET `/api/v1/workspaces/user/{userId}/recent`

- **Method Name:** `recentWorkspaces`
- **Summary / Purpose:** Get recent workspaces for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceResponse>>`
- **Path Variables:** `userId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceResponse>>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-419` — DELETE `/api/v1/workspaces/{id}`

- **Method Name:** `deleteWorkspace`
- **Summary / Purpose:** Delete a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-420` — GET `/api/v1/workspaces/{id}`

- **Method Name:** `getWorkspace`
- **Summary / Purpose:** Get workspace by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-421` — PATCH `/api/v1/workspaces/{id}`

- **Method Name:** `updateWorkspace`
- **Summary / Purpose:** Update an existing workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceUpdateRequest`
  - Fields:
    - `name`: `String` [@Size(min = 2, max = 100, message = "Workspace name must be between 2 and 100 characters")]
    - `slug`: `String` [@Size(max = 100, message = "Slug must not exceed 100 characters")]
    - `description`: `String` [@Size(max = 1000, message = "Description must not exceed 1000 characters")]
    - `icon`: `String`
    - `coverImage`: `String`
    - `visibility`: `WorkspaceVisibility`
    - `defaultRoleId`: `Long`
    - `defaultTaskStatusId`: `Long`
    - `defaultTaskPriorityId`: `Long`
    - `defaultView`: `WorkspaceDefaultView`
    - `storageLimit`: `Long`
    - `maxMembers`: `Integer`
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-422` — POST `/api/v1/workspaces/{id}/archive`

- **Method Name:** `archiveWorkspace`
- **Summary / Purpose:** Archive a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-423` — POST `/api/v1/workspaces/{id}/clone`

- **Method Name:** `cloneWorkspace`
- **Summary / Purpose:** Clone a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceCloneRequest`
  - Fields:
    - `name`: `String` [@NotBlank(message = "Workspace name is required"), @Size(min = 2, max = 100, message = "Workspace name must be between 2 and 100 characters")]
    - `slug`: `String` [@Size(max = 100, message = "Slug must not exceed 100 characters")]
    - `copyMembers`: `boolean`
    - `copyPermissions`: `boolean`
    - `copyAutomation`: `boolean`
    - `copyIntegrations`: `boolean`
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-424` — GET `/api/v1/workspaces/{id}/details`

- **Method Name:** `getWorkspaceDetails`
- **Summary / Purpose:** Get detailed workspace information
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceDetailResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceDetailResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `ownerName`: `String`
  - `ownerEmail`: `String`
  - `defaultView`: `WorkspaceDefaultView`
  - `inviteCode`: `String`
  - *...and 10 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-425` — POST `/api/v1/workspaces/{id}/duplicate`

- **Method Name:** `duplicateWorkspace`
- **Summary / Purpose:** Duplicate a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-426` — DELETE `/api/v1/workspaces/{id}/favorite`

- **Method Name:** `unfavoriteWorkspace`
- **Summary / Purpose:** Unfavorite a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-427` — POST `/api/v1/workspaces/{id}/favorite`

- **Method Name:** `favoriteWorkspace`
- **Summary / Purpose:** Favorite a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-428` — GET `/api/v1/workspaces/{id}/health`

- **Method Name:** `getWorkspaceHealth`
- **Summary / Purpose:** Get workspace health report
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceHealthReportResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceHealthReportResponse>`):**
  - `workspaceId`: `Long`
  - `healthScore`: `int`
  - `status`: `String`
  - `redundantProjectsCount`: `int`
  - `unusedProjectsCount`: `int`
  - `abandonedProjectsCount`: `int`
  - `inactiveMembersCount`: `int`
  - `recommendations`: `List<String>`
  - `growthPrediction`: `String`
  - `storagePrediction`: `String`
  - `estimatedMaintenanceCost`: `Double`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-431` — POST `/api/v1/workspaces/{id}/leave`

- **Method Name:** `leaveWorkspace`
- **Summary / Purpose:** Leave a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-432` — DELETE `/api/v1/workspaces/{id}/pin`

- **Method Name:** `unpinWorkspace`
- **Summary / Purpose:** Unpin a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-433` — POST `/api/v1/workspaces/{id}/pin`

- **Method Name:** `pinWorkspace`
- **Summary / Purpose:** Pin a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-434` — POST `/api/v1/workspaces/{id}/restore`

- **Method Name:** `restoreWorkspace`
- **Summary / Purpose:** Restore an archived workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-435` — GET `/api/v1/workspaces/{id}/settings`

- **Method Name:** `getSettings`
- **Summary / Purpose:** Get workspace settings
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceSettingsResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceSettingsResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `timezone`: `String`
  - `language`: `String`
  - `dateFormat`: `String`
  - `timeFormat`: `String`
  - `weekStartDay`: `String`
  - `workingDays`: `String`
  - `workStartTime`: `LocalTime`
  - `workEndTime`: `LocalTime`
  - `allowGuestUsers`: `Boolean`
  - `allowPublicProjects`: `Boolean`
  - *...and 24 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-436` — PUT `/api/v1/workspaces/{id}/settings`

- **Method Name:** `updateSettings`
- **Summary / Purpose:** Update workspace settings
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceSettingsResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceSettingsUpdateRequest`
  - Fields:
    - `timezone`: `String`
    - `language`: `String`
    - `dateFormat`: `String`
    - `timeFormat`: `String`
    - `weekStartDay`: `String`
    - `workingDays`: `String`
    - `workStartTime`: `LocalTime`
    - `workEndTime`: `LocalTime`
    - `allowGuestUsers`: `Boolean`
    - `allowPublicProjects`: `Boolean`
    - `allowFileUploads`: `Boolean`
    - `allowTimeTracking`: `Boolean`
    - `allowAutomation`: `Boolean`
    - `allowCustomFields`: `Boolean`
    - `allowMultipleAssignees`: `Boolean`
    - `allowExternalInvites`: `Boolean`
    - `allowWorkspaceExport`: `Boolean`
    - `allowWorkspaceClone`: `Boolean`
    - `allowProjectTemplates`: `Boolean`
    - `allowRecurringTasks`: `Boolean`
    - `allowAI`: `Boolean`
    - `defaultTaskView`: `String`
    - `defaultProjectView`: `String`
    - `defaultTaskSorting`: `String`
    - `defaultTaskGrouping`: `String`
- **Response Model Fields (`ResponseEntity<WorkspaceSettingsResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `timezone`: `String`
  - `language`: `String`
  - `dateFormat`: `String`
  - `timeFormat`: `String`
  - `weekStartDay`: `String`
  - `workingDays`: `String`
  - `workStartTime`: `LocalTime`
  - `workEndTime`: `LocalTime`
  - `allowGuestUsers`: `Boolean`
  - `allowPublicProjects`: `Boolean`
  - *...and 24 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-437` — GET `/api/v1/workspaces/{id}/summary`

- **Method Name:** `getWorkspaceSummary`
- **Summary / Purpose:** Get workspace summary information
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceSummaryResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceSummaryResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `icon`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `memberCount`: `Integer`
  - `isArchived`: `boolean`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-438` — POST `/api/v1/workspaces/{id}/transfer-ownership`

- **Method Name:** `transferOwnership`
- **Summary / Purpose:** Transfer ownership of a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceResponse>`
- **Path Variables:** `id` (Long)
- **Query Parameters:** `newOwnerId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceResponse>`):**
  - `id`: `Long`
  - `name`: `String`
  - `slug`: `String`
  - `description`: `String`
  - `icon`: `String`
  - `coverImage`: `String`
  - `visibility`: `WorkspaceVisibility`
  - `ownerId`: `Long`
  - `defaultRoleId`: `Long`
  - `defaultTaskStatusId`: `Long`
  - `defaultTaskPriorityId`: `Long`
  - `defaultView`: `WorkspaceDefaultView`
  - *...and 7 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-439` — GET `/api/v1/workspaces/{id}/users/{userId}/preferences`

- **Method Name:** `getPreferences`
- **Summary / Purpose:** Get workspace preferences for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspacePreferenceResponse>`
- **Path Variables:** `id` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspacePreferenceResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `theme`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `sidebarCollapsed`: `boolean`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

### `API-440` — PUT `/api/v1/workspaces/{id}/users/{userId}/preferences`

- **Method Name:** `updatePreferences`
- **Summary / Purpose:** Update workspace preferences for a user
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspacePreferenceResponse>`
- **Path Variables:** `id` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspacePreferenceRequest`
  - Fields:
    - `theme`: `String`
    - `emailNotifications`: `Boolean`
    - `pushNotifications`: `Boolean`
    - `inAppNotifications`: `Boolean`
    - `sidebarCollapsed`: `Boolean`
- **Response Model Fields (`ResponseEntity<WorkspacePreferenceResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `theme`: `String`
  - `emailNotifications`: `boolean`
  - `pushNotifications`: `boolean`
  - `inAppNotifications`: `boolean`
  - `sidebarCollapsed`: `boolean`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspacePermissionServiceTest)

---

## Controller: `WorkspaceInvitationController`

- **Package:** `org.SprintForge.modules.workspace.workspace.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceInvitationController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceInvitationController.java)
- **Tag / Domain:** Workspace Invitation Controller (REST endpoints for managing workspace invitations lifecycle)
- **Endpoints Count:** 6

### `API-098` — DELETE `/api/v1/invitations/{token}`

- **Method Name:** `cancelInvitation`
- **Summary / Purpose:** Cancel / delete workspace invitation
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-099` — POST `/api/v1/invitations/{token}/accept`

- **Method Name:** `acceptInvitation`
- **Summary / Purpose:** Accept workspace invitation
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceInvitationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceInvitationResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `email`: `String`
  - `invitedUserId`: `Long`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `invitedBy`: `Long`
  - `inviterName`: `String`
  - `token`: `String`
  - `message`: `String`
  - `status`: `WorkspaceInvitationStatus`
  - `expiresAt`: `LocalDateTime`
  - *...and 2 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-100` — POST `/api/v1/invitations/{token}/reject`

- **Method Name:** `rejectInvitation`
- **Summary / Purpose:** Reject workspace invitation
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceInvitationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceInvitationResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `email`: `String`
  - `invitedUserId`: `Long`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `invitedBy`: `Long`
  - `inviterName`: `String`
  - `token`: `String`
  - `message`: `String`
  - `status`: `WorkspaceInvitationStatus`
  - `expiresAt`: `LocalDateTime`
  - *...and 2 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-101` — POST `/api/v1/invitations/{token}/resend`

- **Method Name:** `resendInvitation`
- **Summary / Purpose:** Resend workspace invitation
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceInvitationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceInvitationResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `email`: `String`
  - `invitedUserId`: `Long`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `invitedBy`: `Long`
  - `inviterName`: `String`
  - `token`: `String`
  - `message`: `String`
  - `status`: `WorkspaceInvitationStatus`
  - `expiresAt`: `LocalDateTime`
  - *...and 2 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-429` — GET `/api/v1/workspaces/{id}/invitations`

- **Method Name:** `getWorkspaceInvitations`
- **Summary / Purpose:** Get all invitations for a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceInvitationResponse>>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceInvitationResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `email`: `String`
  - `invitedUserId`: `Long`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `invitedBy`: `Long`
  - `inviterName`: `String`
  - `token`: `String`
  - `message`: `String`
  - `status`: `WorkspaceInvitationStatus`
  - `expiresAt`: `LocalDateTime`
  - *...and 2 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-430` — POST `/api/v1/workspaces/{id}/invitations`

- **Method Name:** `inviteMember`
- **Summary / Purpose:** Invite a new member to a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceInvitationResponse>`
- **Path Variables:** `var` (any)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceInvitationResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `email`: `String`
  - `invitedUserId`: `Long`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `invitedBy`: `Long`
  - `inviterName`: `String`
  - `token`: `String`
  - `message`: `String`
  - `status`: `WorkspaceInvitationStatus`
  - `expiresAt`: `LocalDateTime`
  - *...and 2 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceMemberController`

- **Package:** `org.SprintForge.modules.workspace.workspace.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceMemberController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceMemberController.java)
- **Tag / Domain:** Workspace Member Controller (REST endpoints for managing workspace members)
- **Endpoints Count:** 11

### `API-455` — GET `/api/v1/workspaces/{workspaceId}/members`

- **Method Name:** `getMembers`
- **Summary / Purpose:** Get all members of a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceMemberResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceMemberResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-456` — POST `/api/v1/workspaces/{workspaceId}/members`

- **Method Name:** `addMember`
- **Summary / Purpose:** Add a new member to a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AddWorkspaceMemberRequest`
  - Fields:
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `email`: `String` [@NotBlank(message = "Email is required"), @Email(message = "Invalid email format")]
    - `roleId`: `Long` [@NotNull(message = "Role ID is required")]
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-457` — GET `/api/v1/workspaces/{workspaceId}/members/active`

- **Method Name:** `getActiveMembers`
- **Summary / Purpose:** Get active members of a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceMemberResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceMemberResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-458` — GET `/api/v1/workspaces/{workspaceId}/members/admins`

- **Method Name:** `getWorkspaceAdmins`
- **Summary / Purpose:** Get workspace administrators
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceMemberResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceMemberResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-459` — GET `/api/v1/workspaces/{workspaceId}/members/owner`

- **Method Name:** `getWorkspaceOwner`
- **Summary / Purpose:** Get workspace owner
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-460` — GET `/api/v1/workspaces/{workspaceId}/members/pending`

- **Method Name:** `getPendingMembers`
- **Summary / Purpose:** Get pending invitations/members of a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceMemberResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceMemberResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-461` — GET `/api/v1/workspaces/{workspaceId}/members/search`

- **Method Name:** `searchMembers`
- **Summary / Purpose:** Search workspace members
- **Description:** N/A
- **Category:** Category C (Supporting API used by frontend workflows)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<MemberSearchResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `query` (String, required=True, default=None), `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<MemberSearchResponse>`):**
  - `members`: `List<WorkspaceMemberResponse>`
  - `totalElements`: `long`
  - `totalPages`: `int`
  - `currentPage`: `int`
- **Frontend Destination:** Global Omnisearch Modal (Cmd+K) & Filter Bars
- **UI Trigger / Interaction:** User types search term; displays auto-complete results across tasks, projects, docs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-462` — DELETE `/api/v1/workspaces/{workspaceId}/members/{userId}`

- **Method Name:** `removeMember`
- **Summary / Purpose:** Remove a member from a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-463` — POST `/api/v1/workspaces/{workspaceId}/members/{userId}/reactivate`

- **Method Name:** `reactivateMember`
- **Summary / Purpose:** Reactivate a suspended workspace member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-465` — PUT `/api/v1/workspaces/{workspaceId}/members/{userId}/role`

- **Method Name:** `changeMemberRole`
- **Summary / Purpose:** Change a workspace member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long), `userId` (Long)
- **Query Parameters:** `roleId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

### `API-466` — POST `/api/v1/workspaces/{workspaceId}/members/{userId}/suspend`

- **Method Name:** `suspendMember`
- **Summary / Purpose:** Suspend a workspace member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceMemberServiceTest)

---

## Controller: `WorkspacePreferenceController`

- **Package:** `org.SprintForge.modules.workspace.workspace.preference.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/preference/controller/WorkspacePreferenceController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/preference/controller/WorkspacePreferenceController.java)
- **Tag / Domain:** Workspace Preferences (N/A)
- **Endpoints Count:** 3

### `API-467` — DELETE `/api/v1/workspaces/{workspaceId}/preferences`

- **Method Name:** `resetWorkspacePreferences`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-468` — GET `/api/v1/workspaces/{workspaceId}/preferences`

- **Method Name:** `getWorkspacePreferences`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceGlobalPreferenceResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceGlobalPreferenceResponse>`):**
  - `workspaceId`: `Long`
  - `timezone`: `String`
  - `language`: `String`
  - `dateFormat`: `String`
  - `timeFormat`: `String`
  - `weekStartsOn`: `String`
  - `workingDays`: `List<String>`
  - `businessHoursStart`: `String`
  - `businessHoursEnd`: `String`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-469` — PUT `/api/v1/workspaces/{workspaceId}/preferences`

- **Method Name:** `updateWorkspacePreferences`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceGlobalPreferenceResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceGlobalPreferenceRequest`
  - Fields:
    - `timezone`: `String`
    - `language`: `String`
    - `dateFormat`: `String`
    - `timeFormat`: `String`
    - `weekStartsOn`: `String`
    - `workingDays`: `List<String>`
    - `businessHoursStart`: `String`
    - `businessHoursEnd`: `String`
- **Response Model Fields (`ResponseEntity<WorkspaceGlobalPreferenceResponse>`):**
  - `workspaceId`: `Long`
  - `timezone`: `String`
  - `language`: `String`
  - `dateFormat`: `String`
  - `timeFormat`: `String`
  - `weekStartsOn`: `String`
  - `workingDays`: `List<String>`
  - `businessHoursStart`: `String`
  - `businessHoursEnd`: `String`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceRoleController`

- **Package:** `org.SprintForge.modules.workspace.workspace.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceRoleController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/controller/WorkspaceRoleController.java)
- **Tag / Domain:** Workspace Role Controller (REST endpoints for managing workspace roles and permissions)
- **Endpoints Count:** 10

### `API-410` — POST `/api/v1/workspaces/roles/assign`

- **Method Name:** `assignRole`
- **Summary / Purpose:** Assign a role to a workspace member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** None
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `AssignWorkspaceRoleRequest`
  - Fields:
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `userId`: `Long` [@NotNull(message = "User ID is required")]
    - `roleId`: `Long` [@NotNull(message = "Role ID is required")]
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-411` — DELETE `/api/v1/workspaces/roles/{roleId}`

- **Method Name:** `deleteRole`
- **Summary / Purpose:** Delete a workspace role
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `roleId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-412` — GET `/api/v1/workspaces/roles/{roleId}`

- **Method Name:** `getRole`
- **Summary / Purpose:** Get a single workspace role by ID
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceRoleResponse>`
- **Path Variables:** `roleId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceRoleResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-413` — PUT `/api/v1/workspaces/roles/{roleId}`

- **Method Name:** `updateRole`
- **Summary / Purpose:** Update an existing workspace role
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceRoleResponse>`
- **Path Variables:** `roleId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateWorkspaceRoleRequest`
  - Fields:
    - `name`: `String` [@Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")]
    - `description`: `String` [@Size(max = 255, message = "Description must not exceed 255 characters")]
    - `color`: `String`
    - `priority`: `Integer`
    - `isDefaultRole`: `Boolean`
    - `permissions`: `String`
- **Response Model Fields (`ResponseEntity<WorkspaceRoleResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-414` — POST `/api/v1/workspaces/roles/{roleId}/duplicate`

- **Method Name:** `duplicateRole`
- **Summary / Purpose:** Duplicate a workspace role
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceRoleResponse>`
- **Path Variables:** `roleId` (Long)
- **Query Parameters:** `newName` (String, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceRoleResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-464` — DELETE `/api/v1/workspaces/{workspaceId}/members/{userId}/role`

- **Method Name:** `removeRole`
- **Summary / Purpose:** Remove a custom role from a workspace member
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceMemberResponse>`
- **Path Variables:** `workspaceId` (Long), `userId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceMemberResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `userName`: `String`
  - `userEmail`: `String`
  - `avatarUrl`: `String`
  - `roleId`: `Long`
  - `roleName`: `String`
  - `jobTitle`: `String`
  - `department`: `String`
  - `status`: `WorkspaceMemberStatus`
  - `joinedViaInvite`: `Boolean`
  - *...and 6 additional fields*
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-475` — GET `/api/v1/workspaces/{workspaceId}/roles`

- **Method Name:** `getRoles`
- **Summary / Purpose:** Get all roles of a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceRoleResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<List<WorkspaceRoleResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-476` — POST `/api/v1/workspaces/{workspaceId}/roles`

- **Method Name:** `createRole`
- **Summary / Purpose:** Create a custom role for a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceRoleResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `WorkspaceRoleCreateRequest`
  - Fields:
    - `workspaceId`: `Long` [@NotNull(message = "Workspace ID is required")]
    - `name`: `String` [@NotBlank(message = "Role name is required"), @Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")]
    - `description`: `String` [@Size(max = 255, message = "Description must not exceed 255 characters")]
    - `color`: `String`
    - `priority`: `Integer`
    - `isDefaultRole`: `Boolean`
    - `permissions`: `String`
- **Response Model Fields (`ResponseEntity<WorkspaceRoleResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-477` — PUT `/api/v1/workspaces/{workspaceId}/roles/reorder`

- **Method Name:** `reorderRoles`
- **Summary / Purpose:** Reorder workspace roles
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<List<WorkspaceRoleResponse>>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `List<Long>`
- **Response Model Fields (`ResponseEntity<List<WorkspaceRoleResponse>>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

### `API-478` — PUT `/api/v1/workspaces/{workspaceId}/roles/{roleId}/default`

- **Method Name:** `setDefaultRole`
- **Summary / Purpose:** Set a default role for a workspace
- **Description:** N/A
- **Category:** Category A (Directly user-facing)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WorkspaceRoleResponse>`
- **Path Variables:** `workspaceId` (Long), `roleId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`ResponseEntity<WorkspaceRoleResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `description`: `String`
  - `color`: `String`
  - `priority`: `Integer`
  - `isSystemRole`: `Boolean`
  - `isDefaultRole`: `Boolean`
  - `permissions`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings & Directory (/workspace/settings)
- **UI Trigger / Interaction:** Invite members via email, configure custom workspace roles, set business calendars, customize logo/colors
- **Implementation Status:** Fully Implemented
- **Verification Status:** Verified (WorkspaceRoleServiceTest)

---

## Controller: `WorkspaceSessionController`

- **Package:** `org.SprintForge.modules.workspace.workspace.security.session.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/security/session/controller/WorkspaceSessionController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/security/session/controller/WorkspaceSessionController.java)
- **Tag / Domain:** Session Management (N/A)
- **Endpoints Count:** 5

### `API-479` — DELETE `/api/v1/workspaces/{workspaceId}/sessions`

- **Method Name:** `revokeAllSessions`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-480` — GET `/api/v1/workspaces/{workspaceId}/sessions`

- **Method Name:** `getActiveSessions`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<WorkspaceSessionResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<WorkspaceSessionResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `ipAddress`: `String`
  - `userAgent`: `String`
  - `deviceType`: `String`
  - `location`: `String`
  - `isActive`: `Boolean`
  - `lastActivityAt`: `LocalDateTime`
  - `expiresAt`: `LocalDateTime`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-481` — DELETE `/api/v1/workspaces/{workspaceId}/sessions/{sessionId}`

- **Method Name:** `revokeSession`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `sessionId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-492` — GET `/api/v1/workspaces/{workspaceId}/trusted-devices`

- **Method Name:** `getTrustedDevices`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<TrustedDeviceResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** `userId` (Long, required=True, default=None)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<TrustedDeviceResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `userId`: `Long`
  - `deviceFingerprint`: `String`
  - `deviceName`: `String`
  - `deviceType`: `String`
  - `trustedAt`: `LocalDateTime`
  - `lastSeenAt`: `LocalDateTime`
  - `isActive`: `Boolean`
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-493` — DELETE `/api/v1/workspaces/{workspaceId}/trusted-devices/{deviceId}`

- **Method Name:** `revokeTrustedDevice`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category D (Authentication or authorization infrastructure)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `deviceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Security & API Tokens (/workspace/settings/security)
- **UI Trigger / Interaction:** Admin/Developer creates API tokens or views active workspace user sessions
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

## Controller: `WorkspaceWebhookController`

- **Package:** `org.SprintForge.modules.workspace.workspace.security.webhook.controller`
- **Source File:** [`src/main/java/org/SprintForge/modules/workspace/workspace/security/webhook/controller/WorkspaceWebhookController.java`](file:////Users/vinithvshanbhag/meg/SprintForge/src/main/java/org/SprintForge/modules/workspace/workspace/security/webhook/controller/WorkspaceWebhookController.java)
- **Tag / Domain:** Webhook Management (N/A)
- **Endpoints Count:** 8

### `API-494` — GET `/api/v1/workspaces/{workspaceId}/webhooks`

- **Method Name:** `listWebhooks`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<WebhookResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<WebhookResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-495` — POST `/api/v1/workspaces/{workspaceId}/webhooks`

- **Method Name:** `registerWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<WebhookResponse>`
- **Path Variables:** `workspaceId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `CreateWebhookRequest`
  - Fields:
    - `name`: `String` [@NotBlank]
    - `url`: `String` [@NotBlank, @Pattern(regexp = "https?://.*", message = "Must be a valid URL")]
    - `events`: `List<String>`
    - `secret`: `String`
- **Response Model Fields (`ResponseEntity<WebhookResponse>`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-496` — DELETE `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}`

- **Method Name:** `deleteWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `ResponseEntity<Void>`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-497` — GET `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}`

- **Method Name:** `getWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `WebhookResponse`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`WebhookResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-498` — PATCH `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}`

- **Method Name:** `updateWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `WebhookResponse`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** `UpdateWebhookRequest`
  - Fields:
    - `name`: `String`
    - `url`: `String` [@Pattern(regexp = "https?://.*", message = "Must be a valid URL")]
    - `events`: `List<String>`
    - `isActive`: `Boolean`
- **Response Model Fields (`WebhookResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-499` — GET `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}/deliveries`

- **Method Name:** `getDeliveries`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `List<WebhookDeliveryResponse>`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** `param` (any, required=False, default=none)
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`List<WebhookDeliveryResponse>`):**
  - `id`: `Long`
  - `webhookId`: `Long`
  - `workspaceId`: `Long`
  - `eventType`: `String`
  - `payload`: `String`
  - `responseStatus`: `Integer`
  - `isSuccess`: `Boolean`
  - `attemptNumber`: `Integer`
  - `durationMs`: `Long`
  - `errorMessage`: `String`
  - `createdAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-500` — POST `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}/disable`

- **Method Name:** `disableWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `WebhookResponse`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`WebhookResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

### `API-501` — POST `/api/v1/workspaces/{workspaceId}/webhooks/{webhookId}/enable`

- **Method Name:** `enableWebhook`
- **Summary / Purpose:** N/A
- **Description:** N/A
- **Category:** Category G (External integration or webhook)
- **Authorization Rule:** `Permit All (Public)`
- **Return Type:** `WebhookResponse`
- **Path Variables:** `workspaceId` (Long), `webhookId` (Long)
- **Query Parameters:** None
- **Required Headers:** Standard `Authorization: Bearer <JWT>`
- **Request Body:** None
- **Response Model Fields (`WebhookResponse`):**
  - `id`: `Long`
  - `workspaceId`: `Long`
  - `name`: `String`
  - `url`: `String`
  - `events`: `List<String>`
  - `isActive`: `Boolean`
  - `failureCount`: `Integer`
  - `lastDeliveryAt`: `LocalDateTime`
  - `lastDeliveryStatus`: `String`
  - `createdAt`: `LocalDateTime`
  - `updatedAt`: `LocalDateTime`
- **Frontend Destination:** Workspace Settings -> Integrations & Webhooks (/settings/integrations)
- **UI Trigger / Interaction:** Configure outgoing webhooks, connect GitHub/Jira/Slack OAuth apps, inspect delivery logs
- **Implementation Status:** Fully Implemented
- **Verification Status:** Unverified (Contract Validated via Source Inspection; No Dedicated MockMvc Test)

---

