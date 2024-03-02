package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "court_id", referencedColumnName = "id")
    @NotNull(message = "Court is required!")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required!")
    private User user;

    @NotNull(message = "Start time is required!")
    @Future(message = "Start time must be in future!")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required!")
    @Future(message = "End time must be in future!")
    private LocalDateTime endTime;
    private boolean isDoubles;
    private double price;

    public Reservation(Court court, User user, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles, double price) {
        this.court = court;
        this.user = user;
        this.startTime = startTime;
        this. endTime = endTime;
        this.isDoubles = isDoubles;
        this.price = price;
    }

    public boolean isDoubles() {
        return isDoubles;
    }

    public void setDoubles(boolean doubles) {
        isDoubles = doubles;
    }

    public void calculatePrice() {
        double minutePrice = court.getSurface().getRentalPrice();
        long durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        double multiply = isDoubles ? 1.5 : 1.0;
        price = minutePrice * durationInMinutes * multiply;
    }
}
