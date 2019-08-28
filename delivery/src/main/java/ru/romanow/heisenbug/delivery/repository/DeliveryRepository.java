package ru.romanow.heisenbug.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.heisenbug.delivery.domain.Delivery;

public interface DeliveryRepository
        extends JpaRepository<Delivery, Integer> {}
