package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircleUser;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleUserRepository;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
import com.makeitworkch10.pacemakers.pelicare.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Ruben de Vries
 * Methods for simplifying the entry of Seed data
 */

@Service
@RequiredArgsConstructor
public class SeedService {
    private final TaskRepository taskRepository;
    private final CareCircleUserRepository careCircleUserRepository;

    public void addUserToCircle(User user, CareCircle careCircle, boolean isCircleAdmin){
        CareCircleUser userToAdd = CareCircleUser.builder()
                .user(user)
                .careCircle(careCircle)
                .isCircleAdmin(isCircleAdmin)
                .build();
        careCircleUserRepository.save(userToAdd);
    }

    public void addTasksToCircle(String fileName, CareCircle careCircle){
        File taskListFile = new File(fileName);
        try (Scanner input = new Scanner(taskListFile)){
            while (input.hasNextLine()) {
                String[] taskArray = input.nextLine().split(";");
                Task taskToAdd = new Task();
                taskToAdd.setCareCircle(careCircle);
                buildTaskFromInput(taskArray, taskToAdd);
                taskRepository.save(taskToAdd);
           }
        }  catch (FileNotFoundException notFoundException) {
            System.out.println("The file could not be found.");
        }
    }

    private static void buildTaskFromInput(String[] taskArray, Task taskToAdd) {
        taskToAdd.setTitle(taskArray[0]);
        taskToAdd.setDescription(taskArray[1]);
        if(taskArray[2].equals("")){
            taskToAdd.setDate(null);
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            taskToAdd.setDate(LocalDateTime.parse(taskArray[2], formatter));
        }
        taskToAdd.setCompletedTask(Boolean.parseBoolean(taskArray[3]));
    }

}
