package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CaptchaService captchaService;

    @PostMapping("/validate/email")
    public ResponseEntity<isEmailAvailableResponse> isEmailAvailable(
            @RequestBody isEmailAvailableRequest emailRequest){
        return ResponseEntity.ok(authenticationService.isEmailAvailable(emailRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        //Validate captcha response with the Google server
        if(captchaService.processResponse(registerRequest.getCaptchaResponse())){
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        }
        else {
            throw new RuntimeException("Invalid ReCaptcha.");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
