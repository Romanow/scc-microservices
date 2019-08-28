package ru.romanow.heisenbug.orders.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class OrderRequest {

    @NotNull(message = "{field.is.null")
    private UUID itemUid;

    @NotEmpty(message = "{field.is.empty")
    private String firstName;

    @NotEmpty(message = "{field.is.empty")
    private String lastName;

    @NotEmpty(message = "{field.is.empty")
    private String address;
}
