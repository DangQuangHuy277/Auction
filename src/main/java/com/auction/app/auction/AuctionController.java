package com.auction.app.auction;


import com.auction.app.auction.dto.AuctionRequest;
import com.auction.app.auction.dto.AuctionResponse;
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
