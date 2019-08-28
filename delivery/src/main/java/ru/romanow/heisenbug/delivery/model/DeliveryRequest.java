package ru.romanow.heisenbug.delivery.model;

import lombok.Data;

@Data
public class DeliveryRequest {
    private String firstName;
    private String lastName;
    private String address;
}
