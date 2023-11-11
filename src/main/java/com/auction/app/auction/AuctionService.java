package com.auction.app.auction;

import com.auction.app.auction.dto.ApproveRequest;
import com.auction.app.auction.dto.AuctionRequest;
import com.auction.app.auction.dto.AuctionResponse;
import com.auction.app.checkOut.OrderRepository;
import com.auction.app.event.publisher.AuctionEndEventPublisher;
import com.auction.app.event.publisher.AuctionStartEventPublisher;
import com.auction.app.item.Item;
import com.auction.app.item.ItemRepository;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.utils.exception.ConditionNotMetException;
import com.auction.app.utils.exception.ResourceAlreadyExistException;
import com.auction.app.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Timer;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public AuctionResponse createNewAuction(Long sellerId, AuctionRequest auctionRequest) {
        if (!auctionRequest.getStartingTime().isBefore(auctionRequest.getDeadline()))
            throw new ConditionNotMetException("The deadline must be after the starting time");
        if(!auctionRequest.getStartingTime().isAfter(Instant.now()))
            throw new ConditionNotMetException("The starting time must be after current time");

        Item item = itemRepository.findById(auctionRequest.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("The Item is not found"));
        Seller seller = (Seller) userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("The Seller is not found"));

        if (!item.getStatus().equals(Item.Status.IN_INVENTORY)) {
            throw new ConditionNotMetException("The item is not in inventory");
        }

        try {
            item.setStatus(Item.Status.AUCTIONED);
            Auction newAuction = Auction.builder()
                    .item(item)
                    .startingPrice(auctionRequest.getStartingPrice())
                    .startingTime(auctionRequest.getStartingTime())
                    .minimumIncrease(auctionRequest.getMinimumIncrease())
                    .deadline(auctionRequest.getDeadline())
                    .seller(seller)
                    .status(Auction.Status.PENDING).build();
            newAuction = auctionRepository.save(newAuction);
            Timer timer = new Timer();
            timer.schedule(new AuctionStartEventPublisher(newAuction.getId(), eventPublisher), Date.from(newAuction.getStartingTime()));
            timer.schedule(new AuctionEndEventPublisher(newAuction.getId(), eventPublisher), Date.from(newAuction.getDeadline()));
            return new AuctionResponse(newAuction);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceAlreadyExistException("This item has already been auctioned before");
//            throw new ConditionNotMetException(ex.getMessage());
        }
    }


    public AuctionResponse updateAuction(Long auctionId, AuctionRequest auctionRequest) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The Auction is not exist"));
        if(auction.getStatus() != Auction.Status.PENDING)
            throw new ConditionNotMetException("Auctions can only be updated while pending");
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


    public AuctionResponse registerBidder(Long bidderId, Long auctionId) {
        Bidder bidder = (Bidder) userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("The bidder is not found"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The auction is not found"));
        if (auction.getStatus().equals(Auction.Status.PENDING) || auction.getStatus().equals(Auction.Status.DISAPPROVED))
            throw new ConditionNotMetException("Can't register to pending or disapproved auction!");
        auction.getRegisteredBidders().add(bidder);
        return new AuctionResponse(auctionRepository.save(auction));
    }

    public void approveAuction(Long auctionId, ApproveRequest approveRequest) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("The auction is not found"));
        if (!auction.getStatus().equals(Auction.Status.PENDING))
            throw new ConditionNotMetException("This auction is not in pending list");
//        auction.setStatus(approveRequest.getApprove() ? Auction.Status.APPROVED : Auction.Status.DISAPPROVED);
        if (approveRequest.getApprove()){
            auction.setStatus(Auction.Status.APPROVED);
        }
        else {
            auction.setStatus(Auction.Status.DISAPPROVED);
            auction.getItem().setStatus(Item.Status.IN_INVENTORY);
            itemRepository.save(auction.getItem());
        }
        auctionRepository.save(auction);

    }
}
