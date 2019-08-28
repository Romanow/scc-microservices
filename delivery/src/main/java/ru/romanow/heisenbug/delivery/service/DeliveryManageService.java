package ru.romanow.heisenbug.delivery.service;

import ru.romanow.heisenbug.delivery.model.DeliveryRequest;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface DeliveryManageService {
    void deliver(@Nonnull UUID orderUid, @Nonnull DeliveryRequest request);
}
