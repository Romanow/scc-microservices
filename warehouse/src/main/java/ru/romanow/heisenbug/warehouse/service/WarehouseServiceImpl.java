package ru.romanow.heisenbug.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.heisenbug.warehouse.model.ItemResponse;
import ru.romanow.heisenbug.warehouse.model.TakeItemsRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl
        implements WarehouseService {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> items(@Nullable Integer page, @Nonnull Integer size) {
        return null;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public ItemResponse itemState(@Nonnull UUID itemUid) throws EntityNotFoundException {
        return null;
    }

    @Nonnull
    @Override
    @Transactional
    public List<ItemResponse> takeItems(@Nonnull TakeItemsRequest request) {
        return null;
    }

    @Nonnull
    @Override
    @Transactional
    public ItemResponse checkout(@Nonnull UUID itemUid) {
        return null;
    }
}
