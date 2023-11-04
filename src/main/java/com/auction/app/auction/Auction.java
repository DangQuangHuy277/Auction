package com.auction.app.auction;

import com.auction.app.chat.Forum;
import com.auction.app.item.Item;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    private Long id;
    @OneToOne
    private Item item;
    private Double startingPrice;
    private Instant deadline;
    private Double minimumIncrease;
    @OneToMany
    private List<Bidder> participatedBidders;
    @OneToOne
    private Seller seller;
    @OneToMany
    private List<Bid> bidHistory;
    @OneToOne
    private Bid highestBid;
    @OneToOne
    private Forum forum;

    private Status status;

    enum Status {
        PENDING,
        APPROVED,
        ACTIVE,
        CLOSED,
        SOLD
    }

}
