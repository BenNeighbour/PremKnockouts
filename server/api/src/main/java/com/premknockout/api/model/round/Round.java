package com.premknockout.api.model.round;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premknockout.api.model.fixture.Fixture;
import com.premknockout.api.model.pick.Pick;
import com.premknockout.api.model.round.roundResult.RoundResult;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @created 06/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "round")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Round implements Serializable {

  private static final long serialVersionUID = 7036809630006991569L;

  @Id
  @GeneratedValue
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @Column(name = "round_number")
  private int roundNumber;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = Pick.class, fetch = FetchType.LAZY)
  private List<Pick> picks;

  @OneToOne(cascade = CascadeType.ALL, targetEntity = RoundResult.class, fetch = FetchType.LAZY)
  private RoundResult result;

  @JsonIgnore
  @Column(name = "finished")
  private boolean isFinished;

  @Column(name = "pick_deadline")
  private Date pickDeadline;

  @Column(name = "ending")
  private Date roundEnding;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = Fixture.class, fetch = FetchType.LAZY)
  private List<Fixture> matchesForMatchweek;

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

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public List<Pick> getPicks() {
    return picks;
  }

  public void setPicks(List<Pick> picks) {
    this.picks = picks;
  }

  public RoundResult getResult() {
    return result;
  }

  public void setResult(RoundResult result) {
    this.result = result;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
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

  public Date getPickDeadline() {
    return pickDeadline;
  }

  public void setPickDeadline(Date pickDeadline) {
    this.pickDeadline = pickDeadline;
  }

  public Date getRoundEnding() {
    return roundEnding;
  }

  public void setRoundEnding(Date roundEnding) {
    this.roundEnding = roundEnding;
  }

  public List<Fixture> getMatchesForMatchweek() {
    return matchesForMatchweek;
  }

  public void setMatchesForMatchweek(List<Fixture> matchesForMatchweek) {
    this.matchesForMatchweek = matchesForMatchweek;
  }
}
