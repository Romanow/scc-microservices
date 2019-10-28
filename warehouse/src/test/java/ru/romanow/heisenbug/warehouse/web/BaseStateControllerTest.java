package ru.romanow.heisenbug.warehouse.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;
import ru.romanow.heisenbug.warehouse.service.WarehouseService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static ru.romanow.heisenbug.warehouse.web.TestHelper.buildOrderItemResponse;

public class BaseStateControllerTest
        extends BaseWebTest {
    private static final UUID ORDER_UID = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");
    private static final int ITEMS_SIZE = 2;

    @Autowired
    private WarehouseController warehouseController;

    @MockBean
    private WarehouseService warehouseService;

    @BeforeEach
    public void init() {
        when(warehouseService.orderItemState(any(UUID.class))).thenAnswer((i) -> {
            final UUID orderUid = i.getArgument(0);
            throw new EntityNotFoundException(format("OrderItem '%s' not found", orderUid));
        });

        when(warehouseService.orderItemState(eq(ORDER_UID)))
                .thenReturn(buildOrderItemResponse(ORDER_UID, OrderState.READY_FOR_DELIVERY, ITEMS_SIZE));
    }

    @Override
    protected Object controller() {
        return warehouseController;
    }
}
