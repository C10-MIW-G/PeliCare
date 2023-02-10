package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * Endpoints for CareCircle related requests
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carecircle")
public class CareCircleController {

    private final CareCircleService careCircleService;

    @GetMapping("/all")
    public ResponseEntity<List<CareCircleDTO>> getAllCareCircles() {
        List<CareCircleDTO> responseList = careCircleService.findAllCareCircles();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CareCircleDTO> findCareCircle(@PathVariable("id") Long id) {
        CareCircleDTO careCircle = careCircleService.getCareCircle(id);
        return new ResponseEntity<>(careCircle, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CareCircle> createCareCircle(@RequestBody CareCircle careCircle){
        CareCircle newCareCircle = careCircleService.createCareCircle(careCircle);
        return new ResponseEntity<>(newCareCircle, HttpStatus.CREATED);
    }
}
