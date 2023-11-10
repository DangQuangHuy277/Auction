package com.auction.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class User {
    @Id
    @GeneratedValue
    protected Long id;
    @Column(unique = true, nullable = false)
    protected String username;
    @Column(unique = true, nullable = false)
    protected String email;
    protected String phone;
    @JsonIgnore
    protected String password;

    private Boolean enabled;

    public User(String username, String email, String phone, String password, Boolean enabled) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
