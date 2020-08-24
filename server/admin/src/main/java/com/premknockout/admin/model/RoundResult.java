package com.premknockout.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @created 14/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "round_result")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoundResult implements Serializable {

    private static final long serialVersionUID = 1324339842026965991L;

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @GeneratedValue
    @JsonIgnore
    @Column(name = "knockout_id", columnDefinition = "uuid", updatable = false)
    private UUID knockoutId;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Team.class, fetch = FetchType.LAZY)
    private List<Team> winningTeams;

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

    public List<Team> getWinningTeams() {
        return winningTeams;
    }

    public void setWinningTeams(List<Team> winningTeams) {
        this.winningTeams = winningTeams;
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

    public UUID getKnockoutId() {
        return knockoutId;
    }

    public void setKnockoutId(UUID knockoutId) {
        this.knockoutId = knockoutId;
    }
}
