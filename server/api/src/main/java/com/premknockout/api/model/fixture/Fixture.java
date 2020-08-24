package com.premknockout.api.model.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 26/07/2020 - 09
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "fixture")
public class Fixture implements Serializable {

  private static final long serialVersionUID = -4726422615525815329L;

  @Id
  @GeneratedValue
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @Column(name = "fixture_name")
  private String fixtureName;

  public Fixture() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFixtureName() {
    return fixtureName;
  }

  public void setFixtureName(String fixtureName) {
    this.fixtureName = fixtureName;
  }
}
