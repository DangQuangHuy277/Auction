package com.auction.app.event.publisher;

import com.auction.app.event.event.AuctionStartEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.TimerTask;

@RequiredArgsConstructor
public class AuctionStartEventPublisher extends TimerTask {
    private final Long auctionId;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void run() {
        eventPublisher.publishEvent(new AuctionStartEvent(this, auctionId));
    }
}
