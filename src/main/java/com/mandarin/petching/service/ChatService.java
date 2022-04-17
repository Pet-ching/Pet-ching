package com.mandarin.petching.service;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.domain.ChatRoom;
import com.mandarin.petching.repository.ChatRepository;
import com.mandarin.petching.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public Long createRoom(Long buyerId, Long sellerId) {
        ChatRoom chatRoom = roomRepository.saveChatRoom(buyerId, sellerId);
        return chatRoom.getId();
    }

    public List<ChatMessage> findAllChatByRoomId(Long roomId) {
        return chatRepository.findAllByChatRoomId(roomId);
    }

    public ChatMessage createChat(Long roomId, String sender, String message) {
        ChatRoom room = roomRepository.findChatRoomById(roomId);
        return chatRepository.save(ChatMessage.createChatMessage(room, sender, message));
    }
}
