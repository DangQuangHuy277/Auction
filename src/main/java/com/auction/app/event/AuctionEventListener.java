package com.auction.app.event;


import com.auction.app.auction.Auction;
import com.auction.app.auction.AuctionRepository;
import com.auction.app.checkOut.Order;
import com.auction.app.checkOut.OrderRepository;
import com.auction.app.event.event.AuctionEndEvent;
import com.auction.app.event.event.AuctionStartEvent;
import com.auction.app.item.Item;
import com.auction.app.item.ItemRepository;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.utils.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuctionEventListener {
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(AuctionEventListener.class);


    @EventListener
    public void handleAuctionStartEvent(AuctionStartEvent startEvent) {
        Auction auction = findAuctionById(startEvent.getAuctionId());
        if (auction == null) return;
        if (auction.getStatus().equals(Auction.Status.APPROVED)) {
            auction.setStatus(Auction.Status.ACTIVE);
            logger.info("Auction " + startEvent.getAuctionId() + "start successful");
        } else if (auction.getStatus().equals(Auction.Status.PENDING)) {
            auction.setStatus(Auction.Status.DISAPPROVED);
            auction.getItem().setStatus(Item.Status.IN_INVENTORY);
            itemRepository.save(auction.getItem());
            logger.info("Auction " + startEvent.getAuctionId() + "is automatically disapproved due to timeout");
        }
        auctionRepository.save(auction);
    }

    @EventListener
    @Transactional
    public void handleAuctionEndEvent(AuctionEndEvent endEvent) {
        Auction auction = findAuctionById(endEvent.getAuctionId());
        if (auction == null || !auction.getStatus().equals(Auction.Status.ACTIVE)) return;
        auction.setStatus(Auction.Status.CLOSED);
        auctionRepository.save(auction);
        logger.info("Auction" + endEvent.getAuctionId() + "end successful");
        if (auction.getHighestBid() == null){
            auction.getItem().setStatus(Item.Status.IN_INVENTORY);
            itemRepository.save(auction.getItem());
        }

        //create order for the wining bidder
        Order order = new Order(auction.getItem(), auction.getHighestBid().getPrice());
        Bidder winningBidder = auction.getHighestBid().getBidder();
        Hibernate.initialize(winningBidder.getCart());
        winningBidder.getCart().add(order);
        order = orderRepository.save(order);
        winningBidder = userRepository.save(winningBidder);
        logger.info("Create order " + order.getId() + " of the bidder " + winningBidder.getId());
    }

    private Auction findAuctionById(Long auctionId) {
        Auction auction = null;
        try {
            auction = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Can't find auction with this id"));
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            logger.error("The auction need to find have id: " + auctionId);
        }
        return auction;
    }
}
