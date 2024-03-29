package ru.romanow.heisenbug.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.heisenbug.orders.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository
        extends JpaRepository<Order, Integer> {
    Optional<Order> findByUid(UUID orderUid);
}
