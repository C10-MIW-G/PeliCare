package com.makeitworkch10.pacemakers.pelicare.user;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE user user SET user.password = ?1 WHERE user.email = ?2",
            nativeQuery = true)
    void updateUserPassword(String encodedPassword, String userEmail);
}
