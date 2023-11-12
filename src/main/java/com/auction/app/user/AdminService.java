package com.auction.app.user;

import com.auction.app.auction.repository.AuctionRepository;
import com.auction.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    public HashMap<String, Long> getStatistical() {
        Long numberOfBidders = userRepository.countAllBidders();
        Long numberOfSellers = userRepository.countAllSellers();
        Long numberOfAuctions = auctionRepository.count();
        HashMap<String, Long> statistical = new HashMap<>();
        statistical.put("numberOfBidders", numberOfBidders);
        statistical.put("numberOfSellers", numberOfSellers);
        statistical.put("numberOfAuctions", numberOfAuctions);
        return statistical;
    }
}
