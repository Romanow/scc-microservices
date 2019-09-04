package ru.romanow.heisenbug.orders.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class UUIDGenerator {

    public UUID generate() {
        return UUID.randomUUID();
    }
}
