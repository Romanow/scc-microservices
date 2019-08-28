package ru.romanow.heisenbug.delivery.exceptions;

public class RestRequestException
        extends RuntimeException {
    public RestRequestException(String message) {
        super(message);
    }
}
