package org.SprintForge.platform.communication.template;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("platformCommunicationTemplateService")
@RequiredArgsConstructor
public class CommunicationTemplateService {

    private final CommunicationTemplateRepository templateRepository;

    @Transactional(readOnly = true)
    public String renderContent(String templateName, Map<String, String> variables) {
        CommunicationTemplate template = templateRepository.findByName(templateName)
                .orElseThrow(() -> new ResourceNotFoundException("CommunicationTemplate", "name", templateName));

        return applyVariables(template.getContent(), variables);
    }

    public String applyVariables(String templateText, Map<String, String> variables) {
        if (templateText == null || variables == null || variables.isEmpty()) {
            return templateText != null ? templateText : "";
        }
        String rendered = templateText;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            rendered = rendered.replace(placeholder, entry.getValue() != null ? entry.getValue() : "");
        }
        return rendered;
    }

    @Transactional
    public CommunicationTemplate saveTemplate(String name, NotificationChannel channel, NotificationType type, String subject, String content, String variables) {
        CommunicationTemplate template = templateRepository.findByName(name)
                .orElseGet(() -> CommunicationTemplate.builder()
                        .name(name)
                        .channel(channel)
                        .type(type)
                        .build());

        template.setSubject(subject);
        template.setContent(content);
        template.setVariables(variables);
        template.setVersion(template.getVersion() + 1);

        return templateRepository.save(template);
    }
}
