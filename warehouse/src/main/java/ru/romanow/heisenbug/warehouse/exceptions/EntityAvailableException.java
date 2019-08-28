package ru.romanow.heisenbug.warehouse.exceptions;

public class EntityAvailableException
        extends RuntimeException {
    public EntityAvailableException(String message) {
        super(message);
    }
}
