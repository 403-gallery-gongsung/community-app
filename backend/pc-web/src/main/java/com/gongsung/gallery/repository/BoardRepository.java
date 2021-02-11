package com.gongsung.gallery.repository;

import com.gongsung.gallery.domain.Board;
import com.gongsung.gallery.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;


    public void save(Board board){
        em.persist(board);
    }

    public Board findBoard(Long id){
        return em.find(Board.class,id);
    }

    public void delete(Long id){
        Board board = em.find(Board.class, id);
        User user = board.getUser();
        user.getBoards().remove(board);
        em.remove(board);
    }

    public List<Board> findAll(){
        return em.createQuery(" select o from Board o ",Board.class)
                .getResultList();
    }



}
