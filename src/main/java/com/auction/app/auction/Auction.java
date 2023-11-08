package com.auction.app.auction;

import com.auction.app.bid.Bid;
import com.auction.app.chat.Forum;
import com.auction.app.item.Item;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Item item;
    private Double startingPrice;
    private Instant deadline;
    private Double minimumIncrease;
    @OneToMany
    private List<Bidder> registeredBidders = new ArrayList<>();
    @OneToOne
    private Seller seller;
    @OneToMany
    private List<Bid> bidHistory = new ArrayList<>();
    @OneToOne
    private Bid highestBid;
    @OneToOne
    private Forum forum = new Forum();

    private Status status;

    public enum Status {
        PENDING,
        APPROVED,
        ACTIVE,
        CLOSED,
        SOLD
    }

}
