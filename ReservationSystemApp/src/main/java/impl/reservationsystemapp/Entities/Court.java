package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a court entity in the reservation system.
 *
 * @author Martin Bjaloň
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "court")
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @OneToOne
    @JoinColumn(name = "surface_id", referencedColumnName = "id")
    @NotNull(message = "Surface is required!")
    private Surface surface;

}
