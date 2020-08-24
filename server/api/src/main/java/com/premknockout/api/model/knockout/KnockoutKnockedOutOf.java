package com.premknockout.api.model.knockout;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 10/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "knockout_out_of")
public class KnockoutKnockedOutOf implements Serializable {

    private static final long serialVersionUID = -4616043704218178918L;

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "knockout_id")
    private UUID knockoutId;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getKnockoutId() {
        return knockoutId;
    }

    public void setKnockoutId(UUID knockoutId) {
        this.knockoutId = knockoutId;
    }
}
