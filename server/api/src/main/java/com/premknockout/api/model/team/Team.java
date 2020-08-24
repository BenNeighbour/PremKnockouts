package com.premknockout.api.model.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 06/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "team")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team implements Serializable {

    private static final long serialVersionUID = 5673498344741402227L;

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
