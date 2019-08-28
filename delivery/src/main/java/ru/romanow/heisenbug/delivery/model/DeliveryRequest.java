package ru.romanow.heisenbug.delivery.model;

import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryRequest {
    private UUID itemUid;
}
