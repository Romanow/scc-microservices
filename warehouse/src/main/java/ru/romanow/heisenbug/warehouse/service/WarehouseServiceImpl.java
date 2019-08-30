package ru.romanow.heisenbug.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.heisenbug.warehouse.domain.Items;
import ru.romanow.heisenbug.warehouse.domain.OrderItems;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;
import ru.romanow.heisenbug.warehouse.exceptions.EntityAvailableException;
import ru.romanow.heisenbug.warehouse.model.*;
import ru.romanow.heisenbug.warehouse.repository.ItemsRepository;
import ru.romanow.heisenbug.warehouse.repository.OrderItemsRepository;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Pageable.unpaged;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl
        implements WarehouseService {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    private final ItemsRepository itemsRepository;
    private final OrderItemsRepository orderItemsRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public OrderItems getOrderByUid(@Nonnull UUID orderUid) {
        return orderItemsRepository.findByOrderUid(orderUid)
                .orElseThrow(() -> new EntityNotFoundException(format("OrderItem '%s' not found", orderUid)));
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PageableItemsResponse items(@Nonnull Integer page, @Nonnull Integer size) {
        final Pageable pageable = size > 0 ? of(page, size) : unpaged();
        final Page<Items> items = itemsRepository.findAll(pageable);
        return new PageableItemsResponse()
                .setPage(page)
                .setPageSize(size)
                .setTotalSize((int) items.getTotalElements())
                .setItems(items.getContent()
                        .stream()
                        .map(this::buildItemsFullInfoResponse)
                        .collect(toList()));
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public OrderItemResponse orderItemState(@Nonnull UUID orderUid) throws EntityNotFoundException {
        final OrderItems orderItems = getOrderByUid(orderUid);
        return buildOrderItemsResponse(orderItems);
    }

    @Nonnull
    @Override
    @Transactional
    public OrderItemResponse takeItems(@Nonnull UUID orderUid, @Nonnull TakeItemsRequest request) {
        final List<UUID> itemsUid = request.getItemsUid();
        final List<Items> items = itemsRepository.findByUids(itemsUid);
        if (items.size() != itemsUid.size()) {
            throw new EntityNotFoundException(format("Not all items [%s] found", on(",").join(itemsUid)));
        }

        final List<Items> absentItems = items.stream().filter(item -> item.getCount() == 0).collect(toUnmodifiableList());
        if (absentItems.size() != 0) {
            throw new EntityAvailableException(format("Items [%s] is empty (available count = 0)", on(",").join(absentItems)));
        }

        OrderItems orderItems = new OrderItems()
                .setState(OrderState.CREATED)
                .setOrderUid(orderUid)
                .setItems(items);

        orderItems = orderItemsRepository.save(orderItems);
        if (logger.isDebugEnabled()) {
            logger.debug("Create orderItems '{}'", orderItems);
        }

        items.forEach(item -> {
            item.decrementCount();
            itemsRepository.save(item);
            if (logger.isDebugEnabled()) {
                logger.debug("Update count on item '{}'", item);
            }
        });

        return buildOrderItemsResponse(orderItems);
    }

    @Nonnull
    @Override
    @Transactional
    public OrderItemResponse checkout(@Nonnull UUID orderUid) {
        final OrderItems orderItems = getOrderByUid(orderUid);
        orderItems.setState(OrderState.READY_FOR_DELIVERY);
        orderItemsRepository.save(orderItems);

        if (logger.isDebugEnabled()) {
            logger.debug("Update orderItem state: {}, '{}'", OrderState.READY_FOR_DELIVERY, orderItems);
        }
        return buildOrderItemsResponse(orderItems);
    }

    @Nonnull
    private ItemsFullInfoResponse buildItemsFullInfoResponse(@Nonnull Items items) {
        final ItemsFullInfoResponse response = new ItemsFullInfoResponse();
        response.setCount(items.getCount())
                .setItemUid(items.getUid())
                .setName(items.getName());
        return response;
    }

    @Nonnull
    private ItemsShortInfo buildItemsShortInfoResponse(@Nonnull Items item) {
        return new ItemsShortInfo()
                .setName(item.getName())
                .setItemUid(item.getUid());
    }

    @Nonnull
    private OrderItemResponse buildOrderItemsResponse(@Nonnull OrderItems orderItems) {
        final List<ItemsShortInfo> items = orderItems.getItems()
                .stream()
                .map(this::buildItemsShortInfoResponse)
                .collect(toList());

        return new OrderItemResponse()
                .setState(orderItems.getState())
                .setOrderUid(orderItems.getOrderUid())
                .setItems(items);
    }
}
