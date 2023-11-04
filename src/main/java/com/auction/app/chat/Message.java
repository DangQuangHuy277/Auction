package com.auction.app.chat;

import com.auction.app.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Message {
    @Id
    private Long id;
    private String content;
    private Instant timestamp;
    @ManyToOne
    private User sender;
}
