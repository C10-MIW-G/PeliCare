package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.UserInformationDTO;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.UserDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.UserInformationDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Ramon de Wilde <r.de.wilde@st.hanze.nl>
 * <p>
 * The service for the user entity
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final UserDTOMapper userDTOMapper;
    private final SafeDeleteService safeDeleteService;
    private final UserInformationDTOMapper userInformationDTOMapper;

    public void updatePassword(String jwt, String newPassword){
        userRepository.updateUserPassword(passwordEncoder.encode(newPassword), jwtService.extractUsername(jwt));
    }

    public boolean compareOldPassword(String jwt, String oldPassword){
        User user = userRepository.findByEmail(jwtService.extractUsername(jwt)).orElseThrow();
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public String geEmailOfUser(Long userId) {
        return userRepository.findById(userId).get().getEmail();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
    public UserInformationDTO getUserInformationByEmail(String email) {
        return userRepository.findByEmail(email).map(userInformationDTOMapper).orElseThrow();
    }

    public void saveUserInformation(String email, UserInformationDTO user){
        User userToEdit = userRepository.findByEmail(email).orElseThrow();
        userToEdit.setName(user.getName());
        userToEdit.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(userToEdit);
    }

    public UserDTO findCurrentUser(String jwt){
        return userRepository.findByEmail(jwtService.extractUsername(jwt)).map(userDTOMapper).orElseThrow();
    }

    public void deleteUser(String jwt){
        User userToBeDeleted = findUserByEmail(jwtService.extractUsername(jwt));

        if(safeDeleteService.userCanBeDeleted(userToBeDeleted)){
            userRepository.delete(userToBeDeleted);
        }
    }
}
