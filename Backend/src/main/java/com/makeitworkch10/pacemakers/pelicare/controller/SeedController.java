package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.service.SeedService;
import com.makeitworkch10.pacemakers.pelicare.user.Role;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import com.makeitworkch10.pacemakers.pelicare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * A quick way to add data to the database
 */

@RestController
@RequiredArgsConstructor
public class SeedController {

    private final CareCircleRepository careCircleRepository;
    private final UserRepository userRepository;
    private final SeedService seedService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/seed")
    public void seedDatabase(){

        //Create Users
        User userBart = User.builder()
                .name("Bart")
                .email("bart@pelicare.nl")
                .phoneNumber("202-555-0176")
                .password(passwordEncoder.encode("Bart123"))
                .role(Role.USER)
                .build();
        userRepository.save(userBart);

        User userEmily = User.builder()
                .name("Emily")
                .email("emily@pelicare.nl")
                .phoneNumber("510-555-0113")
                .password(passwordEncoder.encode("Emily123"))
                .role(Role.USER)
                .build();
        userRepository.save(userEmily);

        User userHannah = User.builder()
                .name("Hannah")
                .email("hannah@pelicare.nl")
                .phoneNumber("864-555-0188")
                .password(passwordEncoder.encode("Hannah123"))
                .role(Role.USER)
                .build();
        userRepository.save(userHannah);

        User userJohn = User.builder()
                .name("John")
                .email("john@pelicare.nl")
                .phoneNumber("714-555-0152")
                .password(passwordEncoder.encode("John123"))
                .role(Role.USER)
                .build();
        userRepository.save(userJohn);

        User userMarc = User.builder()
                .name("Marc")
                .email("marc@pelicare.nl")
                .phoneNumber("562-555-0116")
                .password(passwordEncoder.encode("Marc123"))
                .role(Role.USER)
                .build();
        userRepository.save(userMarc);

        User userMartha = User.builder()
                .name("Martha")
                .email("martha@pelicare.nl")
                .phoneNumber("909-555-0166")
                .password(passwordEncoder.encode("Martha123"))
                .role(Role.USER)
                .build();
        userRepository.save(userMartha);

        /////////////////////////////////////////////////////////////////////////////////////

        //Care Circle Bart
        CareCircle circleBart = CareCircle.builder()
                .name("Bart")
                .imagefilename("no file selected")
                .build();
        careCircleRepository.save(circleBart);

        //Tasks Bart
        seedService.addTasksToCircle("src/main/resources/seed-data/tasks-bart.csv",
                circleBart);

        //Members Bart
        seedService.addUserToCircle(userBart, circleBart, true);
        seedService.addUserToCircle(userEmily, circleBart, true);
        seedService.addUserToCircle(userMarc, circleBart, false);

        /////////////////////////////////////////////////////////////////////////////////////

        //Care Circle Eugene
        CareCircle circleEugene = CareCircle.builder()
                .name("Eugene")
                .imagefilename("no file selected")
                .build();
        careCircleRepository.save(circleEugene);

        //Tasks Eugene
        seedService.addTasksToCircle("src/main/resources/seed-data/tasks-eugene.csv",
                circleEugene);

        //Members Eugene
        seedService.addUserToCircle(userHannah, circleEugene, true);
        seedService.addUserToCircle(userEmily, circleEugene, false);

        /////////////////////////////////////////////////////////////////////////////////////

        //Care Circle Ellis
        CareCircle circleEllis = CareCircle.builder()
                .name("Ellis")
                .imagefilename("no file selected")
                .build();
        careCircleRepository.save(circleEllis);

        //Tasks Ellis
        seedService.addTasksToCircle("src/main/resources/seed-data/tasks-ellis.csv",
                circleEllis);

        //Members Ellis
        seedService.addUserToCircle(userMartha, circleEllis, true);

        /////////////////////////////////////////////////////////////////////////////////////

        //Care Circle Mom and Dad
        CareCircle circleMomDad = CareCircle.builder()
                .name("Mom and Dad")
                .imagefilename("no file selected")
                .build();
        careCircleRepository.save(circleMomDad);

        //Tasks Mom and Dad
        seedService.addTasksToCircle("src/main/resources/seed-data/tasks-mom-dad.csv",
                circleMomDad);

        //Members Mom and Dad
        seedService.addUserToCircle(userJohn, circleMomDad, true);
        seedService.addUserToCircle(userMartha, circleMomDad, false);
        seedService.addUserToCircle(userHannah, circleMomDad, false);
    }
}

