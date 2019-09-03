package ru.romanow.heisenbug.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TakeItemsRequest {

    @NotEmpty(message = "{field.is.empty")
    private List<UUID> itemsUid;
}
