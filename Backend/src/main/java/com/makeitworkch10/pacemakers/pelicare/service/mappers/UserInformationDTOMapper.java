package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.UserInformationDTO;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike de Jong
 * DTO to send user information back and forth
 */

@Service
@RequiredArgsConstructor
public class UserInformationDTOMapper implements Function<User, UserInformationDTO> {
    private final UserRepository userRepository;

    @Override
    public UserInformationDTO apply(User user) {
        return new UserInformationDTO(
                user.getEmail(),
                user.getName(),
                user.getPhoneNumber()
        );
    }

    public User mapFromDTO(String email, UserInformationDTO userInformationDTO){
        User userToEdit = userRepository.findByEmail(email).orElseThrow();
        userToEdit.setName(userInformationDTO.getName());
        userToEdit.setPhoneNumber(userInformationDTO.getPhoneNumber());
        return userToEdit;
    }
}
