package ru.romanow.heisenbug.warehouse.model;

import lombok.Data;
import ru.romanow.heisenbug.warehouse.model.enums.ItemState;

import java.util.UUID;

@Data
public class ItemResponse {
    private final UUID itemUid;
    private final ItemState state;
}
