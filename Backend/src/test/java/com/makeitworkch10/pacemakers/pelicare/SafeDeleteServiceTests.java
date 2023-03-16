package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.exception.DeleteUserException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.SafeDeleteService;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Ruben de Vries
 * Testing the SafeDeleteService methods.
 */

@ExtendWith(MockitoExtension.class)
public class SafeDeleteServiceTests {

    @Mock
    private CareCircleUserRepository careCircleUserRepository;

    @Mock
    private CareCircleRepository careCircleRepository;

    @InjectMocks
    private SafeDeleteService safeDeleteService;

    private final User userToBeDeleted = new User();
    private final Set<Long> careCirclesWhereUserIsAdmin = new HashSet<>();
    private final Long careCircleUserId = 1L;

    @Test
    void userCanBeDeletedWhenNoAdminTest(){
        when(careCircleUserRepository.findCareCirclesOfAdmin(userToBeDeleted.getId()))
                .thenReturn(careCirclesWhereUserIsAdmin);

        assertTrue(safeDeleteService.userCanBeDeleted(userToBeDeleted));
    }

    @Test
    void userCanBeDeletedWhenMultipleAdminsTest(){
        int multipleAdminsCount = 3;

        careCirclesWhereUserIsAdmin.add(careCircleUserId);

        when(careCircleUserRepository.findCareCirclesOfAdmin(userToBeDeleted.getId()))
                .thenReturn(careCirclesWhereUserIsAdmin);

        when(careCircleUserRepository.countAdmins(careCircleUserId)).thenReturn(multipleAdminsCount);

        assertTrue(safeDeleteService.userCanBeDeleted(userToBeDeleted));
    }

    @Test
    void userCannotBeDeletedWhenOnlyAdminTest(){
        int singleAdminCount = 1;

        careCirclesWhereUserIsAdmin.add(careCircleUserId);

        when(careCircleUserRepository.findCareCirclesOfAdmin(userToBeDeleted.getId()))
                .thenReturn(careCirclesWhereUserIsAdmin);

        when(careCircleUserRepository.countAdmins(careCircleUserId)).thenReturn(singleAdminCount);

        CareCircle careCircle = new CareCircle();
        when(careCircleRepository.findById(careCircleUserId)).thenReturn(Optional.of(careCircle));

        assertThatExceptionOfType(DeleteUserException.class)
                .isThrownBy(() -> safeDeleteService.userCanBeDeleted(userToBeDeleted))
                .withMessage("User cannot be deleted");
    }
}