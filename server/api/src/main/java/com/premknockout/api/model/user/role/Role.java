package com.premknockout.api.model.user.role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 03/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 9106859257546425768L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private String roleType = "USER";

    public Role() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleType() {
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
