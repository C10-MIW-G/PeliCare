package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.exception.DeleteUserException;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author Ruben de Vries
 * Service that checks if entities can be safely deleted.
 */

@Service
@RequiredArgsConstructor
public class SafeDeleteService {
    private final CareCircleUserRepository careCircleUserRepository;
    private final CareCircleRepository careCircleRepository;

    public boolean userCanBeDeleted(User userToBeDeleted){
        Set<Long> careCirclesWhereUserIsAdmin = careCircleUserRepository.findCareCirclesOfAdmin(userToBeDeleted.getId());

        if(careCirclesWhereUserIsAdmin.isEmpty()){
            return true;
        }

        return checkIfUserIsTheOnlyAdminLeft(careCirclesWhereUserIsAdmin);
    }

    private boolean checkIfUserIsTheOnlyAdminLeft(Set<Long> careCircleUserList) {
        ArrayList<String> careCircleNames = new ArrayList<>();
        for (Long careCircleId : careCircleUserList) {
            addCircleNamesToException(careCircleNames, careCircleId);
        }

        if(!careCircleNames.isEmpty()){
            throw new DeleteUserException("User cannot be deleted", careCircleNames);
        }

        return true;
    }

    private void addCircleNamesToException(ArrayList<String> careCircleNames, Long careCircleId) {
        if(careCircleUserRepository.countAdmins(careCircleId) == 1){
            careCircleNames.add(careCircleRepository.findById(careCircleId).orElseThrow().getName());
        }
    }
}
