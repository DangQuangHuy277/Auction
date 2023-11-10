package com.auction.app.auction.dto;

import com.auction.app.item.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class AuctionRequest {
    @NotNull
    private Long itemId;
    private Double startingPrice;
    private Instant startingTime;
    private Instant deadline;
    private Double minimumIncrease;
}
