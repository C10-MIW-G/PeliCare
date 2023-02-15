package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.configuration.JwtService;
import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.CreateCareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CreateCareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * Service layer for the CareCircle entity
 */
@Service
@RequiredArgsConstructor
public class CareCircleService {

    private final CareCircleRepository careCircleRepository;
    private final CareCircleDTOMapper careCircleDTOMapper;
    private final CreateCareCircleDTOMapper createCareCircleDTOMapper;
    private final JwtService jwtService;
    private final CareCircleUserRepository careCircleUserRepository;
    private final CareCircleUserService careCircleUserService;

    private final UserRepository userRepository;
    private final TaskService taskService;

    public List<CareCircleDTO> findAllCareCircles() {
        return careCircleRepository.findAll()
                .stream()
                .map(careCircleDTOMapper)
                .collect(Collectors.toList());
    }

    public CareCircleDTO getCareCircle(Long id) throws ResourceNotFoundException {

        return careCircleRepository.findById(id)
                .map(careCircleDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CareCircle not found"
                ));
    }
    public CareCircle createCareCircle(CreateCareCircleDTO careCircle){
        CareCircle newCareCircle = createCareCircleDTOMapper.apply(careCircle);
        return careCircleRepository.save(newCareCircle);
    }

    public List<CareCircleDTO> findCirclesOfAdmin(String jwt) {
        String username = jwtService.extractUsername(jwt);
        Long userId = userRepository.findByEmail(username).orElseThrow().getId();
        Set<Long> careCircleIds = careCircleUserRepository.findCareCirclesOfAdmin(userId);
        List<CareCircleDTO> returnlist = new ArrayList<>();
        for (Long circleId : careCircleIds) {
            returnlist.add(careCircleRepository.findById(circleId).
                    map(careCircleDTOMapper)
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "CareCircle not found")));
        }
        return returnlist;
    }
    public List<CareCircleDTO> findCirclesOfUser(String jwt) {
        String username = jwtService.extractUsername(jwt);
        Long userId = userRepository.findByEmail(username).orElseThrow().getId();
        Set<Long> careCircleIds = careCircleUserRepository.findCareCirclesOfUser(userId);
        List<CareCircleDTO> returnlist = new ArrayList<>();
        for (Long circleId : careCircleIds) {
            returnlist.add(careCircleRepository.findById(circleId).
                    map(careCircleDTOMapper)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "CareCircle not found")));
        }
        return returnlist;
    }

    public void deleteCareCircle(Long circleId) {
        // find the circle
        CareCircle circleToDelete = careCircleRepository.findById(circleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "CareCircle not found"
        ));
        // find the task list of this Care Circle and delete all tasks
        List<Task> taskList = circleToDelete.getTaskList();
        for (Task task : taskList) {
            taskService.deleteTask(task.getId());
        }

        // delete the appropriate entries from care_circle_user table
        careCircleUserService.deleteCareCircleUsers(circleId);

        // finally: delete the Care Circle
        careCircleRepository.deleteById(circleId);

    }
}
