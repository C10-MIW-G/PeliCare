package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleUserDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike de Jong
 * Mapper for sending Care Circle Users to and fro.
 */

@Service
@RequiredArgsConstructor
public class CareCircleUserDTOMapper implements Function<CareCircleUser, CareCircleUserDTO> {

    @Override
    public CareCircleUserDTO apply(CareCircleUser careCircleUser) {
        CareCircleUserDTO careCircleUserDTO = new CareCircleUserDTO();
        careCircleUserDTO.setCircleId(careCircleUser.getCareCircle().getId());
        careCircleUserDTO.setEmail(careCircleUser.getUser().getEmail());
        careCircleUserDTO.setIsAdmin(careCircleUser.isCircleAdmin());
        careCircleUserDTO.setName(careCircleUser.getUser().getName());
        careCircleUserDTO.setPhoneNumber(careCircleUser.getUser().getPhoneNumber());

        return careCircleUserDTO;
    }
}
