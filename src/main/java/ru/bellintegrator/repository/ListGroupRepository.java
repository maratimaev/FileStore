package ru.bellintegrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.entity.ListGroup;

/**
 * Методы работы с группой List
 */
public interface ListGroupRepository extends JpaRepository<ListGroup, Long> {
}
