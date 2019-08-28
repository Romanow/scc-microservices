package ru.romanow.heisenbug.orders.service;

import ru.romanow.heisenbug.orders.domain.Orders;
import ru.romanow.heisenbug.orders.model.OrderRequest;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface OrderService {
    void createOrder(@Nonnull UUID orderUid, @Nonnull OrderRequest request);

    @Nonnull
    Orders getOrderByUid(@Nonnull UUID orderUid);
}
