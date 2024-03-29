package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.authentication.JwtSettings;
import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.CreateCareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.service.FileStorageService;
import com.makeitworkch10.pacemakers.pelicare.service.TaskService;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CreateCareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Maaike de Jong
 * Tests for the Care Circle Service
 */

@ExtendWith(MockitoExtension.class)
public class CareCircleServiceTests {

    @Mock
    private CareCircleRepository careCircleRepository;
    @Mock
    private CareCircleDTOMapper careCircleDTOMapper;
    private final JwtSettings jwtSettings = new JwtSettings(
            "0AJ7Wdyt5x0rQpgQaRibL5Z5DS3A48Gwv3jLM9iCIWxSSd87eJHaB1kGsopXx0FLhCMfZkeOur7LyZ26eZ4RVw");

    @Mock
    private CreateCareCircleDTOMapper createCareCircleDTOMapper;
    private final JwtService jwtService = new JwtService(jwtSettings);
    @Mock
    private CareCircleUserRepository careCircleUserRepository;
    @Mock
    private CareCircleUserService careCircleUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskService taskService;
    @Mock
    private FileStorageService fileStorageService;
    private CareCircleService careCircleService;

    @BeforeEach
    public void careCircleServiceSettings(){
        careCircleService = new CareCircleService(
                careCircleRepository, careCircleDTOMapper,
                jwtService, careCircleUserRepository,
                careCircleUserService, userRepository,
                taskService, createCareCircleDTOMapper,
                fileStorageService
        );
    }

    @Test
    public void createCareCircleTest(){
        List<Task> taskList = new ArrayList<>();
        CreateCareCircleDTO createCareCircleDTO = new CreateCareCircleDTO ("Grandma's Circle");
        CareCircle careCircle = new CareCircle(null, "Grandma's Circle", "no file selected", taskList);
        when(careCircleRepository.save(careCircle)).thenReturn(careCircle);
        when(createCareCircleDTOMapper.apply(createCareCircleDTO)).thenReturn(careCircle);
        CareCircle savedCareCircle = careCircleService.createCareCircle(createCareCircleDTO);
        assertThat(savedCareCircle.getName()).isNotEmpty();
    }

    @Test
    public void getCareCircleTest(){
        List<Task> taskList = new ArrayList<>();
        CareCircle careCircle = new CareCircle(1L, "Grandma's Circle", "no file selected", taskList);
        CareCircleDTO careCircleDTO = new CareCircleDTO(1L, "Grandma's Circle", "no file selected", taskList);
        when(careCircleRepository.findById(careCircle.getId())).thenReturn(Optional.of(careCircle));
        when((careCircleDTOMapper.apply(careCircle))).thenReturn(careCircleDTO);
        assertThat(careCircle.getName()).isEqualTo(careCircleService.getCareCircle(careCircleDTO.getId()).getName());
    }

    @Test
    public void getCareCircleThrowException() throws ResourceNotFoundException {
        long id = 2L;
        when(careCircleRepository.findById(id)).thenThrow(new ResourceNotFoundException("CareCircle with id: " + id + " not found"));
        assertThrows(ResourceNotFoundException.class, ()-> this.careCircleService.getCareCircle(id));
    }
}
