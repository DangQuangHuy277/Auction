package com.auction.app.event.publisher;

import com.auction.app.event.event.AuctionEndEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.TimerTask;

@RequiredArgsConstructor
public class AuctionEndEventPublisher extends TimerTask {
    private final Long auctionId;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void run() {
        eventPublisher.publishEvent(new AuctionEndEvent(this, auctionId));
    }
}
