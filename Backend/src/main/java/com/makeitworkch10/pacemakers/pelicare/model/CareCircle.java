package com.makeitworkch10.pacemakers.pelicare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * A group of people dedicated to caring for one Care Recipient
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareCircle {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Task> taskList;

    public CareCircle(String name) {
        this.name = name;
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }


}
