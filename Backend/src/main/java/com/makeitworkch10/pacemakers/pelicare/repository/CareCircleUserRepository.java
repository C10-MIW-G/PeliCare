package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareCircleUserRepository extends JpaRepository<CareCircleUser, Long> {
    Optional<CareCircleUser> findByUser(User user);

}
