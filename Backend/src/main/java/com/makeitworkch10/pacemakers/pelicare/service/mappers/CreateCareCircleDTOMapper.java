package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.CreateCareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author Ruben de Vries
 * Maps the received CreateCareCircleDTO from the front end to the CareCircle model.
 */

@Service
public class CreateCareCircleDTOMapper implements Function<CreateCareCircleDTO, CareCircle> {

    @Override
    public CareCircle apply(CreateCareCircleDTO createCareCircleDTO) {
        return new CareCircle(
                null,
                createCareCircleDTO.getName(),
                "", // image filename
                new ArrayList<>()
        );
    }
}
