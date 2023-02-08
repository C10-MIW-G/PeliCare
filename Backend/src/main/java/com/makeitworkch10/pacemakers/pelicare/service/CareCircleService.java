package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskCompleteDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.TaskCompleteDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final TaskCompleteDTOMapper taskCompleteDTOMapper;

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
    public CareCircle createCareCircle(CareCircle careCircle){
        CareCircle newCareCircle = careCircle.builder()
                .name(careCircle.getName())
                .build();
        return careCircleRepository.save(newCareCircle);
    }
}
