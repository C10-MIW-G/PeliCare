package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.dto.CareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.CreateCareCircleDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.user.User;
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
    private final CareCircleUserService careCircleUserService;

    @GetMapping("/admin")
    public ResponseEntity<List<CareCircleDTO>> getAdminCircles(@RequestHeader (name="Authorization") String jwt) {
        List<CareCircleDTO> responseList = careCircleService.findCirclesOfAdmin(jwt);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<CareCircleDTO>> getUserCircles(@RequestHeader (name="Authorization") String jwt) {
        List<CareCircleDTO> responseList = careCircleService.findCirclesOfUser(jwt);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CareCircleDTO> findCareCircle(@PathVariable("id") Long id ) {
        CareCircleDTO careCircle = careCircleService.getCareCircle(id);
        return new ResponseEntity<>(careCircle, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCareCircle(@RequestBody CreateCareCircleDTO newCareCircle,
                                                   @RequestHeader (name="Authorization") String jwt){
        CareCircle savedCareCircle = careCircleService.createCareCircle(newCareCircle);
        careCircleUserService.addCircleAdminToCareCircle(jwt, savedCareCircle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}/members")
    public ResponseEntity<List<UserDTO>> getUsersOfCareCircle(@PathVariable("id") Long id){
        List<UserDTO> responseList = careCircleService.findUsersOfCareCircle(id);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    @PostMapping("/get/{id}/members/add")
    public ResponseEntity<String> addUserToCareCircle(@PathVariable("id") Long id,
                                                      @RequestBody UserDTO user,
                                                      @RequestHeader (name="Authorization") String jwt){
        careCircleUserService.addUserToCareCircle(jwt, user, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
