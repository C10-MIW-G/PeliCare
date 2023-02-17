package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.authentication.JwtService;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: Ramon de Wilde <r.de.wilde@st.hanze.nl>
 * <p>
 * The service for the user entity
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void updatePassword(String jwt, String newPassword){
        String username = jwtService.extractUsername(jwt);
        userRepository.updateUserPassword(passwordEncoder.encode(newPassword), username);
    }

    public boolean compareOldPassword(String jwt, String oldPassword){
        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(username).orElseThrow();
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public String geEmailOfUser(Long userId) {
        return userRepository.findById(userId).get().getEmail();
    }

}
