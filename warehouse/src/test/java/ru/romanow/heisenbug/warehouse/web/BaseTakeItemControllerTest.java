package ru.romanow.heisenbug.warehouse.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;
import ru.romanow.heisenbug.warehouse.exceptions.EntityAvailableException;
import ru.romanow.heisenbug.warehouse.exceptions.OrderItemAlreadyExistsException;
import ru.romanow.heisenbug.warehouse.model.ItemsShortInfo;
import ru.romanow.heisenbug.warehouse.model.TakeItemsRequest;
import ru.romanow.heisenbug.warehouse.service.WarehouseService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;
import static java.util.UUID.fromString;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static ru.romanow.heisenbug.warehouse.web.TestHelper.buildOrderItemResponse;

public class BaseTakeItemControllerTest
        extends BaseWebTest {
    private static final UUID ORDER_UID_SUCCESS = fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");
    private static final UUID ORDER_UID_NOT_FOUND = fromString("36856fc6-d6ec-47cb-bbee-d20e78299eb9");
    private static final UUID ORDER_UID_NOT_AVAILABLE = fromString("37bb4049-1d1e-449f-8ada-5422f8886231");

    @Autowired
    private WarehouseController warehouseController;

    @MockBean
    private WarehouseService warehouseService;

    @BeforeEach
    public void init() {
        // 1. orderItem already exists
        // 2. not all items found (found < requested)
        // 3. entity not available (count < 0)
        // 4. success
        when(warehouseService.takeItems(any(UUID.class), any(TakeItemsRequest.class))).thenAnswer((i) -> {
            final UUID orderUid = i.getArgument(0);
            throw new OrderItemAlreadyExistsException(format("OrderItem '%s' already exists", orderUid));
        });

        when(warehouseService.takeItems(eq(ORDER_UID_SUCCESS), any(TakeItemsRequest.class))).thenAnswer((i) -> {
            final TakeItemsRequest takeItemsRequest = i.getArgument(1);
            final List<ItemsShortInfo> items = takeItemsRequest
                    .getItemsUid()
                    .stream()
                    .map(TestHelper::buildItemInfo)
                    .collect(toList());
            return buildOrderItemResponse(ORDER_UID_SUCCESS, OrderState.CREATED, items);
        });

        when(warehouseService.takeItems(eq(ORDER_UID_NOT_FOUND), any(TakeItemsRequest.class))).thenAnswer((i) -> {
            final TakeItemsRequest takeItemsRequest = i.getArgument(1);
            throw new EntityNotFoundException(format("Not all items [%s] found", on(",").join(takeItemsRequest.getItemsUid())));
        });

        when(warehouseService.takeItems(eq(ORDER_UID_NOT_AVAILABLE), any(TakeItemsRequest.class))).thenAnswer((i) -> {
            final TakeItemsRequest takeItemsRequest = i.getArgument(1);
            throw new EntityAvailableException(format("Items [%s] is empty (available count = 0)", on(",").join(takeItemsRequest.getItemsUid())));
        });
    }

    @Override
    protected Object controller() {
        return warehouseController;
    }
}
