package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "court_id", referencedColumnName = "id")
    @NotNull(message = "Court is required!")
    private Court court;

    @OneToOne
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

    public Reservation(@NotNull Court court, @NotNull User user, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull boolean isDoubles) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        this.court = court;
        this.user = user;
        this.startTime = startTime;
        this. endTime = endTime;
        this.isDoubles = isDoubles;
        calculatePrice();
    }

    public void calculatePrice() {
        double minutePrice = court.getSurface().getRentalPrice();
        long durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        double multiply = isDoubles ? 1.5 : 1.0;
        price = minutePrice * durationInMinutes * multiply;
    }
}
