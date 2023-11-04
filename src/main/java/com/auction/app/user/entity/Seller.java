package com.auction.app.user.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Seller extends User {

//    @Builder
    public Seller(String username, String email, Integer phone, String password) {
        super(username, email, phone, password);
    }
}
