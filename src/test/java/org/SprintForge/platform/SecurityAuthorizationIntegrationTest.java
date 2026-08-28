package org.SprintForge.platform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class SecurityAuthorizationIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void unauthenticatedRequests_ToAdminEndpoints_ShouldReturn401() throws Exception {
        mockMvc.perform(post("/cache/clear"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/scheduler/jobs"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/audit"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/platform/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "regular_user", roles = {"USER"})
    public void regularUser_ToAdminEndpoints_ShouldReturn403Forbidden() throws Exception {
        mockMvc.perform(post("/cache/clear"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/cache/warm"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/cache/statistics"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/scheduler/jobs"))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/scheduler/jobs/1"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/feature-flags?name=TEST_FLAG"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/audit"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/platform/events"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/platform/events/metrics"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/health/details"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/metrics"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void adminUser_ToAdminEndpoints_ShouldBeAuthorized() throws Exception {
        mockMvc.perform(get("/cache/statistics"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/scheduler/jobs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/audit"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/platform/events"))
                .andExpect(status().isOk());
    }
}