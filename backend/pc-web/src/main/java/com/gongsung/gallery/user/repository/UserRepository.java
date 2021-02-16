package com.gongsung.gallery.user.repository;


import com.gongsung.gallery.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public Long count() {
        List<User> list = findAll();
        return Long.valueOf(list.size());
    }

    public User findByEmail(String email) {
        return entityManager.find(User.class, email);
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null ? true : false;
    }

    public User findByNickName(String nickName) {
        return entityManager.find(User.class, nickName);
    }

    public boolean existsByNickName(String nickName) {
        return findByNickName(nickName) != null ? true : false;
    }

    public List<User> findAll() {
        String jpql = "select u from User u";
        return entityManager.createQuery(jpql)
            .getResultList();
    }

    public void removeByEmail(String email) {
        entityManager.remove(entityManager.find(User.class,email));
    }

    public void removeByNickName(String nickName) {
        entityManager.remove(entityManager.find(User.class,nickName));
    }


}
