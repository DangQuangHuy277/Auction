package com.auction.app.auction;

import com.auction.app.bid.Bid;
import com.auction.app.chat.Forum;
import com.auction.app.item.Item;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private Instant startingTime;
    private Instant deadline;
    private Double minimumIncrease;

    @ManyToMany
    @JoinTable(name = "Auction_registeredBidders",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "registeredBidders_id"))
    private List<Bidder> registeredBidders = new ArrayList<>();

    @ManyToOne
//    @JoinColumn(name = "seller_id")
    private Seller seller;
    @OneToMany
    private List<Bid> bidHistory = new ArrayList<>();
    @OneToOne
    private Bid highestBid;
    @OneToOne
    private Forum forum = new Forum();
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @CreationTimestamp
    private Instant createdTime;

    public enum Status {
        PENDING,
        DISAPPROVED,
        APPROVED,
        ACTIVE,
        CLOSED,
        SOLD
    }

}
