package com.premknockout.api.model.knockout;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/*
 * @created 09/07/2020 - 20
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Entity
@Table(name = "knockout_rounds_picked")
public class KnockoutRoundsPicked implements Serializable {

    private static final long serialVersionUID = -3638581085137650268L;

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
