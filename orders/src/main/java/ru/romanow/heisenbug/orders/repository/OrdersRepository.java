package ru.romanow.heisenbug.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.heisenbug.orders.domain.Orders;

public interface OrdersRepository
        extends JpaRepository<Orders, Integer> {}
