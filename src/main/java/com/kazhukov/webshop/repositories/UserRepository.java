package com.kazhukov.webshop.repositories;

import com.kazhukov.webshop.entities.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Modifying
  @Query(nativeQuery = true, value = "update webshop_db.users set active = false where user_id = ?1")
  void deactivate(long id);

  @Modifying
  @Query(nativeQuery = true, value = "update webshop_db.users set active = true where user_id = ?1")
  void activate(long id);
}
