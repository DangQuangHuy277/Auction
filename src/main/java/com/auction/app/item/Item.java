package com.auction.app.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Item {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer age;
    private String origin;
    private String material;
//    private Instant createdDate;
    private Status status;

    enum Status{
        IN_INVENTORY,
        AUCTIONING,
        SOLD
    }
}


