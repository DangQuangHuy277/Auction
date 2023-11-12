package com.auction.app.user.entity;


import com.auction.app.payment.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bidder extends User {
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "bidder_id")
    private List<Order> cart = new ArrayList<>();

    public Bidder(String username, String email, String phone, String password, Boolean enabled) {
        super(username, email, phone, password, enabled);
    }

//    @OneToOne
//    private Cart cart;
//    @ManyToMany
//    List<Auction> participatedAuctions;
}
