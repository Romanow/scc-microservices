package ru.romanow.heisenbug.warehouse.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageableItemsResponse {
    private int page;
    private int totalSize;
    private int pageSize;
    private List<ItemsFullInfoResponse> items;
}
