package com.mandarin.petching.repository;

import com.mandarin.petching.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByBuyerIdAndSellerId(Long buyerId, Long sellerId);
}
