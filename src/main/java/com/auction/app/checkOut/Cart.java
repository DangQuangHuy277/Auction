package com.auction.app.checkOut;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Cart {
    @Id
    private Long id;
    @OneToMany
    List<Order> orders;
}
