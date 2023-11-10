package com.auction.app.checkOut;

import com.auction.app.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true)
    private Item item;
    private Double price;

    public Order(Item item, Double price) {
        this.item = item;
        this.price = price;
    }

//    private Status status;
//
//    enum Status{
//
//    }
}


