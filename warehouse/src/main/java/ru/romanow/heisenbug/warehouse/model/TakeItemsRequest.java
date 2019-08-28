package ru.romanow.heisenbug.warehouse.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TakeItemsRequest {

    @NotEmpty(message = "{field.is.null")
    private List<UUID> itemsUid;
}
