package com.gongsung.gallery.repository;


import com.gongsung.gallery.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user){
        em.persist(user);

        return user.getId();
    }

    public User find(Long id){
        return em.find(User.class,id);
    }
}