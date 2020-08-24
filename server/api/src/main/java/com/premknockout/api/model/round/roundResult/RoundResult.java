package com.premknockout.api.model.round.roundResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premknockout.api.model.team.Team;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @created 06/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "round_result")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoundResult implements Serializable {

    private static final long serialVersionUID = 6800459444356405577L;

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

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
}
