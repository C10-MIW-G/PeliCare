package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */
@Service
@RequiredArgsConstructor
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply (User user){
        return new UserDTO(
                user.getId(),
                user.getEmail()
        );
    }
}
