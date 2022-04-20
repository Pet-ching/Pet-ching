package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatMessage {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private String sender;

    private String message;

    public static ChatMessage createChatMessage(ChatRoom room, String sender, String message) {

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setChatRoom(room);
        chatMessage.setSender(sender);
        chatMessage.setMessage(message);

        return chatMessage;
    }
}
