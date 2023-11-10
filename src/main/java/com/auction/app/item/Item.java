package com.auction.app.item;

import io.swagger.v3.oas.annotations.Hidden;
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
    @Hidden
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Integer age;
    private String origin;
    private String material;
//    private Instant createdDate;
    @Enumerated
    @Hidden
    private Status status;

    public enum Status{
        IN_INVENTORY,
        AUCTIONED
    }
}


