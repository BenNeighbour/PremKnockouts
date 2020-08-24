package com.premknockout.api.model.knockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premknockout.api.model.round.Round;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @created 06/07/2020 - 19
 * @project PremKnockout
 * @author Ben Neighbour
 */
@Entity
@Table(name = "knockout")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Knockout implements Serializable {

  private static final long serialVersionUID = 1812115232708138273L;

  @Id
  @GeneratedValue
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @NotEmpty(message = "You must give a valid name!")
  @Column(name = "name")
  private String name;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @ManyToMany(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
  private List<User> participants;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @OneToOne(cascade = CascadeType.ALL, targetEntity = Round.class, fetch = FetchType.LAZY)
  private Round currentRound;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @OneToMany(cascade = CascadeType.ALL, targetEntity = Round.class, fetch = FetchType.LAZY)
  private List<Round> rounds;

  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, targetEntity = Team.class, fetch = FetchType.LAZY)
  private List<Team> teams;

  @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
  private User winner;

  @JsonIgnore
  @Column(name = "finished")
  private boolean isFinished = false;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getParticipants() {
    return participants;
  }

  public void setParticipants(List<User> participants) {
    this.participants = participants;
  }

  public Round getCurrentRound() {
    return currentRound;
  }

  public void setCurrentRound(Round currentRound) {
    this.currentRound = currentRound;
  }

  public List<Round> getRounds() {
    return rounds;
  }

  public void setRounds(List<Round> rounds) {
    this.rounds = rounds;
  }

  public List<Team> getTeams() {
    return teams;
  }

  public void setTeams(List<Team> teams) {
    this.teams = teams;
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

  public User getWinner() {
    return winner;
  }

  public void setWinner(User winner) {
    this.winner = winner;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }
}
