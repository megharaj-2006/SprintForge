package org.SprintForge.platform.communication.webhook;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformWebhookController")
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class PlatformWebhookController {

    private final PlatformWebhookDeliveryRepository deliveryRepository;
    private final WebhookRetryService webhookRetryService;

    @GetMapping("/deliveries")
    public ResponseEntity<ApiResponse<List<PlatformWebhookDelivery>>> getDeliveries() {
        return ResponseEntity.ok(ApiResponse.success(deliveryRepository.findAll()));
    }

    @PostMapping("/retry/{id}")
    public ResponseEntity<ApiResponse<PlatformWebhookDelivery>> retryDelivery(@PathVariable Long id) {
        PlatformWebhookDelivery retried = webhookRetryService.retryDelivery(id);
        return ResponseEntity.ok(ApiResponse.success(retried));
    }
}
