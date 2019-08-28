package ru.romanow.heisenbug.orders.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
}
