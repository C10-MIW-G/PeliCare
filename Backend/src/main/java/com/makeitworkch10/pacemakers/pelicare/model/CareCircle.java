package com.makeitworkch10.pacemakers.pelicare.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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

    @OneToMany(mappedBy = "careCircle")
    @JsonManagedReference
    private List<Task> taskList;

    @Override
    public String toString() {
        return String.format("Care Circle: %s", name);
    }
}
