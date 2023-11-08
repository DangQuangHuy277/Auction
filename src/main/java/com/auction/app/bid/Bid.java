package com.auction.app.bid;

import com.auction.app.user.entity.Bidder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Double price;
    private Instant timestamp;
    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private Bidder bidder;
//    private Auction auction;
}
