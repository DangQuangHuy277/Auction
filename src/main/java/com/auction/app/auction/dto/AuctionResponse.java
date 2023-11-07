package com.auction.app.auction.dto;

import com.auction.app.auction.Auction;
import com.auction.app.auction.Bid;
import com.auction.app.chat.Forum;
import com.auction.app.user.entity.Bidder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
public class AuctionResponse {
    private Long id;

    private Long itemId;
    private Double startingPrice;
    private Instant deadline;
    private Double minimumIncrease;

    private List<BidderDTO> registeredBidders;

    private Long sellerId;

    private List<Bid> bidHistory;

    private Bid highestBid;

    private Forum forum;

    private Auction.Status status;

    public AuctionResponse(Auction auction) {
        this.id = auction.getId();
        this.itemId = auction.getItem().getId();
        this.startingPrice = auction.getStartingPrice();
        this.minimumIncrease = auction.getMinimumIncrease();
        this.deadline = auction.getDeadline();
        this.registeredBidders = auction.getRegisteredBidders().stream().map(BidderDTO::new).toList();
        this.sellerId = auction.getSeller().getId();
        this.bidHistory = auction.getBidHistory();
        this.highestBid = auction.getHighestBid();
        this.forum = auction.getForum();
        this.status = auction.getStatus();
    }
    private class BidderDTO {
        private Long id;
        private String username;
        public BidderDTO(Bidder bidder){
            this.id = bidder.getId();
            this.username = bidder.getUsername();
        }
    }
}
