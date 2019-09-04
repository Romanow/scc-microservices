package ru.romanow.heisenbug.orders.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanow.heisenbug.orders.OrdersTestConfiguration;
import ru.romanow.heisenbug.orders.model.OrderRequest;
import ru.romanow.heisenbug.orders.repository.OrdersRepository;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrdersTestConfiguration.class)
class OrderManageServiceImplTest {

    @MockBean
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderManageService orderManageService;

    @Test
    void makeOrder() {
        final List<UUID> items = newArrayList(UUID.randomUUID(), UUID.randomUUID());
        final OrderRequest request =
                new OrderRequest()
                        .setAddress(randomAlphanumeric(10))
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setItemUids(items);
        final UUID orderUid = orderManageService.makeOrder(request);
    }

    @Test
    void status() {
    }

    @Test
    void process() {
    }
}