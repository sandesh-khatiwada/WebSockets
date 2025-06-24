package com.websocket.chatApp.repository.privatemessage;

import com.websocket.chatApp.model.PrivateMessage;
import com.websocket.chatApp.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class PrivateMessageRepositoryCustomImpl implements PrivateMessageRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    public List<PrivateMessage> findTopNMessages(User sender, User receiver, int limit) {
        TypedQuery<PrivateMessage> query = entityManager.createQuery(
                "SELECT m FROM PrivateMessage m WHERE " +
                        "((m.sender = :sender AND m.receiver = :receiver) OR " +
                        "(m.sender = :receiver AND m.receiver = :sender)) " +
                        "ORDER BY m.createdAt DESC",
                PrivateMessage.class
        );
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<PrivateMessage> findMessagesBefore(User sender, User receiver, LocalDateTime createdAt, int limit) {
        TypedQuery<PrivateMessage> query = entityManager.createQuery(
                "SELECT m FROM PrivateMessage m WHERE " +
                        "((m.sender = :sender AND m.receiver = :receiver) OR " +
                        "(m.sender = :receiver AND m.receiver = :sender)) " +
                        "AND m.createdAt < :createdAt " +
                        "ORDER BY m.createdAt DESC",
                PrivateMessage.class
        );
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);
        query.setParameter("createdAt", createdAt);
        query.setMaxResults(limit);
        return query.getResultList();
    }

}
