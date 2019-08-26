package ru.romanow.heisenbug.warehouse.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TakeItemsRequest {
    private List<UUID> itemUids;
}
