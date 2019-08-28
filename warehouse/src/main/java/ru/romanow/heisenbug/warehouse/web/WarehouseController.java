package ru.romanow.heisenbug.warehouse.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanow.heisenbug.warehouse.model.ItemsFullInfoResponse;
import ru.romanow.heisenbug.warehouse.model.OrderItemResponse;
import ru.romanow.heisenbug.warehouse.model.TakeItemsRequest;
import ru.romanow.heisenbug.warehouse.service.WarehouseService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ItemsFullInfoResponse> items(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "0") Integer size) {
        return warehouseService.items(page, size);
    }

    @GetMapping(value = "/items/{orderUid}/state", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderItemResponse orderItemState(@PathVariable UUID orderUid) {
        return warehouseService.orderItemState(orderUid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/items/{orderUid}/take",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void takeItems(@PathVariable UUID orderUid, @Valid @RequestBody TakeItemsRequest request) {
        warehouseService.takeItems(orderUid, request);
    }

    @PostMapping(value = "/items/{itemUid}/checkout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ItemsFullInfoResponse checkout(@PathVariable UUID itemUid) {
        return warehouseService.checkout(itemUid);
    }
}
