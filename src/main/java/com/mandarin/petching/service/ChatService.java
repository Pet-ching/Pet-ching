package com.mandarin.petching.service;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.domain.ChatRoom;
import com.mandarin.petching.repository.ChatRepository;
import com.mandarin.petching.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Long createRoom(Long petOwnerId, Long petSitterId) {

        ChatRoom findChatRoom = roomRepository.findByPetOwnerIdAndPetSitterId(petOwnerId, petSitterId);

        String petOwnerName = memberRepository.getById(petOwnerId).getUserName();
        String petSitterName = memberRepository.getById(petSitterId).getUserName();

        if (findChatRoom == null) {
            ChatRoom chatRoom = ChatRoom.createRoom(petOwnerId, petSitterId, petOwnerName, petSitterName);
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
