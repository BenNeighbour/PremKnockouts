package com.premknockout.api.model.pick;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/*
 * @created 06/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "pick")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pick implements Serializable {

    private static final long serialVersionUID = 7547554984811619685L;

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Team.class, fetch = FetchType.LAZY)
    private Team team;

    @CreationTimestamp
    @Column(name = "created", updatable = false, nullable = false)
    @JsonIgnore
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    @JsonIgnore
    private Date updated;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
