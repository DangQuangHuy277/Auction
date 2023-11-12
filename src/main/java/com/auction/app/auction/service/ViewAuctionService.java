package com.auction.app.auction.service;

import com.auction.app.auction.Auction;
import com.auction.app.auction.repository.AuctionRepository;
import com.auction.app.auction.dto.AuctionResponse;
import com.auction.app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewAuctionService {
    private final AuctionRepository auctionRepository;

    public List<AuctionResponse> getAllAuctions() {
        return auctionRepository.findAll().stream().map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getApprovedAuctionsBySeller(Long sellerId) {
        return auctionRepository.findBySeller_Id(sellerId).stream()
                .filter(auction -> !auction.getStatus().equals(Auction.Status.PENDING) &&
                        !auction.getStatus().equals(Auction.Status.DISAPPROVED))
                .map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getSuccessfulAuctionsBySeller(Long sellerId) {
        return auctionRepository.findBySeller_Id(sellerId).stream()
                .filter(auction -> auction.getStatus().equals(Auction.Status.CLOSED) && auction.getHighestBid() != null)
                .map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getSoldAuctionsBySeller(Long sellerId) {
        return auctionRepository.findBySeller_Id(sellerId).stream()
                .filter(auction -> auction.getStatus().equals(Auction.Status.SOLD))
                .map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getRegisteredAuctionsByBidder(Long bidderId) {
        return auctionRepository.findByRegisteredBidders_Id(bidderId).stream().map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getWiningAuctionsByBidder(Long bidderId) {
        return auctionRepository.findByRegisteredBidders_Id(bidderId).stream()
                .filter(auction -> auction.getStatus() == Auction.Status.CLOSED &&
                        auction.getHighestBid().getBidder().getId().equals(bidderId))
                .map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getPaidAuctionsByBidder(Long bidderId) {
        return auctionRepository.findByRegisteredBidders_Id(bidderId).stream()
                .filter(auction -> auction.getHighestBid().getBidder().getId().equals(bidderId) &&
                        auction.getStatus().equals(Auction.Status.SOLD))
                .map(AuctionResponse::new).toList();
    }

    public AuctionResponse getAuctionDetail(Long auctionId) {
        return new AuctionResponse(auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The auction is not found")));
    }
}
