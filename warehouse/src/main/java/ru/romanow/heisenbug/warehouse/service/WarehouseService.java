package ru.romanow.heisenbug.warehouse.service;

import org.springframework.transaction.annotation.Transactional;
import ru.romanow.heisenbug.warehouse.domain.OrderItems;
import ru.romanow.heisenbug.warehouse.model.ItemsFullInfoResponse;
import ru.romanow.heisenbug.warehouse.model.OrderItemResponse;
import ru.romanow.heisenbug.warehouse.model.PageableItemsResponse;
import ru.romanow.heisenbug.warehouse.model.TakeItemsRequest;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    @Nonnull
    OrderItems getOrderByUid(@Nonnull UUID orderUid);

    @Nonnull
    PageableItemsResponse items(@Nonnull Integer page, @Nonnull Integer size);

    @Nonnull
    OrderItemResponse orderItemState(@Nonnull UUID orderUid);

    @Nonnull
    OrderItemResponse takeItems(@Nonnull UUID orderUid, @Nonnull TakeItemsRequest request);

    @Nonnull
    OrderItemResponse checkout(@Nonnull UUID orderUid);
}
