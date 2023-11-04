package com.auction.app.user.entity;

import com.auction.app.checkOut.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Bidder extends User {
    public Bidder(String username, String email, Integer phone, String password) {
        super(username, email, phone, password);
    }

//    @OneToOne
//    private Cart cart;
//    @ManyToMany
//    List<Auction> participatedAuctions;
}
