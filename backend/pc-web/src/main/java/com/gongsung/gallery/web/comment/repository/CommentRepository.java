package com.gongsung.gallery.web.comment.repository;

import com.gongsung.gallery.web.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id){
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll(){
        return em.createQuery("select c from Comment c")
                .getResultList();
    }

    public void remove(Long id){
        em.remove(em.find(Comment.class, id));
    }

    public List<Comment> findByAuthor(String author){
        return em.createQuery("select c from Comment c where c.author = :name", Comment.class)
                .setParameter("name", author)
                .getResultList();
    }

    /*public List<com.gongsung.gallery.core.Comment> findByBoardId(Long id){
        return em.createQuery(
                "select c from com.gongsung.gallery.core.Comment c " +
                        "where c.board"
        )
    }*/
}
