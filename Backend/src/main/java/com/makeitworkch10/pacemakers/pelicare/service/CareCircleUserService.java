package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleUserDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.ToggleAdminStatusDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.CareCircleMustHaveAdminException;
import com.makeitworkch10.pacemakers.pelicare.exception.DuplicateUserException;
import com.makeitworkch10.pacemakers.pelicare.exception.UserNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.*;


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
    private final UserService userService;

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
        User user = userRepository.findByEmail(username).orElseThrow();
        if(user != null) {
            return careCircleUserRepository.isUserAdminOfCircle(circleId, user.getId()).orElseThrow();
        } else {
            return false;
        }
    }

    public boolean isUserOfCircle(Long circleId, String jwt) {
        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(username).orElseThrow();
        Long userId = user.getId();
        return (careCircleUserRepository.checkCareCircleUser(circleId, userId) == 1);
    }

    public void deleteCareCircleUsers(Long circleId) {
        careCircleUserRepository.deleteCareCircleUsersByCircleId(circleId);
    }

    public void addUserToCareCircle(String jwt, UserDTO user, Long careCircleId) {
        if (isUserAdminOfCircle(careCircleId, jwt)) {
            CareCircle careCircle = careCircleRepository.findById(careCircleId).orElseThrow();
            User newUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                    () -> new UserNotFoundException("User does not exist")
            );
            if (!Objects.equals(newUser.getId(),
                    careCircleUserRepository.findByUserIdAndCareCircle(newUser.getId(), careCircleId))) {
                CareCircleUser careCircleUser = new CareCircleUser(null, newUser, careCircle, false);
                careCircleUserRepository.save(careCircleUser);
            } else {
                throw new DuplicateUserException("User already in the Care Circle");
            }
        }
    }
    public void removeUserFromCareCircle(String jwt, String email, Long careCircleId) {
        if (isUserAdminOfCircle(careCircleId, jwt)) {
            CareCircle careCircle = careCircleRepository.findById(careCircleId).orElseThrow(
                    () -> new UserNotFoundException("CareCircle does not exist")
            );
            User removeUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new UserNotFoundException("User does not exist")
            );
                careCircleUserRepository.deleteCareCircleUserByCircleIdAndUserId
                        (removeUser.getId(), careCircle.getId());
            } else {
                throw new ErrorResponseException(HttpStatus.FORBIDDEN);
        }
    }

    public void removeYourselfFromCareCircle(String jwt, String email, Long careCircleId) {
        if (isUserOfCircle(careCircleId, jwt))  {
            CareCircle careCircle = careCircleRepository.findById(careCircleId).orElseThrow(
                    () -> new UserNotFoundException("CareCircle does not exist")
            );
            User currentUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new UserNotFoundException("User does not exist")
            );
            careCircleUserRepository.deleteCareCircleUserByCircleIdAndUserId
                    (currentUser.getId(), careCircle.getId());
        } else {
            throw new ErrorResponseException(HttpStatus.FORBIDDEN);
        }
    }

    public void toggleUserAdmin(String jwt, ToggleAdminStatusDTO toggleAdminStatusDTO) {

        Long circleId = toggleAdminStatusDTO.getCircleId();
        if(isUserAdminOfCircle(circleId, jwt)) {
            Long userId = userService.findUserByEmail(toggleAdminStatusDTO.getEmail()).getId();
            Boolean isAdmin = careCircleUserRepository.isUserAdminOfCircle(circleId, userId).get();
            if(!isAdmin){
                careCircleUserRepository.promoteUserToAdmin(userId,circleId);
            }
            else {
                if (careCircleUserRepository.countAdmins(circleId) >= 2) {
                    careCircleUserRepository.revokeUserAdmin(userId,circleId);
                } else {
                    throw new CareCircleMustHaveAdminException("A Care Circle should have at least one Admin.");
                }
            }
        }
    }

    public List<CareCircleUserDTO> usersOfCareCircle(Long circleId) {
        // list to return
        List<CareCircleUserDTO> responseList = new ArrayList<>();

        List<Long> userIdList = careCircleUserRepository.findUsersOfCareCircle(circleId);
        for (Long userId : userIdList) {
            Boolean isAdmin = careCircleUserRepository.isUserAdminOfCircle(circleId, userId).get();
            String email = userService.geEmailOfUser(userId);
            CareCircleUserDTO careCircleUserDTO = new CareCircleUserDTO();
            careCircleUserDTO.setCircleId(circleId);
            careCircleUserDTO.setEmail(email);
            careCircleUserDTO.setIsAdmin(isAdmin);
            responseList.add(careCircleUserDTO);
        }
        return responseList;
    }
}
