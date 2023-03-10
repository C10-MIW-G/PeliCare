package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.UserInformationDTO;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */

@Service
public class UserInformationDTOMapper implements Function<User, UserInformationDTO> {
    @Override
    public UserInformationDTO apply(User user) {
        return new UserInformationDTO(
                user.getEmail(),
                user.getName(),
                user.getPhoneNumber()
        );
    }
}
