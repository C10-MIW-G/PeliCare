package com.makeitworkch10.pacemakers.pelicare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * A Task objects represents something to be done for a Care Recipient.
 */
@Entity
@Getter
@Setter
@Builder
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
