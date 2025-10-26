package com.matchup.api.matchup_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @Column(name = "cou_id")
    private UUID id;
    @Column(name = "cou_name")
    private String name;
    @Column(name = "cou_phoneCode")
    private String PhoneCode;
    @Column(name = "cou_icon")
    private byte[] flagIcon = null;


    // <editor-fold desc="Getters">
    public String getName() { return name; }

    // </editor-fold>

    // <editor-fold desc="Setters">
    public void setName(String name) { this.name = name; }

    // </editor-fold>

}
