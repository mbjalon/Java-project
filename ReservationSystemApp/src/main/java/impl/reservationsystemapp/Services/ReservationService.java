package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.calculatePrice();
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation with id " + id + " not found"));
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation reservation = getReservationById(id);
        reservation.setCourt(updatedReservation.getCourt());
        reservation.setCustomer(updatedReservation.getCustomer());
        reservation.setStartTime(updatedReservation.getStartTime());
        reservation.setEndTime(updatedReservation.getEndTime());
        reservation.setDoubles(updatedReservation.isDoubles());
        reservation.calculatePrice();
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}