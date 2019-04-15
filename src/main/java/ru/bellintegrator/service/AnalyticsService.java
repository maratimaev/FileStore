package ru.bellintegrator.service;

import ru.bellintegrator.dto.Analytics;

import java.util.List;

/**
 * Сервисы работы со статистикой
 */
public interface AnalyticsService {
    /** Получение статситики по всем пользователям
     * @return список dto Analytics
     */
    List<Analytics> getAllUserStat();
}
