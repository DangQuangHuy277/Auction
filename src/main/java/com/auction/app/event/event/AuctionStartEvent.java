package com.auction.app.event.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuctionStartEvent extends ApplicationEvent {
    private final Long auctionId;

    public AuctionStartEvent(Object source, Long auctionId) {
        super(source);
        this.auctionId = auctionId;
    }
}
