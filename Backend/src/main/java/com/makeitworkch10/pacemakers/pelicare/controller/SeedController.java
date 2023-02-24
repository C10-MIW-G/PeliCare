package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
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

    private final TaskRepository taskRepository;
    private final CareCircleRepository careCircleRepository;
    private final UserRepository userRepository;
    private final CareCircleUserRepository careCircleUserRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/seed")
    public void seedDatabase(){
        // first CareCircles
        CareCircle circle1 = new CareCircle();
        circle1.setName("Care Circle 1");

        CareCircle circle2 = new CareCircle();
        circle2.setName("Care Circle 2");

        CareCircle circle3 = new CareCircle();
        circle3.setName("Care Circle 3");

        // first tasks
        Task task1 = new Task();
        task1.setTitle("Walk the dog");
        task1.setDescription("Wodan...");
        task1.setCareCircle(circle1);
        task1.setCompletedTask(false);

        Task task2 = new Task();
        task2.setTitle("Feed the cat");
        task2.setDescription("Poekie...");
        task2.setCareCircle(circle2);
        task2.setCompletedTask(false);

        Task task3 = new Task();
        task3.setTitle("Grocery shopping");
        task3.setDescription("Milk, eggs, cheese and bread");
        task3.setCareCircle(circle2);
        task3.setCompletedTask(false);

        careCircleRepository.save(circle1);
        careCircleRepository.save(circle2);
        careCircleRepository.save(circle3);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // Users with different privileges
        // Circle admin is not site admin but site user.
        // role of Circle admin is stored in CareCircleUser objects and table
        User circle1Admin = new User();
        circle1Admin.setEmail("admin1");
        circle1Admin.setPassword(passwordEncoder.encode("admin1"));

        circle1Admin.setRole(Role.USER);
        userRepository.save(circle1Admin);
        CareCircleUser adminCircle1 = new CareCircleUser(circle1Admin, circle1, true);
        careCircleUserRepository.save(adminCircle1);

        User circle1User = new User();
        circle1User.setEmail("user1");
        circle1User.setPassword(passwordEncoder.encode("user1"));
        circle1User.setRole(Role.USER);
        userRepository.save(circle1User);
        CareCircleUser userCircle1 = new CareCircleUser(circle1User, circle1, false);
        careCircleUserRepository.save(userCircle1);

        // people in Circle 2

        User circle2Admin = new User();
        circle2Admin.setEmail("admin2");
        circle2Admin.setPassword(passwordEncoder.encode("admin2"));

        circle2Admin.setRole(Role.USER);
        userRepository.save(circle2Admin);
        CareCircleUser adminCircle2 = new CareCircleUser(circle2Admin, circle2, true);
        careCircleUserRepository.save(adminCircle2);

        User circle2User = new User();
        circle2User.setEmail("user2");
        circle2User.setPassword(passwordEncoder.encode("user2"));
        circle2User.setRole(Role.USER);
        userRepository.save(circle2User);
        CareCircleUser userCircle2 = new CareCircleUser(circle2User, circle2, false);
        careCircleUserRepository.save(userCircle2);

        // Admin for Care Circle 3
        User circle3Admin = new User();
        circle3Admin.setEmail("admin3");
        circle3Admin.setPassword(passwordEncoder.encode("admin3"));

        circle3Admin.setRole(Role.USER);
        userRepository.save(circle3Admin);
        CareCircleUser adminCircle3 = new CareCircleUser(circle3Admin, circle3, true);
        careCircleUserRepository.save(adminCircle3);

    }

}

