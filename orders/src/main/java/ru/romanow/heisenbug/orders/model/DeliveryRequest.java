package ru.romanow.heisenbug.orders.model;

import lombok.Data;

@Data
public class DeliveryRequest {
    private String firstName;
    private String lastName;
    private String address;
}
