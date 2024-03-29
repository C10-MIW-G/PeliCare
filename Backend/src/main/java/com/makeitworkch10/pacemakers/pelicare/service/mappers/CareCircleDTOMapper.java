package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * create mapping for the CareCircle DTO's
 */
@Service
@RequiredArgsConstructor
public class CareCircleDTOMapper implements Function<CareCircle, CareCircleDTO> {

    @Override
    public CareCircleDTO apply(CareCircle careCircle) {
        return new CareCircleDTO(
                careCircle.getId(),
                careCircle.getName(),
                careCircle.getImagefilename(),
                careCircle.getTaskList()
        );
    }
}
