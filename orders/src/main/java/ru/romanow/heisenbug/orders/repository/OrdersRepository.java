package ru.romanow.heisenbug.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.heisenbug.orders.domain.Orders;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository
        extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByUid(UUID orderUid);
}
