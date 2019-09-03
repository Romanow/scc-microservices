package ru.romanow.heisenbug.warehouse.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;
import ru.romanow.heisenbug.warehouse.model.ItemsShortInfo;
import ru.romanow.heisenbug.warehouse.model.OrderItemResponse;
import ru.romanow.heisenbug.warehouse.service.WarehouseService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BaseCheckoutControllerTest
        extends BaseWebTest {
    private static final int ITEMS_SIZE = 2;
    private static final UUID ORDER_UID = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");

    @Autowired
    private WarehouseController warehouseController;

    @MockBean
    private WarehouseService warehouseService;

    @BeforeEach
    public void init() {
        when(warehouseService.checkout(any(UUID.class))).thenAnswer((i) -> {
            final UUID orderUid = i.getArgument(0);
            throw new EntityNotFoundException(format("OrderItem '%s' not found", orderUid));
        });
        when(warehouseService.checkout(eq(ORDER_UID))).thenReturn(buildOrderItemResponse(ORDER_UID));
    }

    @Override
    protected Object controller() {
        return warehouseController;
    }

    private OrderItemResponse buildOrderItemResponse(UUID orderUid) {
        return new OrderItemResponse()
                .setOrderUid(orderUid)
                .setState(OrderState.READY_FOR_DELIVERY)
                .setItems(buildItemInfoList(ITEMS_SIZE));
    }

    private List<ItemsShortInfo> buildItemInfoList(int size) {
        return range(0, size).mapToObj(i -> buildItemInfo()).collect(toList());
    }

    private ItemsShortInfo buildItemInfo() {
        return new ItemsShortInfo()
                .setItemUid(UUID.randomUUID())
                .setName(RandomStringUtils.randomAlphabetic(10));
    }
}
