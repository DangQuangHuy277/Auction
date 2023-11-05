package com.auction.app.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Integer age;
    private String origin;
    private String material;
//    private Instant createdDate;
    @Enumerated
    private Status status;

    enum Status{
        IN_INVENTORY,
        AUCTIONED
    }
}


