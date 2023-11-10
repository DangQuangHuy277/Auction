package com.auction.app.auction;


import com.auction.app.auction.dto.ApproveRequest;
import com.auction.app.auction.dto.AuctionRequest;
import com.auction.app.auction.dto.AuctionResponse;
import com.auction.app.bid.Bid;
import com.auction.app.checkOut.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/bidders/{bidderId}/auctions/{auctionId}/register")
    public ResponseEntity<?> registerBidderToAuction(@PathVariable("bidderId") Long bidderId,
                                                     @PathVariable("auctionId") Long auctionId) {
        AuctionResponse auctionResponse = auctionService.registerBidder(bidderId, auctionId);
        return ResponseEntity.ok(auctionResponse);
    }

    @PostMapping("/sellers/{sellerId}/auctions")
    public ResponseEntity<?> createNewAuction(@PathVariable("sellerId") Long sellerId, @RequestBody @Valid AuctionRequest auction) {
        AuctionResponse newAuction = auctionService.createNewAuction(sellerId, auction);
        return ResponseEntity.created(URI.create("/api/v1/sellers/" + sellerId + "/auctions/" + newAuction.getId()))
                .body(newAuction);
    }

    @PatchMapping("/auctions/{auctionId}")
    public ResponseEntity<?> updateAuction(@PathVariable("auctionId") Long auctionId, @RequestBody @Valid AuctionRequest auctionRequest) {
        AuctionResponse updatedAuction = auctionService.updateAuction(auctionId, auctionRequest);
        return ResponseEntity.ok(updatedAuction);
    }

    @PatchMapping("/auctions/{auctionId}/approval")
    public ResponseEntity<?> approveAuction(@PathVariable("auctionId") Long auctionId, @RequestBody @Valid ApproveRequest approveRequest) {
        auctionService.approveAuction(auctionId, approveRequest);
        return ResponseEntity.ok().build();
    }
}
