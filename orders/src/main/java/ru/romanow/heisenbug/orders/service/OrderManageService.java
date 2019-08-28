package ru.romanow.heisenbug.orders.service;

import ru.romanow.heisenbug.orders.model.OrderInfoResponse;
import ru.romanow.heisenbug.orders.model.OrderRequest;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface OrderManageService {

    @Nonnull
    UUID makeOrder(@Nonnull OrderRequest request);

    @Nonnull
    OrderInfoResponse status(@Nonnull UUID orderUid);

    @Nonnull
    OrderInfoResponse process(@Nonnull UUID orderUid);
}
