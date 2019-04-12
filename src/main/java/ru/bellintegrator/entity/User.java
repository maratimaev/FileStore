package ru.bellintegrator.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

/**
 * Entity пользователя
 */
@Entity
@Table(name = "usr")
public class User {
    /**
     * id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    /**
     * Имя пользователя
     */
    private String username;
    /**
     * Пароль
     */
    private String password;
    /**
     * Признак активности
     */
    private boolean active;
    /**
     * Почтовый адрес для активации пользователя
     */
    private String email;
    /**
     * Код активациии
     */
    private String activationCode;


    /**
     * Дата генерации кода активации
     */
    private LocalDate activationCodeCreation;

    /**
     * Роли пользователя
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_role_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * Список пользователей, имеющих право видеть файлы данного пользователя
     */
    @OneToOne(fetch = FetchType.EAGER)
    private ListGroup listGroup;

    /**
     * Список пользователей, имеющих право скачивать файлы данного пользователя
     */
    @OneToOne(fetch = FetchType.EAGER)
    private DownloadGroup downloadGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public ListGroup getListGroup() {
        return listGroup;
    }

    public void setListGroup(ListGroup listGroup) {
        this.listGroup = listGroup;
    }

    public DownloadGroup getDownloadGroup() {
        return downloadGroup;
    }

    public void setDownloadGroup(DownloadGroup downloadGroup) {
        this.downloadGroup = downloadGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public LocalDate getActivationCodeCreation() {
        return activationCodeCreation;
    }

    public void setActivationCodeCreation(LocalDate activationCodeCreation) {
        this.activationCodeCreation = activationCodeCreation;
    }
}

