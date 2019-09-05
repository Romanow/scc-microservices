package ru.romanow.heisenbug.orders.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
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

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrdersTestConfiguration.class)
@AutoConfigureStubRunner(
        ids = "ru.romanow.heisenbug:warehouse:1.0.0:8070",
        repositoryRoot = "git://https://gitlab.com/heisenbug-conf/heisenbug-contracts",
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class OrderManageServiceImplTest {
    private static final UUID ORDER_UID = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");

    @MockBean
    private OrderService orderService;

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
//        final UUID orderUid = orderManageService.makeOrder(request);

//        assertEquals(ORDER_UID, orderUid);
    }
//
//    @Test
//    void status() {
//    }
//
//    @Test
//    void process() {
//    }
}