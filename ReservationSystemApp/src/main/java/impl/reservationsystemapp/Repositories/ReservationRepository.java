package impl.reservationsystemapp.Repositories;

import impl.reservationsystemapp.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing reservations in the application.
 *
 * @author Martin Bjaloň
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
