package ru.romanow.heisenbug.orders.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanow.heisenbug.orders.OrdersTestConfiguration;
import ru.romanow.heisenbug.orders.model.OrderRequest;
import ru.romanow.heisenbug.orders.repository.OrdersRepository;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrdersTestConfiguration.class)
@AutoConfigureStubRunner(
        ids = "ru.romanow.protocols:producer:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class OrderManageServiceImplTest {
    private static final UUID ORDER_UID = UUID.fromString("");

    @MockBean
    private OrdersRepository ordersRepository;

    @MockBean
    private UUIDGenerator uuidGenerator;

    @Autowired
    private OrderManageService orderManageService;

    @Test
    void makeOrder() {
        when(uuidGenerator.generate()).thenReturn(ORDER_UID);

        final List<UUID> items = newArrayList(UUID.randomUUID(), UUID.randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);
        final UUID orderUid = orderManageService.makeOrder(request);

        assertEquals(ORDER_UID, orderUid);
    }

    @Test
    void status() {
    }

    @Test
    void process() {
    }
}