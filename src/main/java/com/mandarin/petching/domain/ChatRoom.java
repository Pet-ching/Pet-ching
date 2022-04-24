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

    private Long petOwnerId;
    private String petOwnerName;

    private Long petSitterId;
    private String petSitterName;

    public static ChatRoom createRoom(Long petOwnerId,
                                      Long petSitterId,
                                      String petOwnerName,
                                      String petSitterName) {

        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setPetOwnerId(petOwnerId);
        chatRoom.setPetSitterId(petSitterId);
        chatRoom.setPetOwnerName(petOwnerName);
        chatRoom.setPetSitterName(petSitterName);

        return chatRoom;
    }
}
