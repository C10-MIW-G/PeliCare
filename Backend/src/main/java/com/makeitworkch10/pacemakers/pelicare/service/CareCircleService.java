package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.CareCircleDTOMapper;
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
                        "CareCircle with id [%s] not found".formatted(id)
                ));

    }

    public CareCircle findCareCircle( Long id ) {
        return careCircleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carecircle not found"));
    }
}
