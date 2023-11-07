package com.auction.app.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>{
    @Override
    Optional<Auction> findById(Long id);
}