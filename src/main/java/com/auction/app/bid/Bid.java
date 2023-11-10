package com.auction.app.bid;

import com.auction.app.user.entity.Bidder;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bidder_id")
    @JsonIgnore
    private Bidder bidder;

//    private Auction auction;
}
