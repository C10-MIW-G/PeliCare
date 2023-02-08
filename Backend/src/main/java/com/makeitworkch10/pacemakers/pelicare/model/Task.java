package com.makeitworkch10.pacemakers.pelicare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * Something to be done for a Care Recipient
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String description;
    private String title;

    private boolean completedTask;

    @ManyToOne
    @JoinColumn(name = "care_circle_id")
    @JsonBackReference
    private CareCircle careCircle;

    @Override
    public String toString() {
        return String.format("%s, %s", title, description); }
}
