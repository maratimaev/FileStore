package ru.bellintegrator.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли пользователя
 */
public enum Role implements GrantedAuthority {
    /**
     * Обычный пользователь
     */
    USER,

    /**
    * Администратор
    */
    ADMIN,

    /**
     * Аналитик
     */
    ANALYST;

    @Override
    public String getAuthority() {
        return name();
    }
}

