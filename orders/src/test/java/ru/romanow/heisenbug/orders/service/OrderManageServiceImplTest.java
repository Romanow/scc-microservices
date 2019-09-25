package ru.romanow.heisenbug.orders.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanow.heisenbug.orders.OrdersTestConfiguration;
import ru.romanow.heisenbug.orders.domain.Orders;
import ru.romanow.heisenbug.orders.exceptions.RestRequestException;
import ru.romanow.heisenbug.orders.model.ErrorResponse;
import ru.romanow.heisenbug.orders.model.OrderInfoResponse;
import ru.romanow.heisenbug.orders.model.OrderRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;
import static groovy.json.JsonOutput.toJson;
import static java.lang.String.format;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrdersTestConfiguration.class)
@AutoConfigureStubRunner(
        ids = {
                "ru.romanow.heisenbug:warehouse:[1.0.0,2.0.0):8070",
                "ru.romanow.heisenbug:delivery:[1.0.0,2.0.0):8090"
        },
        repositoryRoot = "https://dl.bintray.com/ronin/heisenbug-contracts/",
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class OrderManageServiceImplTest {
    private static final String WAREHOUSE_URL = "http://warehouse:8070/api/v1/items/";
    private static final String DELIVERY_URL = "http://delivery:8090/api/v1/items/";
    private static final String STATE_PATH = "/state";
    private static final String DELIVERY_PATH = "/deliver";


    private static final UUID ORDER_UID_SUCCESS = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");
    private static final UUID ORDER_UID_NOT_FOUND = fromString("36856fc6-d6ec-47cb-bbee-d20e78299eb9");
    private static final UUID ORDER_UID_NOT_AVAILABLE = fromString("37bb4049-1d1e-449f-8ada-5422f8886231");
    private static final UUID ORDER_UID_NOT_READY = fromString("fc1f6904-6a27-4fa0-ac05-64233d111a23");

    @MockBean
    private OrderService orderService;

    @MockBean
    private UUIDGenerator uuidGenerator;

    @Autowired
    private OrderManageService orderManageService;

    @Test
    void makeOrderSuccess() {
        when(uuidGenerator.generate()).thenReturn(ORDER_UID_SUCCESS);

        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);
        final UUID orderUid = orderManageService.makeOrder(request);

        assertEquals(ORDER_UID_SUCCESS, orderUid);
        verify(orderService, times(1)).createOrder(eq(orderUid), eq(request));
    }

    @Test
    void makeOrderAlreadyExists() {
        final UUID generatedOrderUid = randomUUID();
        when(uuidGenerator.generate()).thenReturn(generatedOrderUid);

        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);

        assertThrows(RestRequestException.class, () -> orderManageService.makeOrder(request));
    }

    @Test
    void makeOrderItemsNotAvailable() {
        when(uuidGenerator.generate()).thenReturn(ORDER_UID_NOT_AVAILABLE);

        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);

        assertThrows(RestRequestException.class, () -> orderManageService.makeOrder(request));
    }

    @Test
    void makeOrderItemsNotFound() {
        when(uuidGenerator.generate()).thenReturn(ORDER_UID_NOT_FOUND);

        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);

        assertThrows(RestRequestException.class, () -> orderManageService.makeOrder(request));
    }

    @Test
    void statusSuccess() {
        final List<UUID> itemUids = newArrayList(randomUUID(), randomUUID());
        when(orderService.getOrderByUid(ORDER_UID_SUCCESS)).thenReturn(buildOrder(ORDER_UID_SUCCESS, itemUids));
        final OrderInfoResponse status = orderManageService.status(ORDER_UID_SUCCESS);

        assertEquals(ORDER_UID_SUCCESS  , status.getOrderUid());
    }

    @Test
    void statusOrderNotFound() {
        final UUID orderUid = randomUUID();
        when(orderService.getOrderByUid(orderUid))
                .thenThrow(new EntityNotFoundException(format("Order '%s' not found", orderUid)));

        assertThrows(EntityNotFoundException.class, () -> orderManageService.status(orderUid));
    }

    @Test
    void processOrderNotFound() {
        final UUID orderUid = randomUUID();
        when(orderService.getOrderByUid(orderUid))
                .thenThrow(new EntityNotFoundException(format("Order '%s' not found", orderUid)));

        assertThrows(EntityNotFoundException.class, () -> orderManageService.process(orderUid));
    }

    @Test
    void processRequestWarehouseError() {
        final UUID orderUid = ORDER_UID_NOT_FOUND;
        when(uuidGenerator.generate()).thenReturn(orderUid);
        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        when(orderService.getOrderByUid(orderUid))
                .thenReturn(buildOrder(orderUid, items));

        try {
            orderManageService.process(orderUid);
        } catch (RestRequestException exception) {
            final String url = format("%s%s%s", WAREHOUSE_URL, orderUid, STATE_PATH);
            final String responseMessage = toJson(new ErrorResponse(format("OrderItem '%s' not found", orderUid)));
            final String message = format("Error request to '%s': %d:%s", url, NOT_FOUND.value(), responseMessage);
            assertEquals(message, exception.getMessage());
            return;
        }
        Assertions.fail();
    }

    @Test
    void processRequestDeliveryError() {
        final UUID orderUid = ORDER_UID_SUCCESS;
        when(uuidGenerator.generate()).thenReturn(orderUid);
        final List<UUID> items = newArrayList(randomUUID(), randomUUID());
        when(orderService.getOrderByUid(orderUid))
                .thenReturn(buildOrder(orderUid, items));

        try {
            orderManageService.process(orderUid);
        } catch (RestRequestException exception) {
            final String url = format("%s%s%s", DELIVERY_URL, orderUid, DELIVERY_PATH);
            final String responseMessage = toJson(new ErrorResponse(format("OrderItem '%s' not found", orderUid)));
            final String message = format("Error request to '%s': %d:%s", url, NOT_FOUND.value(), responseMessage);
            assertEquals(message, exception.getMessage());
            return;
        }

        Assertions.fail();
    }

    private Orders buildOrder(UUID orderUid, List<UUID> itemUids) {
        return new Orders()
                .setUid(orderUid)
                .setFirstName(randomAlphabetic(10))
                .setLastName(randomAlphabetic(10))
                .setAddress(randomAlphanumeric(20))
                .setItems(on(",").join(itemUids));
    }
}