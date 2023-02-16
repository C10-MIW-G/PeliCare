package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
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

    public boolean isUserAdminOfCircle(Long circleId, String jwt ) {
        String username = jwtService.extractUsername(jwt);
        Long userId = userRepository.findByEmail(username).orElseThrow().getId();
        return careCircleUserRepository.isUserAdminOfCircle(circleId, userId);
    }

    public void deleteCareCircleUsers(Long circleId) {
        careCircleUserRepository.deleteCareCircleUsersByCircleId(circleId);
    }

    public void addUserToCareCircle(String jwt, UserDTO user, Long careCircleId) {
        if (isUserAdminOfCircle(careCircleId, jwt)) {
            CareCircle careCircle = careCircleRepository.findById(careCircleId).orElseThrow();
            User newUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
            if (newUser.getId() != careCircleUserRepository.findByUserIdAndCareCircle(newUser.getId(), careCircleId)) {
                CareCircleUser careCircleUser = new CareCircleUser(null, newUser, careCircle, false);
                careCircleUserRepository.save(careCircleUser);
            }
        }
    }
}
