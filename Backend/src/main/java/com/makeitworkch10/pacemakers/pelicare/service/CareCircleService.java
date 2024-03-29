package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private final JwtService jwtService;
    private final CareCircleUserRepository careCircleUserRepository;
    private final CareCircleUserService careCircleUserService;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final CreateCareCircleDTOMapper createCareCircleDTOMapper;
    private final FileStorageService fileStorageService;

    public CareCircleDTO getCareCircle(Long id) throws ResourceNotFoundException {

        return careCircleRepository.findById(id)
                .map(careCircleDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CareCircle with id: " + id + " not found"
                ));
    }

    public void saveNewCareCircleWithImage(List<MultipartFile> multipartFiles, String carecirclename, String jwt) {
        CareCircle savedCareCircle = createNewCareCircle(carecirclename);
        careCircleUserService.addCircleAdminToCareCircle(jwt, savedCareCircle);

        // handle image upload
        String newFileNameOfImage = fileStorageService.saveImage(multipartFiles, savedCareCircle.getId());
        addImageToCareCircle(savedCareCircle.getId(), newFileNameOfImage);
    }
    public CareCircle createCareCircle(CreateCareCircleDTO createCareCircleDTO){
        CareCircle careCircle = createCareCircleDTOMapper.apply(createCareCircleDTO);
        return careCircleRepository.save(careCircle);
    }

    public List<CareCircleDTO> findAllCareCirclesOfUser(String jwt) {
        String username = jwtService.extractUsername(jwt);
        Long userId = userRepository.findByEmail(username).orElseThrow().getId();
        Set<Long> careCircleIds = careCircleUserRepository.findAllCareCirclesOfUser(userId);
        List<CareCircleDTO> returnList = new ArrayList<>();
        for (Long circleId : careCircleIds) {
            returnList.add(careCircleRepository.findById(circleId).
                    map(careCircleDTOMapper)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "CareCircle not found")));
        }
        return returnList;
    }

    public void deleteCareCircle(Long circleId) {
        // find the Care Circle
        CareCircle circleToDelete = findCircleToDelete(circleId);

        // delete all its Tasks
        deleteTasksFromCircle(circleToDelete);

        // delete the appropriate entries from care_circle_user table
        careCircleUserService.deleteCareCircleUsers(circleId);

        // delete the picture
        fileStorageService.deleteImage(circleToDelete.getImagefilename());

        // finally: delete the Care Circle
        careCircleRepository.deleteById(circleId);
    }

    private void deleteTasksFromCircle(CareCircle circleToDelete) {
        List<Task> taskList = circleToDelete.getTaskList();
        for (Task task : taskList) {
            taskService.deleteTask(task.getId());
        }
    }

    private CareCircle findCircleToDelete(Long circleId) {
        return careCircleRepository.findById(circleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "CareCircle not found"
        ));
    }

    public CareCircle createNewCareCircle(String carecirclename) {
        CareCircle newCareCircle = new CareCircle();
        newCareCircle.setName(carecirclename);
        return careCircleRepository.save(newCareCircle);
    }

    public void addImageToCareCircle(Long id, String filaname) {
        CareCircle circleReceivingTheImage = careCircleRepository.findById(id).orElseThrow();
        circleReceivingTheImage.setImagefilename(filaname);
        careCircleRepository.save(circleReceivingTheImage);
    }

    public void editCircle(List<MultipartFile> multipartFiles,
                           String newCarecircleName,
                           String carecircleId,
                           String jwt,
                           String oldImageFilename,
                           String noImage) {
        Long circleId = Long.parseLong(carecircleId);
        if(careCircleUserService.isUserAdminOfCircle(circleId,jwt)){

            CareCircle careCircle = careCircleRepository.findById(circleId)
                    .orElseThrow(()-> new ResourceNotFoundException("Care Circle could not be found"));
            careCircle.setName(newCarecircleName);

            String newImageFileName = fileStorageService.updateImage(   circleId,
                                                                        oldImageFilename,
                                                                        noImage,
                                                                        multipartFiles);

            careCircle.setImagefilename(newImageFileName);
            careCircleRepository.save(careCircle);
        }
    }
}
