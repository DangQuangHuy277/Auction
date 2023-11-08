package com.auction.app.auction;

import com.auction.app.auction.dto.AuctionRequest;
import com.auction.app.auction.dto.AuctionResponse;
import com.auction.app.bid.Bid;
import com.auction.app.item.Item;
import com.auction.app.item.ItemRepository;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.utils.exception.ResourceAlreadyExistException;
import com.auction.app.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<AuctionResponse> getAllAuctions() {
        return auctionRepository.findAll().stream().map(AuctionResponse::new).toList();
    }

    public AuctionResponse createNewAuction(Long sellerId, AuctionRequest auctionRequest) {
        Item item = itemRepository.findById(auctionRequest.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("The Item is not found"));
        Seller seller = (Seller) userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("The Seller is not found"));

        try {
            Auction newAuction = Auction.builder()
                    .item(item)
                    .startingPrice(auctionRequest.getStartingPrice())
                    .minimumIncrease(auctionRequest.getMinimumIncrease())
                    .deadline(auctionRequest.getDeadline())
                    .seller(seller)
                    .status(Auction.Status.PENDING).build();
            return new AuctionResponse(auctionRepository.save(newAuction));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceAlreadyExistException("This item has already been auctioned before");
        }
    }


    public AuctionResponse updateAuction(Long auctionId, AuctionRequest auctionRequest) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The Auction is not exist"));
        if (!auctionRequest.getItemId().equals(auction.getItem().getId())) {
            Item newItem = itemRepository.findById(auctionRequest.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("This Item is not exists"));
            auction.setItem(newItem);
        }
        if (auctionRequest.getDeadline() != null) {
            auction.setDeadline(auctionRequest.getDeadline());
        }
        if (auctionRequest.getMinimumIncrease() != null) {
            auction.setMinimumIncrease(auctionRequest.getMinimumIncrease());
        }
        if (auctionRequest.getStartingPrice() != null) {
            auction.setStartingPrice(auctionRequest.getStartingPrice());
        }
        return new AuctionResponse(auctionRepository.save(auction));
    }

    public List<AuctionResponse> getApprovedAuctionsBySeller(Long sellerId) {
        return auctionRepository.findBySeller_Id(sellerId).stream().map(AuctionResponse::new).toList();
    }

    public List<AuctionResponse> getRegisteredAuctionsByBidder(Long bidderId) {
        return auctionRepository.findByRegisteredBidders_Id(bidderId).stream().map(AuctionResponse::new).toList();
    }

    public AuctionResponse registerBidder(Long bidderId, Long auctionId) {
        Bidder bidder = (Bidder) userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("The bidder is not found"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The auction is not found"));
        auction.getRegisteredBidders().add(bidder);
        return new AuctionResponse(auctionRepository.save(auction));
    }

    public List<AuctionResponse> getWiningAuctionsByBidder(Long bidderId) {
        return auctionRepository.findByRegisteredBidders_Id(bidderId).stream()
                .filter(auction -> auction.getStatus() == Auction.Status.CLOSED &&
                        auction.getHighestBid().getBidder().getId().equals(bidderId))
                .map(AuctionResponse::new).toList();
    }

}
