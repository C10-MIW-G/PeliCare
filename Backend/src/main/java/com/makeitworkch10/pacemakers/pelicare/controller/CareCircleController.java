package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<CareCircle>> getAllCareCircles() {
        List<CareCircle> responseList = careCircleService.findAllCareCircles();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CareCircle> findCareCircle(@PathVariable("id") Long id) {
        CareCircle careCircle = careCircleService.findCareCircle(id);
        return new ResponseEntity<>(careCircle, HttpStatus.OK);
    }

}
