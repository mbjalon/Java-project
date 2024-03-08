package impl.reservationsystemapp.Repositories;

import impl.reservationsystemapp.Entities.Surface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing surfaces in the application.
 *
 * @author Martin Bjaloň
 */
@Repository
public interface SurfaceRepository extends JpaRepository<Surface, Long> {
}
