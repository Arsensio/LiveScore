package com.example.livescore.repository;

import com.example.livescore.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByUsername(String username);

    @Modifying
    @Query("update UserEntity u set u.userPassword = ?2 where u.username = ?1")
    void updateUserPassword(String username, String password);

    @Modifying
    @Query("update UserEntity u set u.username = ?2 where u.username = ?1")
    void updateUsername(String oldUsername, String newUsername);
}
