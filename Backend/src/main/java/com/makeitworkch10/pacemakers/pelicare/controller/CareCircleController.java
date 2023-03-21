package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.dto.*;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleService;
import com.makeitworkch10.pacemakers.pelicare.service.CareCircleUserService;
import com.makeitworkch10.pacemakers.pelicare.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.Paths.get;

import java.nio.file.Path;
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

    private final FileStorageService fileStorageService;
    private final CareCircleService careCircleService;
    private final CareCircleUserService careCircleUserService;

    @PostMapping("/upload")
    public ResponseEntity<String> makeNewCareCircle(@RequestParam("files") List<MultipartFile> multipartFiles,
                                                    @RequestParam("carecirclename")String carecirclename,
                                                    @RequestHeader (name="Authorization") String jwt) {

        careCircleService.saveNewCareCircleWithImage(multipartFiles, carecirclename, jwt);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("filename") String filename)
            throws IOException {
        Path filePath = get("src/main/resources/images").toAbsolutePath().normalize().resolve(filename);
        Resource resource = fileStorageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .body(resource);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CareCircleDTO>> getAllCircles(@RequestHeader (name="Authorization") String jwt){
        List<CareCircleDTO> responseList = careCircleService.findAllCareCirclesOfUser(jwt);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CareCircleDTO> findCareCircle(@PathVariable("id") Long id,
                                                        @RequestHeader (name="Authorization") String jwt) throws IllegalAccessException {
        if (careCircleUserService.isUserOfCircle(id, jwt)) {
        CareCircleDTO careCircle = careCircleService.getCareCircle(id);
        return new ResponseEntity<>(careCircle, HttpStatus.OK);
        } else {
            throw new IllegalAccessException("Page not found");
        }
    }

    @GetMapping("/isadmin/{id}")
    public ResponseEntity<Boolean> isUserAdminOfCareCircle(@PathVariable("id") Long id,
                                                           @RequestHeader (name="Authorization") String jwt) {
            boolean isAdmin = careCircleUserService.isUserAdminOfCircle(id,jwt);
            return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    @PatchMapping("/toggleadminstatus")
    public ResponseEntity<String> toggleAdminStatus(@RequestBody ToggleAdminStatusDTO toggleAdminStatusDTO,
                                                  @RequestHeader (name="Authorization") String jwt) {
        careCircleUserService.toggleUserAdmin(jwt, toggleAdminStatusDTO);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCareCircle(@PathVariable("id") Long circleId) {
        careCircleService.deleteCareCircle(circleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}/members")
    public ResponseEntity<List<CareCircleUserDTO>> getUsersWithStatusOfCareCircle(@PathVariable("id") Long circleId){

        List<CareCircleUserDTO> responseList = careCircleUserService.usersOfCareCircle(circleId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/get/{id}/members/add")
    public ResponseEntity<String> addUserToCareCircle(@PathVariable("id") Long id,
                                                      @RequestBody UserDTO user,
                                                      @RequestHeader (name="Authorization") String jwt){
        careCircleUserService.addUserToCareCircle(jwt, user, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/get/{id}/members/remove/{email}")
    public ResponseEntity<String> removeUserFromCareCircle(@PathVariable("id") Long id,
                                                           @PathVariable("email") String email,
                                                           @RequestHeader (name="Authorization") String jwt){
        careCircleUserService.removeUserFromCareCircle(jwt, email, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
