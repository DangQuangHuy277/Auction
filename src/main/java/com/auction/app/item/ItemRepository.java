package com.auction.app.item;

import com.auction.app.user.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Override
    Optional<Item> findById(Long id);
}
