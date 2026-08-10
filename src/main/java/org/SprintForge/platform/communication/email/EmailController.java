package org.SprintForge.platform.communication.email;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("platformEmailController")
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailQueueService emailQueueService;
    private final EmailTemplateRepository emailTemplateRepository;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<EmailQueue>> sendEmail(
            @RequestParam String recipient,
            @RequestParam String templateName,
            @RequestBody(required = false) Map<String, String> payload) {
        EmailQueue queue = emailQueueService.queueEmail(recipient, templateName, payload != null ? payload : Map.of());
        return ResponseEntity.ok(ApiResponse.success(queue));
    }

    @GetMapping("/templates")
    public ResponseEntity<ApiResponse<List<EmailTemplate>>> getTemplates() {
        return ResponseEntity.ok(ApiResponse.success(emailTemplateRepository.findAll()));
    }

    @PatchMapping("/templates/{id}")
    public ResponseEntity<ApiResponse<EmailTemplate>> updateTemplate(
            @PathVariable Long id,
            @RequestBody EmailTemplate updated) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate", "id", id));
        if (updated.getSubject() != null) {
            template.setSubject(updated.getSubject());
        }
        if (updated.getBody() != null) {
            template.setBody(updated.getBody());
        }
        template.setVersion(template.getVersion() + 1);
        template = emailTemplateRepository.save(template);
        return ResponseEntity.ok(ApiResponse.success(template));
    }
}
