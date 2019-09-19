package ru.romanow.heisenbug.delivery.exceptions;

public class OrderNotReadyException
        extends RuntimeException {
    public OrderNotReadyException(String message) {
        super(message);
    }
}
