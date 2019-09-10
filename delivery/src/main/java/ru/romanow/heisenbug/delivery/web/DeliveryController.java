package ru.romanow.heisenbug.delivery.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanow.heisenbug.delivery.model.DeliveryRequest;
import ru.romanow.heisenbug.delivery.service.DeliveryManageService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryManageService deliveryManageService;

    @Data
    class SimpleResponse {
        private final String message;
    }

    @PostMapping(value = "/{orderUid}/deliver",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SimpleResponse> deliver(@PathVariable UUID orderUid, @Valid @RequestBody DeliveryRequest request) {
        deliveryManageService.deliver(orderUid, request);
        return ResponseEntity.accepted().body(new SimpleResponse("OK"));
    }
}
