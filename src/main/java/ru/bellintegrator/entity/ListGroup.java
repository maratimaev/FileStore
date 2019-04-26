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
import javax.persistence.Version;
import java.util.Set;

/**
 * Entity группы пользователей с правом list
 */
@Entity
public class ListGroup {
    /**
     * id группы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_group_id")
    private Long id;

    /**
     * Члены группы
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="lg_u",
            joinColumns = @JoinColumn( name= "list_group_id_fk", referencedColumnName = "list_group_id"),
            inverseJoinColumns = @JoinColumn( name="user_id_fk", referencedColumnName = "user_id")
    )
    private Set<User> members;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
