package com.auction.app.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    protected Long id;
    @Column(unique = true, nullable = false)
    protected String username;
    @Column(unique = true, nullable = false)
    protected String email;
    protected Integer phone;
    protected String password;
}
