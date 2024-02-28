package impl.reservationsystemapp.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;


@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isDoubles;
    private double price;

    public Reservation(Court court, Customer customer, LocalDateTime startTime, LocalDateTime endTime, boolean isDoubles, double price) {
        this.court = court;
        this. customer = customer;
        this.startTime = startTime;
        this. endTime = endTime;
        this.isDoubles = isDoubles;
        this.price = price;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isDoubles() {
        return isDoubles;
    }

    public void setDoubles(boolean doubles) {
        isDoubles = doubles;
    }

    public double getPrice() {
        return price;
    }

    public void calculatePrice() {
        double minutePrice = court.getSurface().getRentalPrice();
        long durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        double multiply = isDoubles ? 1.5 : 1.0;
        price = minutePrice * durationInMinutes * multiply;
    }
}
