package com.matchup.api.matchup_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Sports")
public class Sport {
    @Id
    @Column(name = "spo_id")
    private UUID id;
    @Column(name = "spo_name")
    private String name;
    @Column(name = "spo_icon")
    private byte[] icon = null;
}
