package com.auction.app.auction;


import com.auction.app.auction.dto.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ViewAuctionController {
    private final ViewAuctionService auctionService;
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

    @GetMapping("/sellers/{sellerId}/auctions/successful")
    public ResponseEntity<?> getSuccessfulAuctionsBySeller(@PathVariable("sellerId") Long sellerId){
        List<AuctionResponse> successfulAuctions = auctionService.getSuccessfulAuctionsBySeller(sellerId);
        return ResponseEntity.ok(successfulAuctions);
    }

    @GetMapping("/sellers/{sellerId}/auctions/sold")
    public ResponseEntity<?> getSoldAuctionsBySeller(@PathVariable("sellerId") Long sellerId){
        List<AuctionResponse> soldAuctions = auctionService.getSoldAuctionsBySeller(sellerId);
        return ResponseEntity.ok(soldAuctions);
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

    @GetMapping("/bidders/{bidderId}/auctions/paid")
    public ResponseEntity<?> getPaidAuctionsByBidder(@PathVariable("bidderId") Long bidderId){
        List<AuctionResponse> paidAuctions = auctionService.getPaidAuctionsByBidder(bidderId);
        return ResponseEntity.ok(paidAuctions);
    }
}
