package com.auction.app.auction;


import com.auction.app.auction.dto.AuctionRequest;
import com.auction.app.auction.dto.AuctionResponse;
import com.auction.app.bid.Bid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/auctions")
    public ResponseEntity<?> getAllAuctions() {
        List<AuctionResponse> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/sellers/{sellerId}/auctions/approved")
    public ResponseEntity<?> getApprovedAuctionsBySeller(@PathVariable("sellerId") Long sellerId){
        List<AuctionResponse> approvedAuctions = auctionService.getApprovedAuctionsBySeller(sellerId);
        return ResponseEntity.ok(approvedAuctions);
    }

    @GetMapping("/bidders/{bidderId}/auctions/registered")
    public ResponseEntity<?> getRegisteredAuctionsByBidder(@PathVariable("bidderId") Long bidderId){
        List<AuctionResponse> registeredAuctions = auctionService.getRegisteredAuctionsByBidder(bidderId);
        return ResponseEntity.ok(registeredAuctions);
    }

    @GetMapping("/bidders/{bidderId}/auctions/winning")
    public ResponseEntity<?> getWiningAuctionsByBidder(@PathVariable("bidderId") Long bidderId){
        List<AuctionResponse> winningAuctions = auctionService.getWiningAuctionsByBidder(bidderId);
        return ResponseEntity.ok(winningAuctions);
    }

    @PostMapping("/bidders/{bidderId}/auctions/{auctionId}/register")
    public ResponseEntity<?> registerBidderToAuction(@PathVariable("bidderId") Long bidderId,
                                                     @PathVariable("auctionId") Long auctionId){
        AuctionResponse auctionResponse = auctionService.registerBidder(bidderId, auctionId);
        return ResponseEntity.ok(auctionResponse);
    }

    @PostMapping("/sellers/{sellerId}/auctions")
    public ResponseEntity<?> createNewAuction(@PathVariable("sellerId") Long sellerId,@RequestBody @Valid AuctionRequest auction) {
        AuctionResponse newAuction = auctionService.createNewAuction(sellerId, auction);
        return ResponseEntity.created(URI.create("/api/v1/sellers/" + sellerId + "/auctions/" + newAuction.getId()))
                .body(newAuction);
    }

    @PatchMapping("/auctions/{auctionId}")
    public ResponseEntity<?> updateAuction(@PathVariable("auctionId") Long auctionId,@RequestBody @Valid AuctionRequest auctionRequest){
        AuctionResponse updatedAuction = auctionService.updateAuction(auctionId, auctionRequest);
        return ResponseEntity.ok(updatedAuction);
    }
}
