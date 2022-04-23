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

        ChatRoom findChatRoom = roomRepository.findByBuyerIdAndSellerId(buyerId, sellerId);

        if (findChatRoom == null) {
            ChatRoom chatRoom = ChatRoom.createRoom(buyerId, sellerId);
            roomRepository.save(chatRoom);
            return chatRoom.getId();
        }

        return findChatRoom.getId();
    }

    public List<ChatMessage> findAllChatByRoomId(Long roomId) {
        return chatRepository.findAllByChatRoomId(roomId);
    }

    public ChatMessage createChat(Long roomId, String sender, String message) {

        ChatRoom chatRoom = roomRepository.findById(roomId).get();
        return chatRepository.save(ChatMessage.createChatMessage(chatRoom, sender, message));
    }
}
