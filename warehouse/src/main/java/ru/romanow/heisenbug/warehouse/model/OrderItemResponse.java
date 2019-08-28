package ru.romanow.heisenbug.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.romanow.heisenbug.warehouse.domain.enums.OrderState;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private UUID orderUid;
    private OrderState state;
    private List<ItemsShortInfo> items;
}
