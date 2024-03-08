package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a surface type for courts in the reservation system.
 *
 * @author Martin Bjaloň
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Surface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Column(name = "rental_price", columnDefinition = "DECIMAL(10, 2)")
    private double rentalPrice; // Rental price per minute

    public Surface(String name, double rentalPrice) {
        this.name = name;
        this.rentalPrice = rentalPrice;
    }

}
