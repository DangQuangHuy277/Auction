package com.auction.app.user.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Admin extends User {
    public Admin(String username, String email, String phone, String password, Boolean enabled) {
        super(username, email, phone, password, enabled);
    }
//    List<Auction> pendingAuctions;
}
