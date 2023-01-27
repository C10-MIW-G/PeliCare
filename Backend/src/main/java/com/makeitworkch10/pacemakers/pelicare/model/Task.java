package com.makeitworkch10.pacemakers.pelicare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    public Task(String description, String title) {
        this.description = description;
        this.title = title;
    }

    @ManyToOne
    private CareCircle careCircle;

    @Override
    public String toString() {
        return String.format("%s, %s", title, description); }
}
