package com.auction.app.bid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class BidController {
    private final BidService bidService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/bidders/{bidderId}/auctions/{auctionId}/bid")
    public ResponseEntity<?> bid(@PathVariable("bidderId") Long bidderId,
                                 @PathVariable("auctionId") Long auctionId,
                                 @RequestBody Bid bid) {
        Bid newBid = bidService.createNewBid(bidderId, auctionId, bid);
        messagingTemplate.convertAndSend("/topic/auctions." + auctionId + ".newBid", newBid);
        messagingTemplate.convertAndSend("/topic/auctions." + auctionId + ".notifications",
                "🔔 New Higher Bid Alert! " + newBid.getBidder().getUsername() +
                        " steals the spotlight with an offer of $" + newBid.getPrice() +
                        ", surpassing all other bidders.");
        return ResponseEntity.ok(bid);
    }
}
