package ru.romanow.heisenbug.delivery.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanow.heisenbug.delivery.DeliveryTestConfiguration;
import ru.romanow.heisenbug.delivery.exceptions.RestRequestException;
import ru.romanow.heisenbug.delivery.model.DeliveryRequest;
import ru.romanow.heisenbug.delivery.model.ErrorResponse;

import java.util.UUID;

import static groovy.json.JsonOutput.toJson;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ActiveProfiles("contract-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DeliveryTestConfiguration.class)
@AutoConfigureStubRunner(
        ids = {
                "ru.romanow.heisenbug:warehouse:[1.0.0,2.0.0):stubs:8070",
        },
        mappingsOutputFolder = "build/mappings",
        repositoryRoot = "https://dl.bintray.com/ronin/scc-microservices",
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class DeliveryManageServiceTest {
    private static final String WAREHOUSE_URL = "http://warehouse:8070/api/v1/items/";
    private static final String CHECKOUT_PATH = "/checkout";

    private static final UUID SUCCESS_ORDER_UID = UUID.fromString("1a1f775c-4f31-4256-bec1-c3d4e9bf1b52");

    @Autowired
    private DeliveryManageService deliveryManageService;

    @Test
    void deliverSuccess() {
        final DeliveryRequest request =
                new DeliveryRequest()
                .setFirstName(randomAlphabetic(10))
                .setLastName(randomAlphabetic(10))
                .setAddress(randomAlphabetic(10));
        deliveryManageService.deliver(SUCCESS_ORDER_UID, request);
    }

    @Test
    void deliverOrderNotFound() {
        final UUID orderUid = UUID.randomUUID();
        final DeliveryRequest request =
                new DeliveryRequest()
                        .setFirstName(randomAlphabetic(10))
                        .setLastName(randomAlphabetic(10))
                        .setAddress(randomAlphabetic(10));
        try {
            deliveryManageService.deliver(orderUid, request);
        } catch (RestRequestException exception) {
            final String url = format("%s%s%s", WAREHOUSE_URL, orderUid, CHECKOUT_PATH);
            final String responseMessage = toJson(new ErrorResponse(format("OrderItem '%s' not found", orderUid)));
            final String message = format("Error request to '%s': %d:%s", url, NOT_FOUND.value(), responseMessage);
            assertEquals(message, exception.getMessage());
            return;
        }

        Assertions.fail();
    }

}