package com.auction.app.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Forum {
    @Id
    private Long id;
    @OneToMany
    private List<Message> messageList;
}
