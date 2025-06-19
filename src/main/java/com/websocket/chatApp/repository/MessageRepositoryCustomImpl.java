package com.websocket.chatApp.repository;

import com.websocket.chatApp.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.time.LocalDateTime;
import java.util.List;

public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> findTopNMessages(int limit) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m ORDER BY m.createdAt DESC",
                Message.class
        );
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Message> findMessagesBefore(LocalDateTime createdAt, int limit) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.createdAt < :createdAt ORDER BY m.createdAt DESC",
                Message.class
        );
        query.setParameter("createdAt", createdAt);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}