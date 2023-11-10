package com.auction.app.event.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class AuctionEndEvent extends ApplicationEvent {
    private final Long auctionId;

    public AuctionEndEvent(Object source, Long auctionId) {
        super(source);
        this.auctionId = auctionId;
    }
}
