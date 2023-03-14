package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.authentication.JwtSettings;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.service.TaskService;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
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
    private JwtSettings jwtSettings = new JwtSettings(
            "0AJ7Wdyt5x0rQpgQaRibL5Z5DS3A48Gwv3jLM9iCIWxSSd87eJHaB1kGsopXx0FLhCMfZkeOur7LyZ26eZ4RVw");


    private JwtService jwtService = new JwtService(jwtSettings);
    @Mock
    private CareCircleUserRepository careCircleUserRepository;
    @Mock
    private CareCircleUserService careCircleUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskService taskService;
    private CareCircleService careCircleService;

    @BeforeEach
    public void careCircleServiceSettings(){
        careCircleService = new CareCircleService(
                careCircleRepository, careCircleDTOMapper,
                jwtService, careCircleUserRepository,
                careCircleUserService, userRepository, taskService
        );
    }

    @Test
    public void createCareCircleTest(){
        List<Task> taskList = new ArrayList<>();
        CareCircle careCircle = new CareCircle (null, "Grandma's Circle", taskList);
        when(careCircleRepository.save(careCircle)).thenReturn(careCircle);
        CareCircle savedCareCircle = careCircleService.createCareCircle(careCircle);
        assertThat(savedCareCircle.getName()).isNotEmpty();
    }
}
