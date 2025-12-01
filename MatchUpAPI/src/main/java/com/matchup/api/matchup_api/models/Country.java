package com.matchup.api.matchup_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.UUID;

@Data
@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @GeneratedValue
    @Column(name = "cou_id")
    private UUID id;
    @Column(name = "cou_name")
    private String name;
    @Column(name = "cou_phoneCode")
    private String PhoneCode;
    @Column(name = "cou_icon")
    private String flagIcon;


    public Country(){ }

    public Country(String name, String phoneCode, String flagIcon) {
        this.name = name;
        this.PhoneCode = phoneCode;
        this.flagIcon = flagIcon;
    }

}

