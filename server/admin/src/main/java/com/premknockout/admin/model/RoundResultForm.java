package com.premknockout.admin.model;

import java.io.Serializable;
import java.util.List;

/*
 * @created 14/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class RoundResultForm implements Serializable {

  private static final long serialVersionUID = -669072617368744863L;

  private String knockoutId;

  private String[] teams;

  public String getKnockoutId() {
    return knockoutId;
  }

  public void setKnockoutId(String knockoutId) {
    this.knockoutId = knockoutId;
  }

  public String[] getTeams() {
    return teams;
  }

  public void setTeams(String[] teams) {
    this.teams = teams;
  }
}
