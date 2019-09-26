package ru.romanow.heisenbug.delivery.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanow.heisenbug.delivery.DeliveryTestConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DeliveryTestConfiguration.class)
@AutoConfigureStubRunner(
        ids = {
                "ru.romanow.heisenbug:warehouse:[1.0.0,2.0.0):8070",
        },
        repositoryRoot = "https://dl.bintray.com/ronin/heisenbug-contracts/",
        stubsMode = StubRunnerProperties.StubsMode.REMOTE)
class DeliveryManageServiceTest {

    @Test
    void deliver() {
    }
}