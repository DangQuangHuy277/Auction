package com.auction.app.auction;

import com.auction.app.user.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>{
    @Override
    Optional<Auction> findById(Long id);

    List<Auction> findBySeller_Id(Long id);

    List<Auction> findByRegisteredBidders_Id(Long id);
}