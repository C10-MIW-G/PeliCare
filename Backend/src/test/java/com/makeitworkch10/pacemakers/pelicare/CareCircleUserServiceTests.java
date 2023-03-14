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
import com.makeitworkch10.pacemakers.pelicare.service.mappers.UserInformationDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private final JwtSettings jwtSettings = new JwtSettings(
            "0AJ7Wdyt5x0rQpgQaRibL5Z5DS3A48Gwv3jLM9iCIWxSSd87eJHaB1kGsopXx0FLhCMfZkeOur7LyZ26eZ4RVw");

    @Mock
    private UserInformationDTOMapper informationDTOMapper;
    private JwtService jwtService;
    private CareCircleUserService careCircleUserService;

    @BeforeEach
    public void initServices() {
        jwtService = new JwtService(jwtSettings);

        UserService userService = new UserService(
                jwtService, userRepository, passwordEncoder, userDTOMapper, safeDeleteService, informationDTOMapper);
        careCircleUserService = new CareCircleUserService(
                jwtService, userRepository, careCircleUserRepository, careCircleRepository, userService);
    }

    @Test
    void userIsAdminOfCircle(){
        User user  = new User("admintest", "admintestpw");
        when(userRepository.findByEmail("admintest")).thenReturn(Optional.of(user));
        String jwt = jwtService.generateToken(user);
        CareCircle careCircle = new CareCircle();


        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, true);
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), user.getId()))
                .thenReturn(Optional.of(careCircleUser.isCircleAdmin()));
        assertThat(careCircleUserService.isUserAdminOfCircle(careCircleUser.getId(), jwt)).isTrue();
    }
}