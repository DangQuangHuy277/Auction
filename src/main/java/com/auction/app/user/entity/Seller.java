package com.auction.app.user.entity;

import com.auction.app.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Seller extends User {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    List<Item> itemInventory = new ArrayList<>();

    //    @Builder
    public Seller(String username, String email, String phone, String password, Boolean enabled) {
        super(username, email, phone, password, enabled);
    }
}
