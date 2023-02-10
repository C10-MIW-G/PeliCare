package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
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
    }

}
