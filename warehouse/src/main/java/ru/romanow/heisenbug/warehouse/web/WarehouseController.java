package ru.romanow.heisenbug.warehouse.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.romanow.heisenbug.warehouse.model.ItemResponse;
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
    public List<ItemResponse> items(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "-1") Integer size) {
        return warehouseService.items(page, size);
    }

    @GetMapping(value = "/items/{itemUid}/state", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ItemResponse itemState(@PathVariable UUID itemUid) {
        return warehouseService.itemState(itemUid);
    }

    @PostMapping(value = "/items/take",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ItemResponse> takeItems(@Valid @RequestBody TakeItemsRequest request) {
        return warehouseService.takeItems(request);
    }

    @PostMapping(value = "/items/{itemUid}/checkout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ItemResponse checkout(@PathVariable UUID itemUid) {
        return warehouseService.checkout(itemUid);
    }
}
