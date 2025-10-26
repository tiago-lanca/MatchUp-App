package com.matchup.api.matchup_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @Column(name = "adr_id")
    private UUID Id = UUID.randomUUID();
    @Column(name = "adr_street")
    private String Street;
    @Column(name = "adr_city")
    private String City;
    @Column(name = "adr_zipCode")
    private String ZipCode;
    @Column(name = "adr_latitude")
    private double Latitude;
    @Column(name = "adr_longitude")
    private double Longitude;
}
