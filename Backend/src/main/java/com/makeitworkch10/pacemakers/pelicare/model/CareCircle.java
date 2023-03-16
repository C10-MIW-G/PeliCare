package com.makeitworkch10.pacemakers.pelicare.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * A group of people dedicated to caring for one Care Recipient
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareCircle {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String imagefilename;

    @OneToMany(mappedBy = "careCircle")
    @JsonManagedReference
    private List<Task> taskList;

    @Override
    public String toString() {
        return String.format("Care Circle: %s", name);
    }

}
