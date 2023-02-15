package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.configuration.JwtService;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
    private final CareCircleRepository careCircleRepository;

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

    public void addUserToCareCircle(String jwt, UserDTO user, Long careCircleId) {
        if (isUserAdmin(jwt, careCircleId)) {
            CareCircle careCircle = careCircleRepository.findById(careCircleId).orElseThrow();
            User newUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
            CareCircleUser careCircleUser = new CareCircleUser(null, newUser, careCircle, false);
            careCircleUserRepository.save(careCircleUser);
        }
    }

    private boolean isUserAdmin(String jwt, Long careCircleId){
        String userName = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(userName).orElseThrow();
        return careCircleUserRepository.findIfAdmin(user.getId(), careCircleId);
    }
}
