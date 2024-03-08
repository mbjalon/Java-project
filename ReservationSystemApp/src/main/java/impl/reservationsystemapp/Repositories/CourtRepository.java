package impl.reservationsystemapp.Repositories;

import impl.reservationsystemapp.Entities.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing courts in the application.
 *
 * @author Matin Bjaloň
 */

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
}
