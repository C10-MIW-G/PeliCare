package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CareCircleUserRepository extends JpaRepository<CareCircleUser, Long> {

        @Query(
                value = "SELECT care_circle_id FROM care_circle_user WHERE user_id = ?1 AND is_circle_admin = true",
                nativeQuery = true
        )
        Set<Long> findCareCirclesOfAdmin(Long userId);

        @Query(
                value = "SELECT care_circle_id FROM care_circle_user WHERE user_id = ?1 AND is_circle_admin = false",
                nativeQuery = true
        )
        Set<Long> findCareCirclesOfUser(Long userId);
    }
