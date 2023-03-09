package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        @Query(
                value = "SELECT is_circle_admin FROM care_circle_user WHERE care_circle_id = ?1 AND user_id = ?2",
                nativeQuery = true
        )
        Optional<Boolean> isUserAdminOfCircle(Long circleId, Long userId );

        @Modifying
        @Transactional
        @Query(
                value = "DELETE FROM care_circle_user WHERE care_circle_id = ?1",
                nativeQuery = true
        )
        void deleteCareCircleUsersByCircleId(Long careCircleId);

        @Query(
                value = "SELECT user_id FROM care_circle_user WHERE care_circle_id = ?1",
                nativeQuery = true
        )
        List<Long> findUsersOfCareCircle(Long careCircleId);

        @Query(
                value = "SELECT user_id FROM care_circle_user WHERE care_circle_id = ?2 AND user_id = ?1",
                nativeQuery = true
        )
        Long findByUserIdAndCareCircle(Long userId, Long careCircleId);

        @Query(
                value = "SELECT COUNT(*) FROM care_circle_user WHERE care_circle_id = ?1  AND user_id = ?2",
                nativeQuery = true
        )
        int checkCareCircleUser(Long careCircleId, Long userId);

        @Modifying
        @Transactional
        @Query(
                value = "UPDATE care_circle_user ccu SET ccu.is_circle_admin = true WHERE ccu.user_id = ?1 AND ccu.care_circle_id = ?2",
                nativeQuery = true)
        void promoteUserToAdmin(Long userId, Long circleId);

        @Modifying
        @Transactional
        @Query(
                value = "UPDATE care_circle_user ccu SET ccu.is_circle_admin = false WHERE ccu.user_id = ?1 AND ccu.care_circle_id = ?2",
                nativeQuery = true)
        void revokeUserAdmin(Long userId, Long circleId);

        @Query(
                value = "SELECT COUNT(*) FROM care_circle_user WHERE care_circle_id = ?1 AND is_circle_admin = true",
                nativeQuery = true
        )
        int countAdmins(Long careCircleId);

        @Modifying
        @Transactional
        @Query(
                value = "DELETE FROM care_circle_user WHERE user_id = ?1 AND care_circle_id = ?2",
                nativeQuery = true
        )
        void deleteCareCircleUserByCircleIdAndUserId(Long userId, Long careCircleId);
    }
