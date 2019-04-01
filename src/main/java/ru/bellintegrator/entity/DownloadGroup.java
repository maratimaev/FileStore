package ru.bellintegrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Entity группы пользователей с правом download
 */
@Entity
public class DownloadGroup {
    /**
     * id группы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "download_group_id")
    private Long id;

    /**
     * Члены группы
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="dg_u",
            joinColumns = @JoinColumn( name= "download_group_id_fk", referencedColumnName = "download_group_id"),
            inverseJoinColumns = @JoinColumn( name="user_id_fk", referencedColumnName = "user_id")
    )
    private Set<User> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
