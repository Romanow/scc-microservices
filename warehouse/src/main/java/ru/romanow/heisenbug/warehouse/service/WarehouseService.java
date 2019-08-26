package ru.romanow.heisenbug.warehouse.service;

import ru.romanow.heisenbug.warehouse.model.ItemResponse;
import ru.romanow.heisenbug.warehouse.model.TakeItemsRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    @Nonnull
    List<ItemResponse> items(@Nullable Integer page, @Nonnull Integer size);

    @Nonnull
    ItemResponse itemState(@Nonnull UUID itemUid)
            throws EntityNotFoundException;

    @Nonnull
    List<ItemResponse> takeItems(@Nonnull TakeItemsRequest request);

    @Nonnull
    ItemResponse checkout(@Nonnull UUID itemUid);
}
