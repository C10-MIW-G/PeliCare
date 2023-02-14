package com.makeitworkch10.pacemakers.pelicare.model;

import com.makeitworkch10.pacemakers.pelicare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ruben de Vries
 * This model holds the Users registered to their Care Circles.
 * Some Users can have Admin Rights in their Care Circle.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareCircleUser {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "care_circle_id")
    private CareCircle careCircle;

    private boolean isCircleAdmin;

    public CareCircleUser(User user, CareCircle careCircle, boolean isCircleAdmin) {
        this.user = user;
        this.careCircle = careCircle;
        this.isCircleAdmin = isCircleAdmin;
    }
}
