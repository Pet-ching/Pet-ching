package com.mandarin.petching.repository;

import com.mandarin.petching.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatRoomId(Long roomId);
}
