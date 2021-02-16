package com.gongsung.gallery.user.repository;


import com.gongsung.gallery.User;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
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

  public User findById(Long id) {
    return entityManager.find(User.class, id);
  }

  public User findByEmail(String email) {
    String jpql = "select u from User u where u.email = :email";
    List<User> userEmails = entityManager.createQuery(jpql).setParameter("email", email)
        .getResultList();

    Optional<User> optionalUser = userEmails.stream().filter(user -> user.getEmail().equals(email)).findAny();
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new NullPointerException();
    }
  }

  public boolean existsByEmail(String email) throws Exception{
    return findByEmail(email) == null ? true : false;
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
//        find는 pk만 조회가 됨. email을 조회하는 jpql을 사용하여 다시 짜야함
    entityManager.remove(entityManager.find(User.class, email));
  }

  public void removeByNickName(String nickName) {
    //        find는 pk만 조회가 됨. email을 조회하는 jpql을 사용하여 다시 짜야함
    entityManager.remove(entityManager.find(User.class, nickName));
  }
}
