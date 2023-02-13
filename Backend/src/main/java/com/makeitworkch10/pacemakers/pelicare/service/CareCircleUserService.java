package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.configuration.JwtService;
import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

/**
 * @author Ruben de Vries
 * Service for adding Users to a Care Circle and managing admin rights.
 */

@Service
@RequiredArgsConstructor
public class CareCircleUserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CareCircleUserRepository careCircleUserRepository;

    public void addCircleAdminToCareCircle(String jwt, CareCircle careCircle) {
        String userName = jwtService.extractUsername(jwt);
        User newCircleAdmin = userRepository.findByEmail(userName).orElseThrow();

        CareCircleUser newCareCircleUser = new CareCircleUser(
                null,
                newCircleAdmin,
                careCircle,
                true
        );

        careCircleUserRepository.save(newCareCircleUser);
    }

    public void addUserToCareCircle(String jwt, CareCircleUser careCircleUser){
        if (isUserAdmin(jwt)){
        User newCircleUser = userRepository.findByEmail(careCircleUser.getUser().getEmail()).orElseThrow();
        CareCircleUser newCareCircleUser = new CareCircleUser(
                null,
                newCircleUser,
                careCircleUser.getCareCircle(),
                false
        );
        careCircleUserRepository.save(newCareCircleUser);
        } else {
            throw new ErrorResponseException(HttpStatus.FORBIDDEN);
        }
    }

    public boolean isUserAdmin (String jwt){
        String userName = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(userName).orElseThrow();
        CareCircleUser careCircleUser = careCircleUserRepository.findByUser(user).orElseThrow();
        return careCircleUser.isCircleAdmin();
    }
}
