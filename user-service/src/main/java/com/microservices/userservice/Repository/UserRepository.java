package com.microservices.userservice.Repository;

import com.microservices.userservice.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    List<UserEntity> findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<UserEntity> searchUsers(@Param("keyword") String keyword);

}
