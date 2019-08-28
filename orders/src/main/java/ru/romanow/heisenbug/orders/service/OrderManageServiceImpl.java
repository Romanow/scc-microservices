package ru.romanow.heisenbug.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanow.heisenbug.orders.model.OrderInfoResponse;
import ru.romanow.heisenbug.orders.model.OrderRequest;

import javax.annotation.Nonnull;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderManageServiceImpl
        implements OrderManageService {

    @Nonnull
    @Override
    public UUID makeOrder(@Nonnull OrderRequest request) {
        return null;
    }

    @Nonnull
    @Override
    public OrderInfoResponse getStatus(@Nonnull UUID orderUid) {
        return null;
    }

    @Nonnull
    @Override
    public OrderInfoResponse process(@Nonnull UUID orderUid) {
        return null;
    }
}
