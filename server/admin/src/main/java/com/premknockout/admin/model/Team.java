package com.premknockout.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 14/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "team")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team implements Serializable {

  private static final long serialVersionUID = -8926429845963065357L;

  @Id
  @GeneratedValue
  @JsonIgnore
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @Column(name = "name")
  private String name;

  @JsonIgnore
  @Column(name = "abbreviation")
  private String abbreviation;

  @JsonIgnore
  @Column(name = "img_url")
  private String crestUrl;

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

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getCrestUrl() {
    return crestUrl;
  }

  public void setCrestUrl(String crestUrl) {
    this.crestUrl = crestUrl;
  }
}
