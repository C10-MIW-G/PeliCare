package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * beschrijving van het programma
 */
//@RequiredArgsConstructor
public class DatabaseSeeder {
    private final TaskRepository taskRepository;
    private final CareCircleRepository careCircleRepository;
    public DatabaseSeeder(TaskRepository taskRepository,
                          CareCircleRepository careCircleRepository) {
        this.taskRepository = taskRepository;
        this.careCircleRepository = careCircleRepository;
    }

    // first CareCircles
    CareCircle circle1 = new CareCircle("Hyacinth Bouquet Society");

    CareCircle circle2 = new CareCircle("Richard relief foundation");

    // first tasks
    Task task1 = new Task("hond uitlaten", "Wodan...");


    Task task2 = new Task("kat voeren", "Poekie....");



}
