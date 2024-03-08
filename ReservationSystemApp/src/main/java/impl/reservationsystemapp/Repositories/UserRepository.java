package impl.reservationsystemapp.Repositories;

import impl.reservationsystemapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing users in the application.
 *
 * @author Martin Bjaloň
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks if a user exists with the given username.
     *
     * @param username The username to check.
     * @return true if a user exists with the given username, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return An Optional containing the user with the given username, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
