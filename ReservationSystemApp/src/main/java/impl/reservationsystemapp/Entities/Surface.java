package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;


@Entity
public class Surface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double rentalPrice; // Rental price per minute

    public Surface(String name, double rentalPrice) {
        this.name = name;
        this.rentalPrice = rentalPrice;
    }

    public Surface() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
}
