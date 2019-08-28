package ru.romanow.heisenbug.orders.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.romanow.heisenbug.orders.domain.Orders;
import ru.romanow.heisenbug.orders.exceptions.RestRequestException;
import ru.romanow.heisenbug.orders.model.OrderInfoResponse;
import ru.romanow.heisenbug.orders.model.OrderRequest;
import ru.romanow.heisenbug.orders.model.TakeItemsRequest;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class OrderManageServiceImpl
        implements OrderManageService {
    private static final Logger logger = getLogger(OrderManageServiceImpl.class);

    private static final String WAREHOUSE_URL = "http://warehouse:8070/api/v1/items/";
    private static final String TAKE_PATH = "/take";
    private static final String STATE_PATH = "/state";

    private final RestTemplate restTemplate;
    private final OrderService orderService;

    @Nonnull
    @Override
    public UUID makeOrder(@Nonnull OrderRequest request) {
        final UUID orderUid = UUID.randomUUID();
        final TakeItemsRequest takeItemRequest = new TakeItemsRequest(request.getItemUids());
        final String url = format("%s%s%s", WAREHOUSE_URL, orderUid, TAKE_PATH);
        try {
            final URI location = restTemplate.postForLocation(url, takeItemRequest);
            if (logger.isDebugEnabled()) {
                logger.debug("Reserve items on warehouse, order number '{}'", location);
            }
        } catch (RestClientResponseException exception) {
            final String message = format("Error request to '%s': %d:%s", url, exception.getRawStatusCode(), exception.getResponseBodyAsString());
            throw new RestRequestException(message);
        }

        orderService.createOrder(orderUid, request);

        return orderUid;
    }

    @Nonnull
    @Override
    public OrderInfoResponse status(@Nonnull UUID orderUid) {
        final Orders order = orderService.getOrderByUid(orderUid);
        final String url = format("%s/%s%s", WAREHOUSE_URL, orderUid, STATE_PATH);
        OrderInfoResponse orderInfo;
        try {
            orderInfo = restTemplate.getForObject(url, OrderInfoResponse.class);
        } catch (RestClientResponseException exception) {
            final String message = format("Error request to '%s': %d:%s", url, exception.getRawStatusCode(), exception.getResponseBodyAsString());
            throw new RestRequestException(message);
        }

        return buildOrderInfoResponse(order, orderInfo);
    }

    @Nonnull
    @Override
    public OrderInfoResponse process(@Nonnull UUID orderUid) {
        return null;
    }

    @Nonnull
    private OrderInfoResponse buildOrderInfoResponse(@Nonnull Orders order, @Nonnull OrderInfoResponse orderInfo) {
        return new OrderInfoResponse()
                .setOrderUid(order.getUid())
                .setState(orderInfo.getState())
                .setItems(orderInfo.getItems())
                .setFirstName(order.getFirstName())
                .setLastName(order.getLastName())
                .setAddress(order.getAddress());
    }
}
