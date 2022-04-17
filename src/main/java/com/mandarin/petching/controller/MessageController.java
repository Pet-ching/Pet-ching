package com.mandarin.petching.controller;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @MessageMapping("/chat/room/{roomId}")
    @SendTo("/topic/chat/room/{roomId}")
    public ChatMessage enter(@DestinationVariable Long roomId, ChatMessage message) {

        ChatMessage chatMessage = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return chatMessage;
    }
}
