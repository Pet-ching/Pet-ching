package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buyerId;

    private Long sellerId;

    public static ChatRoom createRoom(Long buyerId, Long sellerId) {

        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setBuyerId(buyerId);
        chatRoom.setSellerId(sellerId);

        return chatRoom;
    }
}
