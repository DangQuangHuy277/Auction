package com.auction.app.user.entity;

import com.auction.app.checkOut.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bidder extends User {
    @OneToMany
    @JoinColumn()
    private List<Order> cart;

    public Bidder(String username, String email, Integer phone, String password) {
        super(username, email, phone, password);
    }

//    @OneToOne
//    private Cart cart;
//    @ManyToMany
//    List<Auction> participatedAuctions;
}
