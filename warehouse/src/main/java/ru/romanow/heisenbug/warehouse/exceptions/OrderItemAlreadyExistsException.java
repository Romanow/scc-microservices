package ru.romanow.heisenbug.warehouse.exceptions;

public class OrderItemAlreadyExistsException
        extends RuntimeException {
    public OrderItemAlreadyExistsException(String message) {
        super(message);
    }
}
