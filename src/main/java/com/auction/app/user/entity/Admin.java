package com.auction.app.user.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Admin extends User {
    public Admin(Long id, String username, String email, Integer phone, String password) {
        super(id, username, email, phone, password);
    }
//    List<Auction> pendingAuctions;
}
