package com.auction.app.bid;

import com.auction.app.auction.Auction;
import com.auction.app.auction.repository.AuctionRepository;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.exception.ConditionNotMetException;
import com.auction.app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BidService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public Bid createNewBid(Long bidderId, Long auctionId, Bid bid) {
        Bidder bidder = (Bidder) userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("The bidder is not found"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The auction is not found"));
        if (!auction.getRegisteredBidders().contains(bidder))
            throw new ConditionNotMetException("This Bidder has not registered to participate in this auction");
        if (auction.getHighestBid() != null){
            if (auction.getHighestBid().getPrice() >= bid.getPrice())
                throw new ConditionNotMetException("This bid is not higher than the current highest bid");
            if (bid.getPrice() < auction.getHighestBid().getPrice() + auction.getMinimumIncrease())
                throw new ConditionNotMetException("This bid does not guarantee a minimum increase over the current highest bid");
        }
        else if (bid.getPrice() < auction.getStartingPrice())
            throw new ConditionNotMetException("This bid does not guarantee the auction's minimum price");
        bid.setBidder(bidder);
        bid.setTimestamp(Instant.now());
        auction.setHighestBid(bid);
        bid = bidRepository.save(bid);
        auctionRepository.save(auction);
        return bid;
    }
}
