package org.SprintForge.common.constants;

public final class ApiPaths {

    public static final String API_V1 = "/api/v1";
    
    // Auth endpoints
    public static final String AUTH = API_V1 + "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refresh-token";
    
    // User endpoints
    public static final String USERS = API_V1 + "/users";
    
    // Workspace endpoints
    public static final String WORKSPACES = API_V1 + "/workspaces";
    
    // Project endpoints
    public static final String PROJECTS = API_V1 + "/projects";
    
    // Task endpoints
    public static final String TASKS = API_V1 + "/tasks";
    
    // Notification endpoints
    public static final String NOTIFICATIONS = API_V1 + "/notifications";
    
    // Admin endpoints
    public static final String ADMIN = API_V1 + "/admin";

    private ApiPaths() {
        // Constants class
    }
}
