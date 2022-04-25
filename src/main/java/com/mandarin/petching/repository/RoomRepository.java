package com.mandarin.petching.repository;

import com.mandarin.petching.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByPetOwnerId(Long petOwnerId);
    List<ChatRoom> findByPetSitterId(Long petSitterId);
    ChatRoom findByPetOwnerIdAndPetSitterId(Long petOwnerId, Long petSitterId);
}
