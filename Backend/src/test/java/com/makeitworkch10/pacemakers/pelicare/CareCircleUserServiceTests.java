package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.authentication.JwtSettings;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.service.SafeDeleteService;
import com.makeitworkch10.pacemakers.pelicare.service.UserService;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.UserDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

/**
 * @author Maaike de Jong
 * Tests for the Care Circle User Service
 */

@ExtendWith(MockitoExtension.class)
public class CareCircleUserServiceTests {

    @Mock
    private CareCircleUserRepository careCircleUserRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CareCircleRepository careCircleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDTOMapper userDTOMapper;
    @Mock
    private SafeDeleteService safeDeleteService;

    private JwtSettings jwtSettings;
    private JwtService jwtService;
    private UserService userService;
    private CareCircleUserService careCircleUserService;

    @BeforeEach
    public void CareCircleUserServiceTests() {
        jwtSettings = new JwtSettings();
        jwtService = new JwtService(jwtSettings);

        userService = new UserService(
                jwtService, userRepository, passwordEncoder, userDTOMapper, safeDeleteService);
        careCircleUserService = new CareCircleUserService(
                jwtService, userRepository, careCircleUserRepository, careCircleRepository, userService);
    }

    @Test
    void userIsAdminOfCircle(){
        User user  = new User("admintest", "admintestpw");
        when(userRepository.save(user)).thenReturn(user);
        String jwt = jwtService.generateToken(user);
        CareCircle careCircle = new CareCircle();
        when(careCircleRepository.save(careCircle)).thenReturn(careCircle);


        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, true);
        when(careCircleUserRepository.save(careCircleUser)).thenReturn(careCircleUser);

        Assertions.assertTrue(careCircleUserService.isUserAdminOfCircle(careCircle.getId(), jwt));
    }

    @Test
    void userIsNotAdminOfCircle(){
        User user  = new User();
        CareCircle careCircle = new CareCircle();

        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, false);

        Assertions.assertFalse(careCircleUser.isCircleAdmin());
    }

    @Test
    void userIsAdminOfDifferentCircle(){
        User user  = new User();
        CareCircle careCircle = new CareCircle();
        CareCircle careCircleNotAdmin = new CareCircle();
        String jwt = jwtService.generateToken(user);

        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, true);
        careCircleUserRepository.save(careCircleUser);
        CareCircleUser careCircleUserNoAdmin = new CareCircleUser(user, careCircleNotAdmin, false);
        careCircleUserRepository.save(careCircleUserNoAdmin);

        Assertions.assertTrue(careCircleUserService.isUserAdminOfCircle(careCircle.getId(), jwt));
    }


}
