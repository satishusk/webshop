package com.kazhukov.webshop.data.repositories;

import com.kazhukov.webshop.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "update webshop_db.users set active = false where user_id = :id")
  void deactivate(@Param("id") Long id);

  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "update webshop_db.users set active = true where user_id = :id")
  void activate(@Param("id") Long id);

}
