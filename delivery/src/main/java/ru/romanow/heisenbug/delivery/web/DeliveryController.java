package ru.romanow.heisenbug.delivery.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/{orderUid}/deliver",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeliveryStatusResponse deliver(@PathVariable UUID orderUid, @Valid @RequestBody DeliveryRequest request) {
        return deliveryManageService.deliver(orderUid, request);
    }
}
