package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.dto.ChangePasswordDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.UserDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.WrongPasswordException;
import com.makeitworkch10.pacemakers.pelicare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Ramon de Wilde <r.de.wilde@st.hanze.nl>
 * <p>
 * Controls the user related requests
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/updatepassword")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public ResponseEntity<ChangePasswordDTO> changeUserPassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                                @RequestHeader (name="Authorization") String jwt){
            boolean oldPasswordOK = userService.compareOldPassword(jwt, changePasswordDTO.getOldPassword());
            ChangePasswordDTO responseDTO = new ChangePasswordDTO();
            responseDTO.setOldPassword("old");
            responseDTO.setNewPassword("new");
            if (oldPasswordOK){
                userService.updatePassword(jwt, changePasswordDTO.getNewPassword());
                return new ResponseEntity<ChangePasswordDTO>(responseDTO, HttpStatus.OK);
            } throw new WrongPasswordException("Old password is incorrect");
    }

    @GetMapping("/currentuser")
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader (name="Authorization") String jwt){
        UserDTO currentUser = userService.findCurrentUser(jwt);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}