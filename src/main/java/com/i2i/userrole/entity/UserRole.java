package com.i2i.userrole.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "user_role")
@EntityListeners(AuditingEntityListener.class)// Enable auditing
public class UserRole {

    @Column(name = "id")
    @Id
    private Long Id;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private List<User> user;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "Id=" + Id +
                ", roleName='" + roleName + '\'' +
                ", user=" + user +
                '}';
    }
}
