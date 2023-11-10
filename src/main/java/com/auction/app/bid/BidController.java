package com.auction.app.bid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
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
                                 @RequestBody Bid bid){
        Bid newBid = bidService.createNewBid(bidderId,auctionId,bid);
        messagingTemplate.convertAndSend("/topic/auctions." + auctionId + ".newBid", newBid);
        return ResponseEntity.ok(bid);
    }
}
