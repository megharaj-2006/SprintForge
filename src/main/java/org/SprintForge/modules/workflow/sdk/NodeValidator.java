package org.SprintForge.modules.workflow.sdk;

import java.util.List;

public interface NodeValidator {
    List<String> validate(NodeConfiguration configuration);
}
