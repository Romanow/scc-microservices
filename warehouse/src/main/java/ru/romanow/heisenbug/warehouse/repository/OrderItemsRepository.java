package ru.romanow.heisenbug.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.heisenbug.warehouse.domain.OrderItems;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemsRepository
        extends JpaRepository<OrderItems, Integer> {
    Optional<OrderItems> findByOrderUid(UUID orderUid);
}
