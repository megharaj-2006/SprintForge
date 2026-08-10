package org.SprintForge.modules.integration;

import org.SprintForge.modules.integration.packages.*;
import org.SprintForge.modules.integration.registry.ConnectorOperationRegistry;
import org.SprintForge.modules.integration.sdk.OperationContext;
import org.SprintForge.modules.integration.sdk.OperationResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorPackageTest {

    @Test
    void testGithubConnectorPackageExecution() {
        GithubConnectorPackage githubPkg = new GithubConnectorPackage();
        ConnectorOperationRegistry registry = new ConnectorOperationRegistry(List.of(githubPkg));

        OperationContext context = OperationContext.builder()
                .providerKey("github")
                .operationKey("CREATE_ISSUE")
                .inputs(Map.of("repository", "SprintForge/engine", "title", "Bug fix"))
                .build();

        OperationResult result = registry.executeOperation(context);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertTrue(result.getOutputs().containsKey("issueNumber"));
        assertEquals(101, result.getOutputs().get("issueNumber"));
    }

    @Test
    void testSlackConnectorPackageExecution() {
        SlackConnectorPackage slackPkg = new SlackConnectorPackage();
        ConnectorOperationRegistry registry = new ConnectorOperationRegistry(List.of(slackPkg));

        OperationContext context = OperationContext.builder()
                .providerKey("slack")
                .operationKey("SEND_MESSAGE")
                .inputs(Map.of("channel", "#dev", "message", "Deployment ready"))
                .build();

        OperationResult result = registry.executeOperation(context);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertTrue(result.getOutputs().containsKey("ts"));
    }
}
