package ru.romanow.heisenbug.warehouse.web;

import org.apache.commons.lang3.RandomStringUtils;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;
import ru.romanow.heisenbug.warehouse.model.ItemsShortInfo;
import ru.romanow.heisenbug.warehouse.model.OrderItemResponse;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class TestHelper {

    public static OrderItemResponse buildOrderItemResponse(UUID orderUid, OrderState state, int count) {
        return new OrderItemResponse()
                .setOrderUid(orderUid)
                .setState(state)
                .setItems(buildItemInfoList(count));
    }

    public static OrderItemResponse buildOrderItemResponse(UUID orderUid, OrderState state, List<ItemsShortInfo> items) {
        return new OrderItemResponse()
                .setOrderUid(orderUid)
                .setState(state)
                .setItems(items);
    }

    public static List<ItemsShortInfo> buildItemInfoList(int size) {
        return range(0, size).mapToObj(i -> buildItemInfo()).collect(toList());
    }

    public static ItemsShortInfo buildItemInfo() {
        return new ItemsShortInfo()
                .setItemUid(UUID.randomUUID())
                .setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static ItemsShortInfo buildItemInfo(UUID itemUid) {
        return new ItemsShortInfo()
                .setItemUid(itemUid)
                .setName(RandomStringUtils.randomAlphabetic(10));
    }
}
