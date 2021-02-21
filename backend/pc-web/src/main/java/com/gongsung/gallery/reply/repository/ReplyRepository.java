package com.gongsung.gallery.reply.repository;

import com.gongsung.gallery.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ReplyRepository {

    private final EntityManager em;

    public void save(Reply reply) {
        if(reply.getId() != null)
            em.merge(reply);
        else
            em.persist(reply);
    }

    public Reply findOne(Long id) {
        return em.find(Reply.class, id);
    }

    public List<Reply> findAll() {
        return em.createQuery("select c from Reply c")
            .getResultList();
    }

    public void remove(Long id) {
        em.remove(em.find(Reply.class, id));
    }

    public List<Reply> findByAuthor(String author) {
        return em.createQuery("select c from Reply c where c.author = :name", Reply.class)
            .setParameter("name", author)
            .getResultList();
    }
}
