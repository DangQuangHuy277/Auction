package com.auction.app.auction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Bid {
    @Id
    private Long id;
    private Double price;
    private Instant timestamp;
//    private Bidder bidder;
//    private Auction auction;
}
