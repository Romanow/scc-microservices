package ru.romanow.heisenbug.orders.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator {

    public UUID generate() {
        return UUID.randomUUID();
    }
}
