package com.auction.app.checkOut;

import com.auction.app.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Order {
    @Id
    private Long id;
    @OneToOne
    private Item item;
    private Double price;

    private Status status;

    enum Status{

    }
}


