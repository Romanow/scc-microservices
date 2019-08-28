package ru.romanow.heisenbug.delivery.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.romanow.heisenbug.delivery.domain.Delivery;
import ru.romanow.heisenbug.delivery.domain.enums.DeliveryState;
import ru.romanow.heisenbug.delivery.exceptions.RestRequestException;
import ru.romanow.heisenbug.delivery.model.DeliveryRequest;
import ru.romanow.heisenbug.delivery.model.OrderItemResponse;
import ru.romanow.heisenbug.delivery.model.enums.OrderState;
import ru.romanow.heisenbug.delivery.repository.DeliveryRepository;

import javax.annotation.Nonnull;
import java.util.UUID;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class DeliveryManageServiceImpl
        implements DeliveryManageService {
    private static final Logger logger = getLogger(DeliveryManageServiceImpl.class);

    private static final String WAREHOUSE_URL = "http://warehouse:8070/api/v1/items";
    private static final String CHECKOUT_PATH = "/checkout";

    private final RestTemplate restTemplate;
    private final DeliveryRepository deliveryRepository;

    @Override
    public void deliver(@Nonnull UUID orderUid, @Nonnull DeliveryRequest request) {
        final String url = format("%s%s%s", WAREHOUSE_URL, orderUid, CHECKOUT_PATH);
        final OrderItemResponse response;
        try {
            response = restTemplate.postForObject(url, null, OrderItemResponse.class);
        } catch (RestClientResponseException exception) {
            final String message = format("Error request to '%s': %d:%s", url, exception.getRawStatusCode(), exception.getResponseBodyAsString());
            throw new RestRequestException(message);
        }

        if (response.getState() != OrderState.READY_FOR_DELIVERY) {
            throw new IllegalArgumentException(format("Order '%s' has invalid state", orderUid));
        }

        Delivery delivery = new Delivery()
                .setOrderUid(orderUid)
                .setState(DeliveryState.DELIVERED)
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setAddress(request.getAddress());

        delivery = deliveryRepository.save(delivery);
        if (logger.isDebugEnabled()) {
            logger.debug("Created new delivery '{}'", delivery);
        }
    }
}
