package ru.bellintegrator.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bellintegrator.entity.DownloadGroup;
import ru.bellintegrator.entity.ListGroup;
import ru.bellintegrator.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Dto пользователя и объект привязки пользователя текущей сессии
 */
public class UserView implements UserDetails {
    /**
     * id пользователя
     */
    private Long id;
    /**
     * Имя пользователя
     */
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;
    /**
     * Пароль
     */
    @NotNull
    @NotEmpty
    @NotBlank
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
     * Код активации
     */
    private String activationCode;

    /**
     * Дата генерации кода активации
     */
    private LocalDate activationCodeCreation;

    /**
     * Роли пользователя
     */
    private Set<Role> roles;

    /**
     * Члены группы, имеющие право видеть файлы данного пользователя
     */
    private ListGroup listGroup;

    /**
     * Члены группы, имеющие право скачивать файлы данного пользователя
     */
    private DownloadGroup downloadGroup;

    public UserView() {
    }

    public UserView(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isAnalyst() {
        return roles.contains(Role.ANALYST);
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
