package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.authentication.JwtSettings;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.DuplicateUserException;
import com.makeitworkch10.pacemakers.pelicare.exception.UserNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.service.SafeDeleteService;
import com.makeitworkch10.pacemakers.pelicare.service.UserService;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleUserDTOMapper;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    @Mock
    private CareCircleUserDTOMapper careCircleUserDTOMapper;
    private JwtService jwtService;
    private CareCircleUserService careCircleUserService;

    @BeforeEach
    public void initServices() {
        jwtService = new JwtService(jwtSettings);

        UserService userService = new UserService(
                jwtService, userRepository, passwordEncoder, userDTOMapper, safeDeleteService, informationDTOMapper);
        careCircleUserService = new CareCircleUserService(
                jwtService, userRepository, careCircleUserRepository, careCircleRepository,
                userService, careCircleUserDTOMapper);
    }

    @Test
    void userIsAdminOfCircle(){
        User user  = new User("joke@pelicare.nl", "jokepw");
        when(userRepository.findByEmail("joke@pelicare.nl")).thenReturn(Optional.of(user));
        String jwt = jwtService.generateToken(user);
        CareCircle careCircle = new CareCircle();
        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, true);
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), user.getId()))
                .thenReturn(Optional.of(careCircleUser.isCircleAdmin()));
        assertThat(careCircleUserService.isUserAdminOfCircle(careCircleUser.getId(), jwt)).isTrue();
    }

    @Test
    void userIsNotAdminOfCircle(){
        User user  = new User("joke@pelicare.nl", "jokepw");
        when(userRepository.findByEmail("joke@pelicare.nl")).thenReturn(Optional.of(user));
        String jwt = jwtService.generateToken(user);
        CareCircle careCircle = new CareCircle();
        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, false);
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), user.getId()))
                .thenReturn(Optional.of(careCircleUser.isCircleAdmin()));
        assertThat(careCircleUserService.isUserAdminOfCircle(careCircleUser.getId(), jwt)).isFalse();
    }

    @Test
    void userIsAdminOfDifferentCircle(){
        User user  = new User("joke@pelicare.nl", "jokepw");
        when(userRepository.findByEmail("joke@pelicare.nl")).thenReturn(Optional.of(user));
        String jwt = jwtService.generateToken(user);
        CareCircle careCircle = new CareCircle();
        CareCircle careCircleOther = new CareCircle();
        CareCircleUser careCircleUser = new CareCircleUser(user, careCircle, false);
        CareCircleUser careCircleUserOther = new CareCircleUser(user, careCircleOther, true);
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), user.getId()))
                .thenReturn(Optional.of(careCircleUser.isCircleAdmin()));
        assertThat(careCircleUserService.isUserAdminOfCircle(careCircleUser.getId(), jwt)).isFalse();
    }

    @Test
    void userIsMemberOfCircle(){
        User kees  = new User("kees@pelicare.nl", "keespw");
        when(userRepository.findByEmail("kees@pelicare.nl")).thenReturn(Optional.of(kees));
        String jwtKees = jwtService.generateToken(kees);
        CareCircle auntEmma = new CareCircle();
        when(careCircleUserRepository.checkCareCircleUser(auntEmma.getId(), kees.getId()))
                .thenReturn(1);
        assertThat(careCircleUserService.isUserOfCircle(auntEmma.getId(), jwtKees)).isTrue();
    }
    
    @Test
    void UserIsNoMemberOfCircle() {
        User piet  = new User("piet@pelicare.nl", "pietpw");
        when(userRepository.findByEmail("piet@pelicare.nl")).thenReturn(Optional.of(piet));
        String jwtPiet = jwtService.generateToken(piet);
        CareCircle auntEmma = new CareCircle();
        when(careCircleUserRepository.checkCareCircleUser(auntEmma.getId(), piet.getId()))
                .thenReturn(0);
        assertThat(careCircleUserService.isUserOfCircle(auntEmma.getId(), jwtPiet)).isFalse();
    }

    @Test
    void AddDuplicateUserToCareCircle() throws DuplicateUserException {
        UserDTO newUser = new UserDTO(2L, "joke@pelicare.nl");
        User joke = new User(2l, "joke@pelicare.nl", "jokepw");
        User kees = new User("kees@pelicare.nl", "keespw");
        String jwtKees = jwtService.generateToken(kees);
        CareCircle careCircle = new CareCircle();
        CareCircleUser careCircleUser = new CareCircleUser(joke, careCircle, false);
        when(userRepository.findByEmail(kees.getEmail())).thenReturn(Optional.of(kees));
        when(careCircleRepository.findById(careCircle.getId())).thenReturn(Optional.of(careCircle));
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(joke));
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), kees.getId()))
                .thenReturn(Optional.of(true));
        when(careCircleUserRepository.findByUserIdAndCareCircle(newUser.getId(), careCircle.getId()))
                .thenReturn(careCircleUser.getUser().getId());
        assertThrows(DuplicateUserException.class,
                ()-> careCircleUserService.addUserToCareCircle(jwtKees, newUser, careCircle.getId()));
    }

    @Test
    void AddNonExistentUserToCareCircle() throws UserNotFoundException {
        UserDTO newUser = new UserDTO(2L, "joke@pelicare.nl");
        User kees = new User("kees@pelicare.nl", "keespw");
        String jwtKees = jwtService.generateToken(kees);
        CareCircle careCircle = new CareCircle();
        when(userRepository.findByEmail(kees.getEmail())).thenReturn(Optional.of(kees));
        when(careCircleRepository.findById(careCircle.getId())).thenReturn(Optional.of(careCircle));
        when(careCircleUserRepository.isUserAdminOfCircle(careCircle.getId(), kees.getId()))
                .thenReturn(Optional.of(true));
        assertThrows(UserNotFoundException.class,
                ()-> careCircleUserService.addUserToCareCircle(jwtKees, newUser, careCircle.getId()));
    }
}