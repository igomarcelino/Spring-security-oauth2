package com.igomarcelino.demo_oauth_security.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public enum Values{
        BASIC(2),
        ADMIN(1);

        Integer id;

        Values(Integer id) {
            this.id = id;
        }
    }
}
