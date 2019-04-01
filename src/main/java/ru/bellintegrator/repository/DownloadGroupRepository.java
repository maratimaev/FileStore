package ru.bellintegrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.entity.DownloadGroup;

/**
 * Методы работы с группой Download
 */
public interface DownloadGroupRepository extends JpaRepository<DownloadGroup, Long> {
}
