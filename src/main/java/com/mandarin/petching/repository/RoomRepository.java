package com.mandarin.petching.repository;

import com.mandarin.petching.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    public ChatRoom saveChatRoom(Long buyerId, Long sellerId) {
        TypedQuery<ChatRoom> query = em.createQuery("select c from ChatRoom c where c.buyerId=?1 and c.sellerId=?2", ChatRoom.class);
        query.setParameter(1, buyerId);
        query.setParameter(2, sellerId);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            ChatRoom room = ChatRoom.createRoom(buyerId, sellerId);
            em.persist(room);
            return room;
        }
    }

    public ChatRoom findChatRoomById(Long roomId) {
        TypedQuery<ChatRoom> query = em.createQuery("select c from ChatRoom c where c.id=?1", ChatRoom.class);
        query.setParameter(1, roomId);

        return query.getSingleResult();
    }
}
