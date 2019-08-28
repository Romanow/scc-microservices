package ru.romanow.heisenbug.orders.exceptions;

public class RestRequestException
        extends RuntimeException {
    public RestRequestException(String message) {
        super(message);
    }
}
